package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

@GivenEnvironments(environments = {
    @GivenEnvironment(OneBicycleEnvironment.class)
})
public class OneBikerWithBicycleWithMethodRunAndGivenEnvironmentsEnvironment extends AbstractBikerBicycleEnvironment {

    @Override
    public void run() {
        Biker lemaoBiker = oneBiker().withName("Lemão").male().gimme();
        BikerInMemorySupport.persist(lemaoBiker);

        Bicycle bicycle = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - BLACK");
        bicycle.setOwner(lemaoBiker);
    }

}
