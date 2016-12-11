package br.com.lemao.environment.exception;

public class EnvironmentHierarchyException extends EnvironmentException {

	private static final long serialVersionUID = -8816563652053350425L;

	public EnvironmentHierarchyException(Class<?> environmentClass) {
		super(environmentClass);
	}
	
}
