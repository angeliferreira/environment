package br.com.lemao.environment.annotation;

import static br.com.lemao.environment.Environment.DEFAULT_ENVIRONMENT_METHOD_NAME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.lemao.environment.Environment;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GivenEnvironment {

	Class<? extends Environment> value();

	String environmentName() default DEFAULT_ENVIRONMENT_METHOD_NAME;

}
