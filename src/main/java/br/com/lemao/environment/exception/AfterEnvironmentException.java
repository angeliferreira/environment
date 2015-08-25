package br.com.lemao.environment.exception;

public class AfterEnvironmentException extends EnvironmentException {

	private static final String MESSAGE_ERROR_TRYING_TO_RUN_AFTER_ENVIRONMENT = "Error trying to run after environment";
	private static final long serialVersionUID = -3711300800387484490L;
	
	public AfterEnvironmentException(Class<?> environmentClass, String environmentName, Exception e) {
		super(MESSAGE_ERROR_TRYING_TO_RUN_AFTER_ENVIRONMENT, environmentClass, environmentName, e);
	}

}
