package br.com.lemao.environment.exception;

import br.com.lemao.environment.Environment;

public class BeforeEnvironmentException extends EnvironmentException {

	private static final String MESSAGE_ERROR_TRYING_TO_RUN_BEFORE_ENVIRONMENT = "Error trying to run before environment";
	private static final long serialVersionUID = -3711300800387484490L;
	
	public BeforeEnvironmentException(Class<? extends Environment> environmentClass, String environmentName, Exception e) {
		super(MESSAGE_ERROR_TRYING_TO_RUN_BEFORE_ENVIRONMENT, environmentClass, environmentName, e);
	}

}
