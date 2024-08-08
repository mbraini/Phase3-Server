package model.objectModel.fighters.normalEnemies.wyrmModel;

import constants.SizeConstants;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;

public class WyrmShooter {

    private WyrmModel wyrmModel;
    public WyrmShooter(WyrmModel wyrmModel){
        this.wyrmModel = wyrmModel;
    }
    public void shoot() {
        Vector position = wyrmModel.getPosition();
        EpsilonModel epsilon = wyrmModel.getChasingPlayer().getPlayerData().getEpsilon();

        Vector direction = Math.VectorAdd(
                Math.ScalarInVector(-1 ,position),
                epsilon.getPosition()
        );

        Vector bulletPosition = Math.VectorAdd(
                position,
                Math.VectorWithSize(
                        direction ,
                        SizeConstants.OMENOCT_BULLET_RADIOS + SizeConstants.OMENOCT_RADIOS
                )
        );

        Spawner.addProjectile(wyrmModel.getGame(),wyrmModel.getTargetedPlayers() ,bulletPosition ,direction , ModelType.wyrmBullet);
    }
}
