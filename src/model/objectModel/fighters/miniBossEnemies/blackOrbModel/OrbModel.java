package model.objectModel.fighters.miniBossEnemies.blackOrbModel;

import constants.SizeConstants;
import controller.game.Game;
import controller.game.ObjectController;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import model.interfaces.collisionInterfaces.IsCircle;
import model.objectModel.fighters.miniBossEnemies.MiniBossModel;
import model.objectModel.frameModel.FrameModel;
import utils.Vector;

import java.util.ArrayList;

public class OrbModel extends MiniBossModel implements IsCircle {

    @SkippedByJson
    private BlackOrbModel blackOrbModel;
    private FrameModel frameModel;
    private int number;

    public OrbModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers , Vector position , BlackOrbModel blackOrbModel , int number , String id){
        super(game ,chasingPlayer ,targetedPlayers);
        this.position = position;
        this.id = id;
        this.blackOrbModel = blackOrbModel;
        this.HP = 30;
        this.number = number;
        this.frameModel = blackOrbModel.getFrameModels().get(number);
        type = ModelType.orb;
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
            Spawner.addCollectives(game ,targetedPlayers ,position, 1, 30);
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
