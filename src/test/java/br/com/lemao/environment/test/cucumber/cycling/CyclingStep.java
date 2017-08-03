package br.com.lemao.environment.test.cucumber.cycling;

import static org.junit.Assert.assertEquals;

import br.com.lemao.environment.environments.OneMaleBikerAndOneBicycleForThisBikerEnvironment;
import br.com.lemao.environment.executor.EnvironmentExecutor;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CyclingStep {
	
	private Biker biker;
	private Bicycle bicycle;

	@Given("^I have one male biker and one bicycle for this biker$")
	public void iHaveOneMaleBikerAndOneBicycleForThisBiker() throws Throwable {
	    EnvironmentExecutor.gimme().execute(OneMaleBikerAndOneBicycleForThisBikerEnvironment.class);
	}

	@When("^I read the attributes of the biker$")
	public void iReadTheAttributesOfTheBiker() throws Throwable {
		biker = BikerInMemorySupport.findAll().get(0);
	}

	@When("^I read the attributes of the bicycle$")
	public void iReadTheAttributesOfTheBicycle() throws Throwable {
		bicycle = BicycleInMemorySupport.findAll().get(0);
	}

	@Then("^the name of the biker is \"([^\"]*)\"$")
	public void theNameOfTheBikerIs(String nameBiker) throws Throwable {
		assertEquals(nameBiker, biker.getName());
	}

	@Then("^the gender of the biker is \"([^\"]*)\"$")
	public void theGenderOfTheBikerIs(String genderBiker) throws Throwable {
		assertEquals(genderBiker, biker.getGender().toString());
	}

	@Then("^the model name of the bicycle is \"([^\"]*)\"$")
	public void theModelNameOfTheBicycleIs(String modelNameBicycle) throws Throwable {
		assertEquals(modelNameBicycle, bicycle.getModelName());
	}

	@Then("^the serial number of the bicycle is (\\d+)$")
	public void theSerialNumberOfTheBicycleIs(Long serialNumberBicycle) throws Throwable {
		assertEquals(serialNumberBicycle, bicycle.getSerialNumber());
	}

	@Then("^the name of the owner of the bicycle is \"([^\"]*)\"$")
	public void theNameOfTheOwnerOfTheBicycleIs(String nameOwnerBicycle) throws Throwable {
		assertEquals(nameOwnerBicycle, bicycle.getOwner().getName());
	}
	
	@After
	public void after() {
		BikerInMemorySupport.dropObjects();
		BicycleInMemorySupport.dropObjects();
	}
	
}
