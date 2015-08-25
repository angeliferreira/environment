package br.com.lemao.environment.model.bicycle;

import br.com.lemao.environment.model.biker.Biker;

public class BicycleBuilder {
	
	private Long serialNumber;
	private String modelName;
	private Biker owner;
	
	public BicycleBuilder forBiker(Biker owner) {
		this.owner = owner;
		return this;
	}
	
	public BicycleBuilder withSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}
	
	public BicycleBuilder withModelName(String modelName) {
		this.modelName = modelName;
		return this;
	}
	
	public Bicycle gimme() {
		Bicycle bicycle = new Bicycle();
		bicycle.setModelName(modelName);
		bicycle.setSerialNumber(serialNumber);
		bicycle.setOwner(owner);
		
		return bicycle;
	}

}
