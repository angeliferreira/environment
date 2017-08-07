package br.com.lemao.environment.executor;

import static br.com.lemao.environment.Environment.DEFAULT_ENVIRONMENT_METHOD_NAME;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import br.com.lemao.environment.Environment;
import br.com.lemao.environment.annotation.AfterEnvironment;
import br.com.lemao.environment.annotation.BeforeEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.exception.AfterEnvironmentException;
import br.com.lemao.environment.exception.BeforeEnvironmentException;
import br.com.lemao.environment.exception.CyclicDependencyEnvironmentException;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.exception.EnvironmentHierarchyException;
import br.com.lemao.environment.exception.EnvironmentNotImplementedException;

public class EnvironmentExecutor {

	private Map<String, Boolean> executionMap;

	private EnvironmentExecutor() {
		executionMap = new HashMap<String, Boolean>();
	}

	public static EnvironmentExecutor gimme() {
		return new EnvironmentExecutor();
	}

	public void execute(GivenEnvironment givenEnvironment) {
		execute(givenEnvironment.value(), givenEnvironment.environmentName());
	}

	public void execute(GivenEnvironment... givenEnvironments) {
		for (GivenEnvironment givenEnvironment : givenEnvironments) {
			execute(givenEnvironment);
		}
	}

	public void execute(GivenEnvironments givenEnvironments) {
		for (GivenEnvironment givenEnvironment : givenEnvironments.environments()) {
			execute(givenEnvironment.value(), givenEnvironment.environmentName());
		}
	}

	public void execute(Class<?> environmentClass) {
		execute(environmentClass, DEFAULT_ENVIRONMENT_METHOD_NAME);
	}

	public void execute(Class<?>... environmentsClasses) {
		for (Class<?> environmentClass : environmentsClasses) {
			execute(environmentClass);
		}
	}

	public void execute(Object... environmentsDefinition) {
		for (Object environmentDefinition : environmentsDefinition) {
			if (environmentDefinition instanceof Class<?>) {
				execute((Class<?>) environmentDefinition);
			} else if (environmentDefinition instanceof GivenEnvironment) {
				execute((GivenEnvironment) environmentDefinition);
			} else {
				throw new IllegalArgumentException("Environment definition not found to execute! -> " + environmentDefinition.getClass().getSimpleName());
			}
		}
	}

	public void execute(Class<?> environmentClass, String environmentName) {
		validateCyclicDependency(environmentClass, environmentName);
		if (isEnvironmentAlreadyExecuted(environmentClass, environmentName)) {
			return;
		}
		validateEnvironmentHierarchy(environmentClass);
		computeForExecution(environmentClass, environmentName);
		try {
			executeEnvironment(environmentClass, environmentName);
		} catch (EnvironmentException e) {
			throw e;
		} catch (Exception e) {
			throw new EnvironmentException(e);
		} finally {
			afterRun(environmentClass, environmentName);
			computeExecuted(environmentClass, environmentName);
		}
	}

	private void executeEnvironment(Class<?> environmentClass, String environmentName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Method environmentMethod = getEnvironmentMethod(environmentClass, environmentName);
		if (environmentMethod != null) {
			execute(environmentClass, environmentName, environmentMethod);
			return;
		}
		executeMultiple(environmentClass, environmentName);
	}

	private void validateCyclicDependency(Class<?> environmentClass, String environmentName) {
		if (isEnvironmentAlreadyForExecution(environmentClass, environmentName)) {
			throw new CyclicDependencyEnvironmentException(environmentClass, environmentName);
		}
	}

	private boolean isEnvironmentAlreadyForExecution(Class<?> environmentClass, String environmentName) {
		Boolean isEnvironmentAlreadyExecuted = executionMap.get(getEnvironmentSimpleName(environmentClass, environmentName));
		return isEnvironmentAlreadyExecuted != null && !isEnvironmentAlreadyExecuted;
	}

	private void computeForExecution(Class<?> environmentClass, String environmentName) {
		executionMap.put(getEnvironmentSimpleName(environmentClass, environmentName), false);
	}

	private void executeMultiple(Class<?> environmentClass, String environmentName) {
		GivenEnvironments givenEnvironments = environmentClass.getAnnotation(GivenEnvironments.class);
		if (givenEnvironments == null) {
			throw new EnvironmentNotImplementedException(environmentClass, environmentName);
		}
		execute(givenEnvironments);
	}

