package br.com.lemao.environment.annotation;

import java.lang.annotation.Annotation;

import br.com.lemao.environment.Environment;

public class GivenEnvironmentBuilder {

	private Class<?> environmentClass;
	private String environmentName;
	
	private GivenEnvironmentBuilder() {}
	
	public GivenEnvironmentBuilder forClass(Class<?> environmentClass) {
		this.environmentClass = environmentClass;
		return this;
	}

	public GivenEnvironmentBuilder forName(String environmentName) {
		this.environmentName = environmentName;
		return this;
	}

	public GivenEnvironment gimme() {
		return new GivenEnvironment() {
			public Class<? extends Annotation> annotationType() {
				return GivenEnvironment.class;
			}
			
			public Class<?> value() {
				return environmentClass;
			}
			
			public String environmentName() {
				return (environmentName == null || environmentName.isEmpty()) ? Environment.DEFAULT_ENVIRONMENT_METHOD_NAME : environmentName;
			}
		};
	}

	public static GivenEnvironmentBuilder getInstance() {
		return new GivenEnvironmentBuilder();
	}

}
