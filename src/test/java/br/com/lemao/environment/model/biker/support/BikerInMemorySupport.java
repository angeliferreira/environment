package br.com.lemao.environment.model.biker.support;

import java.util.ArrayList;
import java.util.List;

import br.com.lemao.environment.model.biker.Biker;

public final class BikerInMemorySupport {
	
	private static List<Biker> bikers = new ArrayList<Biker>();
	
	private BikerInMemorySupport() {}
	
	public static void persist(Biker bicycle) {
		bikers.add(bicycle);
	}
	
	public static List<Biker> findAll() {
		return bikers;
	}
	
	public static Biker findByName(String name) {
		for (Biker biker : findAll()) {
			if (biker.getName().equals(name))
				return biker;			
		}
		return null;
	}

	public static void dropObjects() {
		bikers.clear();
	}
}
