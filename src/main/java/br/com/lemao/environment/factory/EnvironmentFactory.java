package br.com.lemao.environment.factory;

public interface EnvironmentFactory {
	
	<T> T create(Class<T> clazz);

}
