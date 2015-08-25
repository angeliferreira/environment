package br.com.lemao.environment.model.bicycle.support;

import java.util.ArrayList;
import java.util.List;

import br.com.lemao.environment.model.bicycle.Bicycle;

public final class BicycleInMemorySupport {
	
	private static List<Bicycle> bicycles = new ArrayList<Bicycle>();
	
	private BicycleInMemorySupport() {}
	
	public static void persist(Bicycle bicycle) {
		bicycles.add(bicycle);
	}
	
	public static List<Bicycle> findAll() {
		return bicycles;
	}
	
	public static Bicycle findByModelName(String modelName) {
		for (Bicycle bicycle : findAll()) {
			if (bicycle.getModelName().equals(modelName))
				return bicycle;			
		}
		return null;
	}

	public static void dropObjects() {
		bicycles.clear();
	}
}
