package br.com.lemao.environment.environments;

import br.com.lemao.environment.Environment;

public class EnvironmentSampleExtendsEnvironment extends Environment {
	
	public void throwNonEnvironmentException() {
		throw new RuntimeException();
	}
	
}
