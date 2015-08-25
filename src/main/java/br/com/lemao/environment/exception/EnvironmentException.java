package br.com.lemao.environment.exception;


public class EnvironmentException extends RuntimeException {

	private static final String MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT = "Error trying to run environment";
	private static final long serialVersionUID = -3711300800387484490L;
	
	public EnvironmentException(Class<?> environmentClass, String environmentName, Exception e) {
		super(getMessage(null, environmentClass, environmentName), e);
	}
	
	public EnvironmentException(String simpleMessage, Class<?> environmentClass, String environmentName, Exception e) {
		super(getMessage(simpleMessage, environmentClass, environmentName), e);
	}

	static String getMessage(String simpleMessage, Class<?> environmentClass,	String environmentName) {
		return getSimpleMessageOrDefault(simpleMessage) + " >> " + environmentClass.getName() + "." + environmentName;
	}

	private static String getSimpleMessageOrDefault(String simpleMessage) {
		return simpleMessage == null ? MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT : simpleMessage;
	}

}
