package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.manager.GameState;
import controller.game.player.Player;

public class Athena extends SkillTreeAbility{

    public Athena(Player player ,boolean isBought){
        super(player ,isBought);
        unlockXpCost = CostConstants.ATHENA_UNLOCK_COST;
        type = SkillTreeAbilityType.athena;
        initTimer();
    }



    @Override
    protected void cast() {
        canCast = false;
        GameState gameState = player.getGame().getGameState();
        gameState.setShrinkageVelocity(gameState.getShrinkageVelocity() - 0.2 * gameState.getShrinkageVelocity());
        coolDownTimer.start();
    }

    @Override
    public void setUp() {
        super.setUp();
    }
}
