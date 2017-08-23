package br.com.lemao.environment.factory;

import br.com.lemao.environment.exception.EnvironmentException;

public class EnvironmentReflectionFactory implements EnvironmentFactory {

	public <T> T create(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new EnvironmentException(e);
		}
	}

}
