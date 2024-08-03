package model.inGameAbilities;

import constants.DistanceConstants;
import controller.game.enums.InGameAbilityType;
import model.ModelData;
import model.logics.Impact;
import model.objectModel.fighters.EpsilonModel;

public class Banish extends InGameAbility{

    private EpsilonModel epsilon;

    public Banish(EpsilonModel epsilon){
        type = InGameAbilityType.banish;
        xpCost = 100;
        this.epsilon = epsilon;
    }

    @Override
    public void performAbility() {
        new Impact(epsilon.getPosition() , DistanceConstants.BANISH_IMPACT_RANGE ,2000 ,true).MakeImpact();
        isActive = false;
    }

    @Override
    public void setUp() {
        epsilon = ModelData.getEpsilon();
    }
}
