package br.com.lemao.environment.executor;

import static br.com.lemao.environment.Environment.DEFAULT_ENVIRONMENT_METHOD_NAME;

import java.lang.reflect.Method;

import br.com.lemao.environment.Environment;
import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.exception.AfterEnvironmentException;
import br.com.lemao.environment.exception.BeforeEnvironmentException;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.exception.EnvironmentNotImplementedException;

public class EnvironmentExecutor {
	
	private EnvironmentExecutor() {}
	
	public static EnvironmentExecutor gimme() {
		return new EnvironmentExecutor();
	}
	
	public void execute(GivenEnvironment givenEnvironment) {
		execute(givenEnvironment.value(), givenEnvironment.environmentName());
	}
	
	public void execute(Class<? extends Environment> environmentClass) {
		execute(environmentClass, DEFAULT_ENVIRONMENT_METHOD_NAME);
	}

	public void execute(Class<? extends Environment> environmentClass, String environmentName) {
		try {
			Method environmentMethod = environmentClass.getMethod(environmentName);

			GivenEnvironment environmentFather = environmentMethod.getAnnotation(GivenEnvironment.class);
			if (environmentFather != null) execute(environmentFather.value(), environmentFather.environmentName());
			
			beforeRun(environmentClass, environmentName);
			
			environmentMethod.invoke(getEnvironmentInstance(environmentClass));
		} catch (NoSuchMethodException e) {
			throw new EnvironmentNotImplementedException(environmentClass, environmentName, e);
		} catch (Exception e) {
			throw new EnvironmentException(environmentClass, environmentName, e);
		} finally {
			afterRun(environmentClass, environmentName);
		}
	}
	
	private void beforeRun(Class<? extends Environment> environmentClass, String environmentName) {
		try {
			getEnvironmentInstance(environmentClass).beforeRun();
		} catch (Exception e) {
			throw new BeforeEnvironmentException(environmentClass, environmentName, e);
		}
	}
	
	private void afterRun(Class<? extends Environment> environmentClass, String environmentName) {
		try {
			getEnvironmentInstance(environmentClass).afterRun();
		} catch (Exception e) {
			throw new AfterEnvironmentException(environmentClass, environmentName, e);
		}
	}
	
	private Environment getEnvironmentInstance(Class<? extends Environment> environmentClass) throws InstantiationException, IllegalAccessException {
		return (Environment) environmentClass.newInstance();
	}

}
