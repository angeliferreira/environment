package br.com.lemao.environment.environments;

import br.com.lemao.environment.annotation.AfterEnvironment;
import br.com.lemao.environment.annotation.Environment;

@AfterEnvironment("afterEnvironmentNotImplemented")
@Environment
public class EnvironmentSampleEnvironmentAnnotationAfterEnvironmentNotImplemented {

	public void run() {
		// does nothing
	}
}
