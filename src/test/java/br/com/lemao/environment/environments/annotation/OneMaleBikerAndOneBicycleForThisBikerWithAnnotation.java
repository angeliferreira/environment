package br.com.lemao.environment.environments.annotation;

import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

public class OneMaleBikerAndOneBicycleForThisBikerWithAnnotation extends BikerEnvironmentWithAnnotation {
	
	public void run() {
		Biker lemaoBiker = oneBiker().withName("Lemão").male().gimme();
		BikerInMemorySupport.persist(lemaoBiker);
		
		Bicycle bicycle = oneBicycle()
				.forBiker(lemaoBiker)
				.withModelName("S-WORKS EPIC 29")
				.withSerialNumber(165487L)
				.gimme();
		BicycleInMemorySupport.persist(bicycle);
	}

}