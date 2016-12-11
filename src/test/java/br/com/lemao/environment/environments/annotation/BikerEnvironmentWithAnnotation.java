package br.com.lemao.environment.environments.annotation;

import br.com.lemao.environment.annotation.Environment;
import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.BicycleBuilder;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.BikerBuilder;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

@Environment
public class BikerEnvironmentWithAnnotation {
	
	public void twoBikersOneMaleAnotherFemale() {
		Biker zeBiker = oneBiker().withName("Zé Grandão").male().gimme();
		BikerInMemorySupport.persist(zeBiker);
		
		Biker maricotinhaBiker = oneBiker().withName("Maria Maricotinha").female().gimme();
		BikerInMemorySupport.persist(maricotinhaBiker);
	}
	
	@GivenEnvironment(value=BikerEnvironmentWithAnnotation.class, environmentName="twoBikersOneMaleAnotherFemale")
	public void twoBikersOneMaleAnotherFemaleWithBicycles() {
		Bicycle epicBicycle = oneBicycle()
				.forBiker(BikerInMemorySupport.findByName("Zé Grandão"))
				.withModelName("S-WORKS EPIC 29")
				.withSerialNumber(165487L)
				.gimme();
		BicycleInMemorySupport.persist(epicBicycle);
		
		Bicycle allezBicycle = oneBicycle()
				.forBiker(BikerInMemorySupport.findByName("Maria Maricotinha"))
				.withModelName("S-WORKS ALLEZ DI2")
				.withSerialNumber(98657L)
				.gimme();
		BicycleInMemorySupport.persist(allezBicycle);
	}
	
	protected BicycleBuilder oneBicycle() {
		return new BicycleBuilder();
	}

	protected BikerBuilder oneBiker() {
		return new BikerBuilder();
	}

}
