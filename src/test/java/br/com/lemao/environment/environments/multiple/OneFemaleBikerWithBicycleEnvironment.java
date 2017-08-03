package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

public class OneFemaleBikerWithBicycleEnvironment extends AbstractBikerBicycleEnvironment {
	
	@Override
	@GivenEnvironment(TwoBicyclesEnvironment.class)
	public void run() {
		Biker maricotinhaBiker = oneBiker().withName("Maria Maricotinha").female().gimme();
		BikerInMemorySupport.persist(maricotinhaBiker);
		
		Bicycle bicycle = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - PINK");
		bicycle.setOwner(maricotinhaBiker);
	}

}