package model.skillTreeAbilities.Cerberus;

import constants.CostConstants;
import constants.SizeConstants;
import controller.game.enums.ModelType;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;
import model.skillTreeAbilities.SkillTreeAbility;
import utils.Math;
import utils.Vector;

public class Cerberus extends SkillTreeAbility {

    public Cerberus(Player player ,boolean isBought){
        super(player ,isBought);
        unlockXpCost = CostConstants.CERBERUS_UNLOCK_COST;
        type = SkillTreeAbilityType.cerberus;
        initTimer();
    }

    @Override
    protected void cast() {
        canCast = false;
        EpsilonModel epsilon = player.getPlayerData().getEpsilon();
        Vector direction = new Vector(0 ,1);
        direction = Math.VectorWithSize(
                direction,
                SizeConstants.CERBERUS_RADIOS + SizeConstants.EPSILON_DIMENSION.height / 2d
        );
        for (int i = 0; i < 3 ;i++) {
            Spawner.spawnObject(
                    player.getGame(),
                    null,
                    epsilon.getTargetedPlayers(),
                    Math.VectorAdd(
                            epsilon.getPosition(),
                            direction
                    ),
                    ModelType.cerberus
            );
            direction = Math.RotateByTheta(direction , new Vector(),java.lang.Math.PI / 3 * 2);
        }
        coolDownTimer.start();
    }
}
