package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

import java.awt.*;

public class Empusa extends SkillTreeAbility{
    private EpsilonModel epsilonModel;

    public Empusa(Player player ,boolean isBought) {
        super(player ,isBought);
//        isBought = Configs.SkillTreeConfigs.empusaBought;
        unlockXpCost = CostConstants.EMPUSA_UNLOCK_COST;
        type = SkillTreeAbilityType.empusa;
        initTimer();
        initEpsilon();
    }

    private void initEpsilon() {
        this.epsilonModel = player.getPlayerData().getEpsilon();
    }


    @Override
    protected void cast() {
        canCast = false;
        Dimension newSize = new Dimension(
                epsilonModel.getSize().width * 9 / 10,
                epsilonModel.getSize().height * 9 / 10
        );
        epsilonModel.setSize(newSize);
        coolDownTimer.start();
    }

    @Override
    public void setUp() {
        super.setUp();
        initEpsilon();
    }
}
