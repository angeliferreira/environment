package br.com.lemao.environment.exception;

import br.com.lemao.environment.Environment;

public class EnvironmentNotImplementedException extends EnvironmentException {

	private static final long serialVersionUID = -5783452972838832690L;
	
	public EnvironmentNotImplementedException(Class<? extends Environment> environmentClass, String environmentName, Exception e) {
		super(environmentClass, environmentName, e);
	}
	
	public EnvironmentNotImplementedException(Class<? extends Environment> environmentClass, String environmentName) {
		super(environmentClass, environmentName, null);
	}

}
