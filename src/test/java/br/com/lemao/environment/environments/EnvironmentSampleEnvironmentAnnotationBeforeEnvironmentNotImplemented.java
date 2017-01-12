package br.com.lemao.environment.environments;

import br.com.lemao.environment.annotation.BeforeEnvironment;
import br.com.lemao.environment.annotation.Environment;

@BeforeEnvironment("beforeEnvironmentNotImplemented")
@Environment
public class EnvironmentSampleEnvironmentAnnotationBeforeEnvironmentNotImplemented {

	public void run() {
		// does nothing
	}
}
