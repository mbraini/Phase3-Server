package model.skillTreeAbilities;

import constants.CostConstants;
import controller.game.configs.Configs;
import controller.game.enums.SkillTreeAbilityType;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Astrape extends SkillTreeAbility{

    public Astrape(){
        isBought = Configs.SkillTreeConfigs.astrapeBought;
        unlockXpCost = CostConstants.ASTRAPE_UNLOCK_COST;
        type = SkillTreeAbilityType.astrape;
        initTimer();
    }


    @Override
    protected void cast() {
        canCast = false;
        EpsilonModel epsilon = (EpsilonModel) ModelData.getModels().getFirst();
        epsilon.setEpsilonDamageOnCollision(epsilon.getEpsilonDamageOnCollision() + 2);
        coolDownTimer.start();
    }


}
