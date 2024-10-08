package controller.game.listeners;

import constants.SizeConstants;
import constants.TimeConstants;
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
        if (epsilon == null || clickedPoint == null  || player.getGame().getGameState().isPause())
            return;
        if (player.isDead())
            return;
        double timePassed = player.getGame().getGameState().getTime() - player.getPlayerData().getLastTimeShot();
        if (timePassed > TimeConstants.PLAYER_SHOT_DELAY) {
            player.getPlayerData().setLastTimeShot(player.getGame().getGameState().getTime());
        }
        else
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
            Spawner.addEpsilonBullet(
                    epsilon.getGame() ,
                    player,
                    epsilon.getTargetedPlayers() ,
                    position ,
                    direction ,
                    ModelType.slaughterBullet
            );
            player.getPlayerData().setSlaughterBulletCount(
                    player.getPlayerData().getSlaughterBulletCount() - 1
            );
            Slaughter slaughter = (Slaughter) InGameAbilityHandler.getInGameAbility(InGameAbilityType.slaughter ,player);
            if (slaughter != null)
                slaughter.setUsed(true);
        }
        else {
            Spawner.addEpsilonBullet(
                    epsilon.getGame() ,
                    player,
                    epsilon.getTargetedPlayers(),
                    position,
                    direction,
                    ModelType.epsilonBullet
            );
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
            Spawner.addEpsilonBullet(
                    epsilon.getGame(),
                    player,
                    epsilon.getTargetedPlayers(),
                    spawnPosition,
                    direction2,
                    ModelType.epsilonBullet
            );
        }
    }

}
