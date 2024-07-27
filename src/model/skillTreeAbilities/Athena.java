package model.skillTreeAbilities;

import constants.CostConstants;
import controller.gameController.configs.Configs;
import controller.gameController.enums.SkillTreeAbilityType;
import controller.gameController.manager.GameState;

public class Athena extends SkillTreeAbility{

    public Athena(){
        isBought = Configs.SkillTreeConfigs.athenaBought;
        unlockXpCost = CostConstants.ATHENA_UNLOCK_COST;
        type = SkillTreeAbilityType.athena;
        initTimer();
    }



    @Override
    protected void cast() {
        canCast = false;
        GameState.setShrinkageVelocity(GameState.getShrinkageVelocity() - 0.2 * GameState.getShrinkageVelocity());
        coolDownTimer.start();
    }

    @Override
    public void setUp() {
        super.setUp();
    }
}
