package br.com.lemao.environment.test;

import static br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment.oneBicycle;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import br.com.lemao.environment.environments.BikerEnvironment;
import br.com.lemao.environment.environments.OneMaleBikerAndOneBicycleForThisBiker;
import br.com.lemao.environment.environments.TwoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBiker;
import br.com.lemao.environment.executor.EnvironmentExecutor;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;
import br.com.lemao.environment.model.gender.Gender;

public class EnvironmentExecutorTest {
	
	@After
	public void dropObjects() {
		BikerInMemorySupport.dropObjects();
		BicycleInMemorySupport.dropObjects();
	}

	@Test
	public void oneBikerAndOneBicycleForThisBikerCreatedByEnvironment() {
		EnvironmentExecutor.gimme().execute(OneMaleBikerAndOneBicycleForThisBiker.class);
		
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(1));
		
		Biker biker = bikers.get(0);
		assertThat(biker.getGender(), is(Gender.MALE));
		assertThat(biker.getName(), is("Lemão"));
		
		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(1));
		
		Bicycle bicycle = bicycles.get(0);
		assertThat(bicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(bicycle.getSerialNumber(), is(165487L));
		assertThat(bicycle.getOwner(), is(biker));
	}
	
	@Test
	public void twoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBikerCreatedByEnvironment() {
		EnvironmentExecutor.gimme().execute(TwoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBiker.class);
		
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));
		
		Biker lemaoBiker = BikerInMemorySupport.findByName("Lemão");
		assertThat(lemaoBiker.getGender(), is(Gender.MALE));
		assertThat(lemaoBiker.getName(), is("Lemão"));
		
		Biker oliviaBiker = BikerInMemorySupport.findByName("Olivia");
		assertThat(oliviaBiker.getGender(), is(Gender.FEMALE));
		assertThat(oliviaBiker.getName(), is("Olivia"));
		
		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(1));
		
		Bicycle bicycle = bicycles.get(0);
		assertThat(bicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(bicycle.getSerialNumber(), is(165487L));
		assertThat(bicycle.getOwner(), is(lemaoBiker));
	}
	
	@Test
	public void twoBikersOneMaleAnotherFemaleCreatedByEnvironment() {
		EnvironmentExecutor.gimme().execute(BikerEnvironment.class, "twoBikersOneMaleAnotherFemale");
		
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));
		
		Biker zeBiker = BikerInMemorySupport.findByName("Zé Grandão");
		assertThat(zeBiker.getGender(), is(Gender.MALE));
		assertThat(zeBiker.getName(), is("Zé Grandão"));
		
		Biker maricotinhaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(maricotinhaBiker.getGender(), is(Gender.FEMALE));
		assertThat(maricotinhaBiker.getName(), is("Maria Maricotinha"));
	}
	
	@Test
	public void twoBikersOneMaleAnotherFemaleWithBicyclesCreatedByEnvironment() {
		EnvironmentExecutor.gimme().execute(BikerEnvironment.class, "twoBikersOneMaleAnotherFemaleWithBicycles");
		
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));
		
		Biker zeBiker = BikerInMemorySupport.findByName("Zé Grandão");
		assertThat(zeBiker.getGender(), is(Gender.MALE));
		assertThat(zeBiker.getName(), is("Zé Grandão"));
		
		Biker maricotinhaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(maricotinhaBiker.getGender(), is(Gender.FEMALE));
		assertThat(maricotinhaBiker.getName(), is("Maria Maricotinha"));
		
		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(2));
		
		Bicycle epicBicycle = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29");
		assertThat(epicBicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(epicBicycle.getSerialNumber(), is(165487L));
		assertThat(epicBicycle.getOwner(), is(zeBiker));
		
		Bicycle allezBicycle = BicycleInMemorySupport.findByModelName("S-WORKS ALLEZ DI2");
		assertThat(allezBicycle.getModelName(), is("S-WORKS ALLEZ DI2"));
		assertThat(allezBicycle.getSerialNumber(), is(98657L));
		assertThat(allezBicycle.getOwner(), is(maricotinhaBiker));
	}
	
	@Test
	public void twoBikersOneMaleAnotherFemaleWithBicyclesCreatedByTest() {
		EnvironmentExecutor.gimme().execute(BikerEnvironment.class, "twoBikersOneMaleAnotherFemale");
		
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));
		
		Biker zeBiker = BikerInMemorySupport.findByName("Zé Grandão");
		assertThat(zeBiker.getGender(), is(Gender.MALE));
		assertThat(zeBiker.getName(), is("Zé Grandão"));
		
		Biker maricotinhaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(maricotinhaBiker.getGender(), is(Gender.FEMALE));
		assertThat(maricotinhaBiker.getName(), is("Maria Maricotinha"));
		
		assertTrue(BicycleInMemorySupport.findAll().isEmpty());
		
		Bicycle epicBicycle = oneBicycle()
				.forBiker(zeBiker)
				.withModelName("S-WORKS EPIC 29")
				.withSerialNumber(165487L)
				.gimme();
		BicycleInMemorySupport.persist(epicBicycle);
		
		Bicycle allezBicycle = oneBicycle()
				.forBiker(maricotinhaBiker)
				.withModelName("S-WORKS ALLEZ DI2")
				.withSerialNumber(98657L)
				.gimme();
		BicycleInMemorySupport.persist(allezBicycle);
		
		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(2));
		
		epicBicycle = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29");
		assertThat(epicBicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(epicBicycle.getSerialNumber(), is(165487L));
		assertThat(epicBicycle.getOwner(), is(zeBiker));
		
		allezBicycle = BicycleInMemorySupport.findByModelName("S-WORKS ALLEZ DI2");
		assertThat(allezBicycle.getModelName(), is("S-WORKS ALLEZ DI2"));
		assertThat(allezBicycle.getSerialNumber(), is(98657L));
		assertThat(allezBicycle.getOwner(), is(maricotinhaBiker));
	}

}
