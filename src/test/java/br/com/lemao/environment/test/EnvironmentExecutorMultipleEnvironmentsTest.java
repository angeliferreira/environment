package br.com.lemao.environment.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironmentBuilder;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.environments.multiple.OneFemaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.OneMaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.TwoBikersWithBicyclesEnvironment;
import br.com.lemao.environment.executor.EnvironmentExecutor;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;
import br.com.lemao.environment.model.gender.Gender;

public class EnvironmentExecutorMultipleEnvironmentsTest {
	
	@After
	public void dropObjects() {
		BikerInMemorySupport.dropObjects();
		BicycleInMemorySupport.dropObjects();
	}

	@Test
	public void twoBikersWithBicyclesCreatedByMultipleEnvironmentsClasses() {
		EnvironmentExecutor.gimme().execute(OneMaleBikerWithBicycleEnvironment.class, OneFemaleBikerWithBicycleEnvironment.class);
		
		assertTwoBikersWithBicycles();
	}
	
	@Test
	public void twoBikersWithBicyclesCreatedByGivenEnvironmentsAnnotation() {
		EnvironmentExecutor.gimme().execute(getAnnotationForTwoBikersWithBicycles());
		
		assertTwoBikersWithBicycles();
	}

	@Test
	public void twoBikersWithBicyclesCreatedByMultipleEnvironmentsGivenEnvironment() {
		GivenEnvironment oneMaleBikerWithBicycleEnvironment = GivenEnvironmentBuilder.getInstance()
				.forClass(OneMaleBikerWithBicycleEnvironment.class)
				.gimme();
		
		GivenEnvironment oneFemaleBikerWithBicycleEnvironment = GivenEnvironmentBuilder.getInstance()
				.forClass(OneFemaleBikerWithBicycleEnvironment.class)
				.gimme();
		EnvironmentExecutor.gimme().execute(oneMaleBikerWithBicycleEnvironment, oneFemaleBikerWithBicycleEnvironment);
		
		assertTwoBikersWithBicycles();
	}
	
	@Test
	public void twoBikersWithBicyclesCreatedByMultipleEnvironmentsClassAndGivenEnvironment() {
		GivenEnvironment oneFemaleBikerWithBicycleEnvironment = GivenEnvironmentBuilder.getInstance()
				.forClass(OneFemaleBikerWithBicycleEnvironment.class)
				.gimme();
		EnvironmentExecutor.gimme().execute(OneMaleBikerWithBicycleEnvironment.class, oneFemaleBikerWithBicycleEnvironment);
		
		assertTwoBikersWithBicycles();
	}
	
	@Test
	public void twoBikersWithBicyclesCreatedByEnvironmentAgregateEnvironments() {
		EnvironmentExecutor.gimme().execute(TwoBikersWithBicyclesEnvironment.class);
		
		assertTwoBikersWithBicycles();
	}
	
	private void assertTwoBikersWithBicycles() {
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));
		
		Biker lemaoBiker = BikerInMemorySupport.findByName("Lemão");
		assertThat(lemaoBiker.getGender(), is(Gender.MALE));
		assertThat(lemaoBiker.getName(), is("Lemão"));
		
		Biker mariaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(mariaBiker.getGender(), is(Gender.FEMALE));
		assertThat(mariaBiker.getName(), is("Maria Maricotinha"));
		
		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(2));
		
		Bicycle bicycleBlue = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - BLUE");
		assertThat(bicycleBlue.getModelName(), is("S-WORKS EPIC 29 - BLUE"));
		assertThat(bicycleBlue.getSerialNumber(), is(165487L));
		assertThat(bicycleBlue.getOwner(), is(lemaoBiker));
		
		Bicycle bicyclePink = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - PINK");
		assertThat(bicyclePink.getModelName(), is("S-WORKS EPIC 29 - PINK"));
		assertThat(bicyclePink.getSerialNumber(), is(132423L));
		assertThat(bicyclePink.getOwner(), is(mariaBiker));
	}
	
	private GivenEnvironments getAnnotationForTwoBikersWithBicycles() {
		return new GivenEnvironments() {
			public Class<? extends Annotation> annotationType() {
				return GivenEnvironments.class;
			}
			
			public GivenEnvironment[] environments() {
				GivenEnvironment oneMaleBikerWithBicycleEnvironment = GivenEnvironmentBuilder.getInstance()
						.forClass(OneMaleBikerWithBicycleEnvironment.class)
						.gimme();
				
				GivenEnvironment oneFemaleBikerWithBicycleEnvironment = GivenEnvironmentBuilder.getInstance()
						.forClass(OneFemaleBikerWithBicycleEnvironment.class)
						.gimme();
				
				return new GivenEnvironment[] {oneMaleBikerWithBicycleEnvironment , oneFemaleBikerWithBicycleEnvironment};
			}
		};
	}

}
