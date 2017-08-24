package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;

public class OneBicycleEnvironment extends AbstractBikerBicycleEnvironment {

	@Override
	public void run() {
		Bicycle epicBicycleBlack = oneBicycle()
				.withModelName("S-WORKS EPIC 29 - BLACK")
				.withSerialNumber(99999L)
				.gimme();
		BicycleInMemorySupport.persist(epicBicycleBlack);
	}

}
