package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.annotation.Environment;
import br.com.lemao.environment.annotation.GivenEnvironment;

@Environment
public class A1EnvironmentDependsB1Environment {
	
	@GivenEnvironment(B1EnvironmentDependsC1Environment.class)
	public void run() {
		// nothing to do here
	}

}
