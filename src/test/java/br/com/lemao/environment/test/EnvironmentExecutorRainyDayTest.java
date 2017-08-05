package br.com.lemao.environment.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

import br.com.lemao.environment.environments.EnvironmentSampleDoesNotExtendsEnvironment;
import br.com.lemao.environment.environments.EnvironmentSampleEnvironmentAnnotationAfterEnvironment;
import br.com.lemao.environment.environments.EnvironmentSampleEnvironmentAnnotationAfterEnvironmentNotImplemented;
import br.com.lemao.environment.environments.EnvironmentSampleEnvironmentAnnotationBeforeEnvironment;
import br.com.lemao.environment.environments.EnvironmentSampleEnvironmentAnnotationBeforeEnvironmentNotImplemented;
import br.com.lemao.environment.environments.EnvironmentSampleExtendsEnvironment;
import br.com.lemao.environment.environments.multiple.A1EnvironmentDependsB1Environment;
import br.com.lemao.environment.environments.multiple.B1EnvironmentDependsC1Environment;
import br.com.lemao.environment.environments.multiple.C1EnvironmentDependsB1Environment;
import br.com.lemao.environment.exception.CyclicDependencyEnvironmentException;
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
	public void shouldThrowEnvironmentNotImplementedExceptionForRunMethod() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleExtendsEnvironment.class);
			Assert.fail("should throw an EnvironmentNotImplementedException");
		} catch (EnvironmentNotImplementedException e) {
			assertThat(e.getMessage(), is("Error trying to run environment >> EnvironmentSampleExtendsEnvironment.run"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentHierarchyException() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleDoesNotExtendsEnvironment.class);
			Assert.fail("should throw an EnvironmentHierarchyException");
		} catch (EnvironmentHierarchyException e) {
			assertThat(e.getMessage(), is("Missing 'extends Environment' or @Environment !? >> EnvironmentSampleDoesNotExtendsEnvironment"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionCauseNonEnvironmentException() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleExtendsEnvironment.class, "throwNonEnvironmentException");
			Assert.fail("should throw an non environment Exception");
		} catch (EnvironmentException e) {
			assertThat(e.getCause().getClass().getSimpleName(), not(containsString("Environment")));
			assertThat(e.getMessage(), is("java.lang.reflect.InvocationTargetException"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionOnExecuteBeforeEnvironmentMethodNotImplemented() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleEnvironmentAnnotationBeforeEnvironmentNotImplemented.class);
			Assert.fail("should throw an EnvironmentException");
		} catch (EnvironmentException e) {
			assertThat(e.getCause().getClass().getSimpleName(), not(containsString("Environment")));
			assertThat(e.getMessage(), is("Error trying to run before environment >> EnvironmentSampleEnvironmentAnnotationBeforeEnvironmentNotImplemented.beforeEnvironmentNotImplemented"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionOnExecuteBeforeEnvironmentMethod() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleEnvironmentAnnotationBeforeEnvironment.class);
			Assert.fail("should throw an EnvironmentException");
		} catch (EnvironmentException e) {
			assertThat(e.getCause().getClass().getSimpleName(), not(containsString("Environment")));
			assertThat(e.getMessage(), is("Error trying to run before environment >> EnvironmentSampleEnvironmentAnnotationBeforeEnvironment.run"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionOnExecuteAfterEnvironmentMethodNotImplemented() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleEnvironmentAnnotationAfterEnvironmentNotImplemented.class);
			Assert.fail("should throw an EnvironmentException");
		} catch (EnvironmentException e) {
			assertThat(e.getCause().getClass().getSimpleName(), not(containsString("Environment")));
			assertThat(e.getMessage(), is("Error trying to run after environment >> EnvironmentSampleEnvironmentAnnotationAfterEnvironmentNotImplemented.afterEnvironmentNotImplemented"));
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionOnExecuteAfterEnvironmentMethod() {
		try {
			EnvironmentExecutor.gimme().execute(EnvironmentSampleEnvironmentAnnotationAfterEnvironment.class);
			Assert.fail("should throw an EnvironmentException");
		} catch (EnvironmentException e) {
			assertThat(e.getCause().getClass().getSimpleName(), not(containsString("Environment")));
			assertThat(e.getMessage(), is("Error trying to run after environment >> EnvironmentSampleEnvironmentAnnotationAfterEnvironment.run"));
		}
	}
	
	@Test
	public void shouldThrowCyclicDependencyException() {
		try {
			EnvironmentExecutor.gimme().execute(A1EnvironmentDependsB1Environment.class, B1EnvironmentDependsC1Environment.class, C1EnvironmentDependsB1Environment.class);
			Assert.fail("should throw an CyclicDependencyException");
		} catch (CyclicDependencyEnvironmentException e) {
			assertThat(e.getMessage(), is("Error trying to run environment >> B1EnvironmentDependsC1Environment.run"));
		}
	}
	
}
