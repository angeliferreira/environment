package br.com.lemao.environment.model.biker;

import static br.com.lemao.environment.model.gender.Gender.FEMALE;
import static br.com.lemao.environment.model.gender.Gender.MALE;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.gender.Gender;

public class BikerBuilder {
	
	private String name;
	private Gender gender;
	
	public BikerBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	private BikerBuilder withGender(Gender gender) {
		this.gender = gender;
		return this;
	}
	
	public BikerBuilder male() {
		return withGender(MALE);
	}
	
	public BikerBuilder female() {
		return withGender(FEMALE);
	}
	
	public Biker gimme() {
		Biker biker = new Biker();
		biker.setName(name);
		biker.setGender(gender);
		
		return biker;
	}

}
