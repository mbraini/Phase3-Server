package model.objectModel.fighters.normalEnemies.necropickModel;

import constants.SizeConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class NecropickShooter {

    private Vector position;
    private ArrayList<Player> targetedPlayers;
    private Game game;

    public NecropickShooter(Game game,ArrayList<Player> targetedPlayers, Vector position) {
        this.position = position;
        this.targetedPlayers = targetedPlayers;
        this.game = game;
    }

    public void shoot() {
        double theta = 0;
        Vector direction;
        Vector spawnPosition;
        for (int i = 0; i < 8 ;i++){
            theta += java.lang.Math.PI / 4d;
            direction = new Vector(java.lang.Math.cos(theta) , java.lang.Math.sin(theta));
            spawnPosition = Math.VectorAdd(
                    Math.VectorWithSize(direction , SizeConstants.NECROPICK_DIMENSION.width / 2d),
                    position
            );
            Spawner.addProjectile(
                    game,
                    targetedPlayers,
                    spawnPosition,
                    direction,
                    ModelType.necropickBullet
            );
        }
    }
}
