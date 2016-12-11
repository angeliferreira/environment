package br.com.lemao.environment.environments.annotation;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import br.com.lemao.environment.annotation.AfterEnvironment;
import br.com.lemao.environment.annotation.BeforeEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

@BeforeEnvironment("beforeRun")
@AfterEnvironment("afterRun")
public class TwoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBikerWithAnnotation extends BikerEnvironmentWithAnnotation {
	
	@GivenEnvironment(OneMaleBikerAndOneBicycleForThisBikerWithAnnotation.class)
	public void run() {
		Biker oliviaBiker = oneBiker().withName("Olivia").female().gimme();
		BikerInMemorySupport.persist(oliviaBiker);
	}
	
	public void beforeRun() {
		assertThat(BikerInMemorySupport.findAll(), hasSize(1));
		assertThat(BicycleInMemorySupport.findAll(), hasSize(1));
	}
	
	public void afterRun() {
		assertThat(BikerInMemorySupport.findAll(), hasSize(2));
		assertThat(BicycleInMemorySupport.findAll(), hasSize(1));
	}
}