package model.objectModel.fighters.miniBossEnemies.barricadosModel;

import constants.SizeConstants;
import controller.game.onlineGame.Game;
import controller.game.onlineGame.ObjectController;
import controller.game.enums.ModelType;
import controller.game.player.Player;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.frameModel.FrameModelBuilder;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class BarricadosSecondModel extends BarricadosModel{

    public BarricadosSecondModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers, Vector position , String id){
        super(game ,chasingPlayer ,targetedPlayers);
        this.position = position;
        this.HP = 10000000;
        this.id = id;
        this.vulnerableToEpsilonBullet = false;
        this.vulnerableToEpsilonMelee = false;
        isMotionless = true;
        type = ModelType.barricadosTheSecond;
        initVertices();
        initFrame();
        game.getModelRequests().addFrameModel(frameModel);
    }

    private void initFrame() {
        FrameModelBuilder builder = new FrameModelBuilder(
                game,
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.BARRICADOS_DIMENSION.width / 2d,
                                -SizeConstants.BARRICADOS_DIMENSION.height / 2d
                        )
                ),
                SizeConstants.BARRICADOS_DIMENSION,
                id
        );
        builder.setSolid(true);
        builder.setIsometric(true);
        frameModel = builder.create();
    }

    private FrameModel frameModel;

    @Override
    public void die() {
        super.die();
        ObjectController.removeFrame(frameModel);
    }

    public FrameModel getFrameModel() {
        return frameModel;
    }

    public void setFrameModel(FrameModel frameModel) {
        this.frameModel = frameModel;
    }
}
