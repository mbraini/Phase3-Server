package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.enums.SkillTreeAbilityType;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Proteus extends SkillTreeAbility{

    private EpsilonModel epsilonModel;

    public Proteus(){
//        isBought = Configs.SkillTreeConfigs.proteusBought;
        unlockXpCost = CostConstants.PROTEUS_UNLOCK_COST;
        type = SkillTreeAbilityType.proteus;
        initTimer();
        initEpsilon();
    }

    private void initEpsilon() {
        epsilonModel = player.getPlayerData().getEpsilon();
    }


    @Override
    protected void cast() {
        canCast = false;
        epsilonModel.addVertex();
        coolDownTimer.start();
    }

    @Override
    public void setUp() {
        super.setUp();
        epsilonModel = player.getPlayerData().getEpsilon();
    }
}
