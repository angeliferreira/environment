package br.com.lemao.environment.executor;

import static br.com.lemao.environment.Environment.DEFAULT_ENVIRONMENT_METHOD_NAME;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import br.com.lemao.environment.Environment;
import br.com.lemao.environment.annotation.AfterEnvironment;
import br.com.lemao.environment.annotation.BeforeEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.exception.AfterEnvironmentException;
import br.com.lemao.environment.exception.BeforeEnvironmentException;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.exception.EnvironmentHierarchyException;
import br.com.lemao.environment.exception.EnvironmentNotImplementedException;

public class EnvironmentExecutor {
	
	private EnvironmentExecutor() {}
	
	public static EnvironmentExecutor gimme() {
		return new EnvironmentExecutor();
	}
	
	public void execute(GivenEnvironment givenEnvironment) {
		execute(givenEnvironment.value(), givenEnvironment.environmentName());
	}
	
	public void execute(Class<?> environmentClass) {
		execute(environmentClass, DEFAULT_ENVIRONMENT_METHOD_NAME);
	}

	public void execute(Class<?> environmentClass, String environmentName) {
		validateEnvironmentHierarchy(environmentClass);
		try {
			Method environmentMethod = environmentClass.getMethod(environmentName);

			GivenEnvironment environmentFather = environmentMethod.getAnnotation(GivenEnvironment.class);
			if (environmentFather != null) execute(environmentFather.value(), environmentFather.environmentName());
			
			beforeRun(environmentClass, environmentName);
			
			environmentMethod.invoke(getEnvironmentInstance(environmentClass));
		} catch (NoSuchMethodException e) {
			throw new EnvironmentNotImplementedException(environmentClass, environmentName, e);
		} catch (BeforeEnvironmentException e) {
			throw e;
		} catch (Exception e) {
			throw new EnvironmentException(environmentClass, environmentName, e);
		} finally {
			afterRun(environmentClass, environmentName);
		}
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
