package br.com.lemao.environment.environments;

import br.com.lemao.environment.annotation.AfterEnvironment;
import br.com.lemao.environment.annotation.Environment;

@AfterEnvironment("afterEnvironmentThrowException")
@Environment
public class EnvironmentSampleEnvironmentAnnotationAfterEnvironment {

	public void run() {
		// does nothing
	}
	
	public void afterEnvironmentThrowException() {
		throw new RuntimeException();
	}
}
