package br.com.lemao.environment.exception;


public class EnvironmentNotImplementedException extends EnvironmentException {

	private static final long serialVersionUID = -5783452972838832690L;
	
	public EnvironmentNotImplementedException(Class<?> environmentClass, String environmentName, Exception e) {
		super(environmentClass, environmentName, e);
	}

}
