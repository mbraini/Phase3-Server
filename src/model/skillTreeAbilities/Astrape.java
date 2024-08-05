package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Astrape extends SkillTreeAbility{

    public Astrape(Player player ,boolean isBought){
        super(player ,isBought);
        unlockXpCost = CostConstants.ASTRAPE_UNLOCK_COST;
        type = SkillTreeAbilityType.astrape;
        initTimer();
    }


    @Override
    protected void cast() {
        canCast = false;
        EpsilonModel epsilon = player.getPlayerData().getEpsilon();
        epsilon.setEpsilonDamageOnCollision(epsilon.getEpsilonDamageOnCollision() + 2);
        coolDownTimer.start();
    }


}
