package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Melapmus extends SkillTreeAbility{

    private EpsilonModel epsilonModel;

    public Melapmus(Player player ,boolean isBought){
        super(player ,isBought);
        unlockXpCost = CostConstants.MELAMPUS_UNLOCK_COST;
        type = SkillTreeAbilityType.melampus;
        initTimer();
        initEpsilon();
    }

    private void initEpsilon() {
        epsilonModel = player.getPlayerData().getEpsilon();
    }


    @Override
    protected void cast() {
        canCast = false;
        epsilonModel.setChanceOfSurvival(epsilonModel.getChanceOfSurvival() + 5);
        coolDownTimer.start();
    }

    @Override
    public void setUp() {
        super.setUp();
        initEpsilon();
    }
}
