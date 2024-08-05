package model.skillTreeAbilities;

import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Ares extends SkillTreeAbility{

    public Ares(Player player ,boolean isBought){
        super(player ,isBought);
//        isBought = Configs.SkillTreeConfigs.aresBought;
        unlockXpCost = 750;
        type = SkillTreeAbilityType.ares;
        initTimer();
    }


    @Override
    protected void cast() {
        canCast = false;
        EpsilonModel epsilon = player.getPlayerData().getEpsilon();
        epsilon.setEpsilonBulletDamage(epsilon.getEpsilonBulletDamage() + 2);
        epsilon.setMeleeAttack(epsilon.getMeleeAttack() + 2);
        coolDownTimer.start();
    }

}
