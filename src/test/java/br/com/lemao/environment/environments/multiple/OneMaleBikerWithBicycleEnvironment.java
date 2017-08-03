package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

public class OneMaleBikerWithBicycleEnvironment extends AbstractBikerBicycleEnvironment {
	
	@Override
	@GivenEnvironment(TwoBicyclesEnvironment.class)
	public void run() {
		Biker lemaoBiker = oneBiker().withName("Lemão").male().gimme();
		BikerInMemorySupport.persist(lemaoBiker);
		
		Bicycle bicycle = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - BLUE");
		bicycle.setOwner(lemaoBiker);
	}

}