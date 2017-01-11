package br.com.lemao.environment.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

import br.com.lemao.environment.environments.EnvironmentSampleDoNotExtendsEnvironment;
import br.com.lemao.environment.environments.EnvironmentSampleExtendsEnvironment;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.exception.EnvironmentHierarchyException;
import br.com.lemao.environment.exception.EnvironmentNotImplementedException;
import br.com.lemao.environment.executor.EnvironmentExecutor;

public class EnvironmentExecutorRainyDayTest {
	
	@Test
	public void shouldThrowEnvironmentNotImplementedException() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleExtendsEnvironment.class, "wtfMethod");
			Assert.fail("should throw an EnvironmentNotImplementedException");
		} catch (EnvironmentNotImplementedException e) {
			assertThat(e.getMessage(), is("Error trying to run environment >> EnvironmentSampleExtendsEnvironment.wtfMethod"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentHierarchyException() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleDoNotExtendsEnvironment.class);
			Assert.fail("should throw an EnvironmentHierarchyException");
		} catch (EnvironmentHierarchyException e) {
			assertThat(e.getMessage(), is("Missing 'extends Environment' or @Environment !? >> EnvironmentSampleDoNotExtendsEnvironment"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionCauseNonEnvironmentException() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleExtendsEnvironment.class, "throwNonEnvironmentException");
			Assert.fail("should throw an non environment Exception");
		} catch (EnvironmentException e) {
			assertThat(e.getCause().getClass().getSimpleName(), not(containsString("Environment")));
			assertThat(e.getMessage(), is("Error trying to run environment >> EnvironmentSampleExtendsEnvironment.throwNonEnvironmentException"));
		}
	}
	
}
