package br.com.lemao.environment.exception;

public class EnvironmentException extends RuntimeException {

	private static final long serialVersionUID = -3711300800387484490L;
	
	private static final String MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT = "Error trying to run environment";
	
	public EnvironmentException(Exception e) {
		super(e);
	}
	
	public EnvironmentException(String message) {
		super(message);
	}
	
	public EnvironmentException(Class<?> environmentClass, String environmentName, Exception e) {
		super(getMessage(MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT, environmentClass, environmentName), e);
	}
	
	public EnvironmentException(String simpleMessage, Class<?> environmentClass, String environmentName, Exception e) {
		super(getMessage(simpleMessage, environmentClass, environmentName), e);
	}

	public EnvironmentException(String simpleMessage, Class<?> environmentClass) {
		super(getMessage(simpleMessage, environmentClass));
	}

	private static String getMessage(String simpleMessage, Class<?> environmentClass) {
		return simpleMessage + " >> " + environmentClass.getSimpleName();
	}

	private static String getMessage(String simpleMessage, Class<?> environmentClass, String environmentName) {
		return getMessage(simpleMessage, environmentClass) + "." + environmentName;
	}

}
