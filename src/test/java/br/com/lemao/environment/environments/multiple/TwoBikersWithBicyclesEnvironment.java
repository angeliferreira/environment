package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment;

@GivenEnvironments(environments = {
		@GivenEnvironment(TwoBicyclesEnvironment.class),
		@GivenEnvironment(OneMaleBikerWithBicycleEnvironment.class),
		@GivenEnvironment(OneFemaleBikerWithBicycleEnvironment.class)
})
public class TwoBikersWithBicyclesEnvironment extends AbstractBikerBicycleEnvironment{

}
