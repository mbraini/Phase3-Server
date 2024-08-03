package model.objectModel.fighters.miniBossEnemies.blackOrbModel;

import constants.SizeConstants;
import controller.game.ObjectController;
import controller.game.manager.Spawner;
import controller.online.annotations.SkippedByJson;
import model.interfaces.collisionInterfaces.IsCircle;
import model.objectModel.fighters.miniBossEnemies.MiniBossModel;
import model.objectModel.frameModel.FrameModel;
import utils.Vector;

public class OrbModel extends MiniBossModel implements IsCircle {

    @SkippedByJson
    private BlackOrbModel blackOrbModel;
    private FrameModel frameModel;
    private int number;

    public OrbModel(Vector position ,BlackOrbModel blackOrbModel ,int number ,String id){
        this.position = position;
        this.id = id;
        this.blackOrbModel = blackOrbModel;
        this.HP = 30;
        this.number = number;
        this.frameModel = blackOrbModel.getFrameModels().get(number);
        isMotionless = true;
        vulnerableToEpsilonMelee = true;
        vulnerableToEpsilonBullet = true;
    }


    @Override
    public void die() {
        synchronized (blackOrbModel.getEffectModels()) {
            super.die();
            blackOrbModel.getBlackOrbThread().disconnectLasers(this);
            blackOrbModel.removeOrb(id);
            blackOrbModel.checkDeath();
            ObjectController.removeFrame(frameModel);
            Spawner.addCollectives(position, 1, 30);
        }
    }


    @Override
    public double getRadios() {
        return SizeConstants.ORB_DIMENSION.width / 2d;
    }

    @Override
    public Vector getCenter() {
        return position;
    }


    public void setBlackOrbModel(BlackOrbModel blackOrbModel) {
        this.blackOrbModel = blackOrbModel;
    }
}