	private void execute(Class<?> environmentClass, String environmentName, Method environmentMethod) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		GivenEnvironment givenEnvironmentFather = environmentMethod.getAnnotation(GivenEnvironment.class);
		GivenEnvironments givenEnvironmentsFather = environmentMethod.getAnnotation(GivenEnvironments.class);
		if (givenEnvironmentFather != null) {
			execute(givenEnvironmentFather.value(), givenEnvironmentFather.environmentName());
		} else if (givenEnvironmentsFather != null) {
		    execute(givenEnvironmentsFather);
		}

		beforeRun(environmentClass, environmentName);

		environmentMethod.invoke(getEnvironmentInstance(environmentClass));
	}

	private Method getEnvironmentMethod(Class<?> environmentClass, String environmentName) throws NoSuchMethodException {
		try {
			return environmentClass.getDeclaredMethod(environmentName);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	private boolean isEnvironmentAlreadyExecuted(Class<?> environmentClass, String environmentName) {
		Boolean isEnvironmentAlreadyExecuted = executionMap.get(getEnvironmentSimpleName(environmentClass, environmentName));
		return isEnvironmentAlreadyExecuted != null && isEnvironmentAlreadyExecuted;
	}

	private void computeExecuted(Class<?> environmentClass, String environmentName) {
		executionMap.put(getEnvironmentSimpleName(environmentClass, environmentName), true);
	}

	private String getEnvironmentSimpleName(Class<?> environmentClass, String environmentName) {
		return environmentClass.getSimpleName() + "." + environmentName;
	}

	private void validateEnvironmentHierarchy(Class<?> environmentClass) {
		if (!extendsEnvironment(environmentClass) && !isAnnotationPresent(environmentClass, br.com.lemao.environment.annotation.Environment.class)) {
			throw new EnvironmentHierarchyException(environmentClass);
		}
	}

	private boolean isAnnotationPresent(Class<?> environmentClass, Class<? extends Annotation> annotationClass) {
		if (environmentClass == null) {
			return false;
		}
		return environmentClass.isAnnotationPresent(annotationClass) || isAnnotationPresent(environmentClass.getSuperclass(), annotationClass);
	}

	private boolean extendsEnvironment(Class<?> environmentClass) {
		return Environment.class.isAssignableFrom(environmentClass);
	}

	private void beforeRun(Class<?> environmentClass, String environmentName) {
		BeforeEnvironment beforeEnvironment = null;
		try {
			if (extendsEnvironment(environmentClass)) {
				((Environment) getEnvironmentInstance(environmentClass)).beforeRun();
			} else if (isAnnotationPresent(environmentClass, BeforeEnvironment.class)) {
				beforeEnvironment = environmentClass.getAnnotation(BeforeEnvironment.class);
				Method environmentMethod = environmentClass.getMethod(beforeEnvironment.value());
				environmentMethod.invoke(getEnvironmentInstance(environmentClass));
			}
		} catch (NoSuchMethodException e) {
			throw new BeforeEnvironmentException(environmentClass, beforeEnvironment.value(), e);
		} catch (Exception e) {
			throw new BeforeEnvironmentException(environmentClass, environmentName, e);
		}
	}

	private void afterRun(Class<?> environmentClass, String environmentName) {
		AfterEnvironment afterEnvironment = null;
		try {
			if (extendsEnvironment(environmentClass)) {
				((Environment) getEnvironmentInstance(environmentClass)).afterRun();
			} else if (isAnnotationPresent(environmentClass, AfterEnvironment.class)) {
				afterEnvironment = environmentClass.getAnnotation(AfterEnvironment.class);
				Method environmentMethod = environmentClass.getMethod(afterEnvironment.value());
				environmentMethod.invoke(getEnvironmentInstance(environmentClass));
			}
		} catch (NoSuchMethodException e) {
			throw new AfterEnvironmentException(environmentClass, afterEnvironment.value(), e);
		} catch (Exception e) {
			throw new AfterEnvironmentException(environmentClass, environmentName, e);
		}
	}

	private Object getEnvironmentInstance(Class<?> environmentClass) throws InstantiationException, IllegalAccessException {
		return environmentClass.newInstance();
	}

}
