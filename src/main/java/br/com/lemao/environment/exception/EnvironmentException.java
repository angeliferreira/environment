package br.com.lemao.environment.exception;

import br.com.lemao.environment.Environment;

public class EnvironmentException extends RuntimeException {

	private static final long serialVersionUID = -3711300800387484490L;
	
	private static final String MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT = "Error trying to run environment";
	
	public EnvironmentException(Class<? extends Environment> environmentClass, String environmentName, Exception e) {
		super(getMessage(MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT, environmentClass, environmentName), e);
	}
	
	public EnvironmentException(String simpleMessage, Class<? extends Environment> environmentClass, String environmentName, Exception e) {
		super(getMessage(simpleMessage, environmentClass, environmentName), e);
	}

	static String getMessage(String simpleMessage, Class<? extends Environment> environmentClass, String environmentName) {
		return getSimpleMessageOrDefault(simpleMessage) + " >> " + environmentClass.getSimpleName() + "." + environmentName;
	}

	private static String getSimpleMessageOrDefault(String simpleMessage) {
		return simpleMessage == null ? MESSAGE_ERROR_TRYING_TO_RUN_ENVIRONMENT : simpleMessage;
	}
	
}
