package model.viewRequests;

import constants.SizeConstants;
import controller.game.enums.InGameAbilityType;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import model.inGameAbilities.InGameAbilityHandler;
import model.inGameAbilities.Slaughter;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;

public class ShootRequest {

    private final Player player;
    private Vector clickedPoint;

    public ShootRequest(Player player ,Vector clickedPoint){
        this.player = player;
        this.clickedPoint = clickedPoint;
    }



    public void shoot() {
        EpsilonModel epsilon = player.getPlayerData().getEpsilon();
        if (epsilon == null || clickedPoint == null)
            return;
        Vector direction = Math.VectorAdd(Math.ScalarInVector(-1 ,epsilon.getPosition()) ,clickedPoint);
        Vector position = Math.VectorAdd(
                Math.VectorWithSize(
                        direction ,
                        SizeConstants.EPSILON_BULLET_RADIOS + SizeConstants.EPSILON_DIMENSION.width / 2d + 1
                )
                ,epsilon.getPosition()
        );
        int constant = -1;
        if (player.getPlayerData().getSlaughterBulletCount() >= 1){
            Spawner.addProjectile(epsilon.getGame() ,epsilon.getTargetedPlayers() ,position ,direction ,ModelType.slaughterBullet);
            player.getPlayerData().setSlaughterBulletCount(
                    player.getPlayerData().getSlaughterBulletCount() - 1
            );
            Slaughter slaughter = (Slaughter) InGameAbilityHandler.getInGameAbility(InGameAbilityType.slaughter ,player);
            if (slaughter != null)
                slaughter.setUsed(true);
        }
        else {
            Spawner.addProjectile(epsilon.getGame() ,epsilon.getTargetedPlayers(),position, direction, ModelType.epsilonBullet);
        }
        for (int i = 0; i < player.getPlayerData().getExtraBullet() ;i++) {
            constant = constant * (-1);
            Vector direction2 = Math.RotateByTheta(
                    direction,
                    new Vector(0 ,0),
                    java.lang.Math.PI / 12 * constant
            );
            Vector spawnPosition = Math.VectorAdd(
                    Math.VectorWithSize(direction2 , SizeConstants.EPSILON_BULLET_RADIOS + 1),
                    position
            );
            Spawner.addProjectile(
                    epsilon.getGame(),
                    epsilon.getTargetedPlayers(),
                    spawnPosition,
                    direction2,
                    ModelType.epsilonBullet
            );
        }
    }

}
