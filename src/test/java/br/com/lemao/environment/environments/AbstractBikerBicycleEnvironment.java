package br.com.lemao.environment.environments;

import br.com.lemao.environment.Environment;
import br.com.lemao.environment.model.bicycle.BicycleBuilder;
import br.com.lemao.environment.model.biker.BikerBuilder;

public abstract class AbstractBikerBicycleEnvironment extends Environment {
	
	public static BicycleBuilder oneBicycle() {
		return new BicycleBuilder();
	}

	public static BikerBuilder oneBiker() {
		return new BikerBuilder();
	}
}
