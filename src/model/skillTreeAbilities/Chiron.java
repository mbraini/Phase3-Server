package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Chiron extends SkillTreeAbility{
    private EpsilonModel epsilonModel;

    public Chiron(Player player ,boolean isBought){
        super(player ,isBought);
        unlockXpCost = CostConstants.CHIRON_UNLOCK_COST;
        type = SkillTreeAbilityType.chiron;
        initTimer();
        initEpsilon();
    }

    private void initEpsilon() {
        epsilonModel = player.getPlayerData().getEpsilon();
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
