package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;

public class TwoBicyclesEnvironment extends AbstractBikerBicycleEnvironment {
	
	@Override
	public void run() {
		Bicycle epicBicycleBlue = oneBicycle()
				.withModelName("S-WORKS EPIC 29 - BLUE")
				.withSerialNumber(165487L)
				.gimme();
		BicycleInMemorySupport.persist(epicBicycleBlue);
		
		Bicycle epicBicyclePink = oneBicycle()
				.withModelName("S-WORKS EPIC 29 - PINK")
				.withSerialNumber(132423L)
				.gimme();
		BicycleInMemorySupport.persist(epicBicyclePink);
	}

}
