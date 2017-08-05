package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.annotation.Environment;
import br.com.lemao.environment.annotation.GivenEnvironment;

@Environment
public class B1EnvironmentDependsC1Environment {
	
	@GivenEnvironment(C1EnvironmentDependsB1Environment.class)
	public void run() {
		// nothing to do here
	}

}
