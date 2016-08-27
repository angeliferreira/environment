package br.com.lemao.environment.exception;

import br.com.lemao.environment.Environment;

public class AfterEnvironmentException extends EnvironmentException {

	private static final long serialVersionUID = -3711300800387484490L;
	
	private static final String MESSAGE_ERROR_TRYING_TO_RUN_AFTER_ENVIRONMENT = "Error trying to run after environment";
	
	public AfterEnvironmentException(Class<? extends Environment> environmentClass, String environmentName, Exception e) {
		super(MESSAGE_ERROR_TRYING_TO_RUN_AFTER_ENVIRONMENT, environmentClass, environmentName, e);
	}

}
