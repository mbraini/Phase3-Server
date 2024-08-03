package model.objectModel.fighters.normalEnemies.necropickModel;

import constants.SizeConstants;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import utils.Math;
import utils.Vector;

public class NecropickShooter {

    private Vector position;

    public NecropickShooter(Vector position) {
        this.position = position;
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
                    spawnPosition,
                    direction,
                    ModelType.necropickBullet
            );
        }
    }
}
