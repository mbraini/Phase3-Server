package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.manager.GameState;

public class Athena extends SkillTreeAbility{

    public Athena(){
//        isBought = Configs.SkillTreeConfigs.athenaBought;
        unlockXpCost = CostConstants.ATHENA_UNLOCK_COST;
        type = SkillTreeAbilityType.athena;
        initTimer();
    }



    @Override
    protected void cast() {
        canCast = false;
//        GameState.setShrinkageVelocity(GameState.getShrinkageVelocity() - 0.2 * GameState.getShrinkageVelocity());
        ///todo
        coolDownTimer.start();
    }

    @Override
    public void setUp() {
        super.setUp();
    }
}
