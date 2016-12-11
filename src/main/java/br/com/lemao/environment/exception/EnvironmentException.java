package br.com.lemao.environment.exception;

public class EnvironmentException extends RuntimeException {

	private static final long serialVersionUID = -3711300800387484490L;
	
	private static final String MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT = "Error trying to run environment";
	
	public EnvironmentException(Class<?> environmentClass, String environmentName, Exception e) {
		super(getMessage(MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT, environmentClass, environmentName), e);
	}
	
	public EnvironmentException(String simpleMessage, Class<?> environmentClass, String environmentName, Exception e) {
		super(getMessage(simpleMessage, environmentClass, environmentName), e);
	}

	public EnvironmentException(Class<?> environmentClass) {
		super(getMessage(MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT, environmentClass));
	}

	private static String getMessage(String simpleMessage, Class<?> environmentClass) {
		return getSimpleMessageOrDefault(simpleMessage) + " >> " + environmentClass.getSimpleName();
	}

	private static String getMessage(String simpleMessage, Class<?> environmentClass, String environmentName) {
		return getMessage(simpleMessage, environmentClass) + "." + environmentName;
	}

	private static String getSimpleMessageOrDefault(String simpleMessage) {
		return simpleMessage == null ? MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT : simpleMessage;
	}
	
}
