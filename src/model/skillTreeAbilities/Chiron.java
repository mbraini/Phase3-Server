package model.skillTreeAbilities;

import constants.CostConstants;
import controller.gameController.configs.Configs;
import controller.gameController.enums.SkillTreeAbilityType;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Chiron extends SkillTreeAbility{
    private EpsilonModel epsilonModel;

    public Chiron(){
        isBought = Configs.SkillTreeConfigs.chironBought;
        unlockXpCost = CostConstants.CHIRON_UNLOCK_COST;
        type = SkillTreeAbilityType.chiron;
        initTimer();
        initEpsilon();
    }

    private void initEpsilon() {
        epsilonModel = ModelData.getEpsilon();
    }


    @Override
    protected void cast() {
        canCast = false;
        epsilonModel.setLifeSteal(epsilonModel.getLifeSteal() + 3);
        coolDownTimer.start();
    }

    @Override
    public void setUp() {
        super.setUp();
        initEpsilon();
    }
}
