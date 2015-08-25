package br.com.lemao.environment;

import br.com.lemao.environment.exception.EnvironmentNotImplementedException;

public abstract class Environment {

	public static final String DEFAULT_ENVIRONMENT_METHOD_NAME = "run";

	public void run() {
		throw new EnvironmentNotImplementedException(getClass(), DEFAULT_ENVIRONMENT_METHOD_NAME, null);
	}

	public void beforeRun() {
		// Nothing to do here
	}

	public void afterRun() {
		// Nothing to do here
	}

}
