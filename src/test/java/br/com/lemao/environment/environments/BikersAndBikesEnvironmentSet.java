package br.com.lemao.environment.environments;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

public class BikersAndBikesEnvironmentSet {

	public static final class TwoBikers extends AbstractBikerBicycleEnvironment {
		@Override
		public void run() {
			Biker bortolozzo = oneBiker().withName("Bortolozzo").male().gimme();
			BikerInMemorySupport.persist(bortolozzo);

			Biker bruna = oneBiker().withName("Bruna").female().gimme();
			BikerInMemorySupport.persist(bruna);
		}
	}
	
	public static final class TwoBikersInMemory extends AbstractBikerBicycleEnvironment {
		@Override
		public void run() {
			Biker bortolozzo = oneBiker().withName("Bortolozzo").male().gimme();
			BikerInMemorySupport.persist(bortolozzo);

			Biker bruna = oneBiker().withName("Bruna").female().gimme();
			BikerInMemorySupport.persist(bruna);
		}
	}

	public static final class TwoBikersWithBicycles extends AbstractBikerBicycleEnvironment {
		@Override
		@GivenEnvironment(TwoBikers.class)
		public void run() {
			Bicycle bortolozzoBike = oneBicycle()
					.forBiker(BikerInMemorySupport.findByName("Bortolozzo"))
					.withModelName("KHS")
					.withSerialNumber(100l)
					.gimme();
			BicycleInMemorySupport.persist(bortolozzoBike);

			Bicycle brunaBike = oneBicycle()
					.forBiker(BikerInMemorySupport.findByName("Bruna"))
					.withModelName("Moulton")
					.withSerialNumber(100l)
					.gimme();
			BicycleInMemorySupport.persist(brunaBike);
		}
	}
	
	public static final class TwoBikersWithBicyclesInMemory extends AbstractBikerBicycleEnvironment {
		@Override
		@GivenEnvironment(TwoBikersInMemory.class)
		public void run() {
			Bicycle bortolozzoBike = oneBicycle()
					.forBiker(BikerInMemorySupport.findByName("Bortolozzo"))
					.withModelName("KHS")
					.withSerialNumber(100l)
					.gimme();
			BicycleInMemorySupport.persist(bortolozzoBike);

			Bicycle brunaBike = oneBicycle()
					.forBiker(BikerInMemorySupport.findByName("Bruna"))
					.withModelName("Moulton")
					.withSerialNumber(100l)
					.gimme();
			BicycleInMemorySupport.persist(brunaBike);
		}
	}

	public static final class TwoBikersWithOneBicycle extends AbstractBikerBicycleEnvironment {
		@Override
		@GivenEnvironment(TwoBikers.class)
		public void run() {
			Bicycle bortolozzoBike = oneBicycle()
					.forBiker(BikerInMemorySupport.findByName("Bortolozzo"))
					.withModelName("KHS")
					.withSerialNumber(100l)
					.gimme();
			BicycleInMemorySupport.persist(bortolozzoBike);
		}
	}
	
	public static final class TwoBikersWithOneBicycleInMemory extends AbstractBikerBicycleEnvironment {
		@Override
		@GivenEnvironment(TwoBikersInMemory.class)
		public void run() {
			Bicycle bortolozzoBike = oneBicycle()
					.forBiker(BikerInMemorySupport.findByName("Bortolozzo"))
					.withModelName("KHS")
					.withSerialNumber(100l)
					.gimme();
			BicycleInMemorySupport.persist(bortolozzoBike);
		}
	}
}