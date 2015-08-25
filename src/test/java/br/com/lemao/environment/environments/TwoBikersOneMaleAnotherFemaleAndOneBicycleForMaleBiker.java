package br.com.lemao.environment.environments;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

public class TwoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBiker extends AbstractBikerBicycleEnvironment {
	
	@Override
	@GivenEnvironment(OneMaleBikerAndOneBicycleForThisBiker.class)
	public void run() {
		Biker oliviaBiker = oneBiker().withName("Olivia").female().gimme();
		BikerInMemorySupport.persist(oliviaBiker);
	}
	
	@Override
	public void beforeRun() {
		assertThat(BikerInMemorySupport.findAll(), hasSize(1));
		assertThat(BicycleInMemorySupport.findAll(), hasSize(1));
	}
	
	@Override
	public void afterRun() {
		assertThat(BikerInMemorySupport.findAll(), hasSize(2));
		assertThat(BicycleInMemorySupport.findAll(), hasSize(1));
	}
}