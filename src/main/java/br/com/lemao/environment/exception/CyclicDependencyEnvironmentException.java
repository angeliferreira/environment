package br.com.lemao.environment.exception;

public class CyclicDependencyEnvironmentException extends EnvironmentException {

	private static final long serialVersionUID = 2474045565085413674L;

	public CyclicDependencyEnvironmentException(Class<?> environmentClass, String environmentName) {
		super(environmentClass, environmentName, null);
	}

}
