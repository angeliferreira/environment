package br.com.lemao.environment.environments;

import br.com.lemao.environment.annotation.BeforeEnvironment;
import br.com.lemao.environment.annotation.Environment;

@BeforeEnvironment("beforeEnvironmentThrowException")
@Environment
public class EnvironmentSampleEnvironmentAnnotationBeforeEnvironment {

	public void run() {
		// does nothing
	}
	
	public void beforeEnvironmentThrowException() {
		throw new RuntimeException();
	}
}
