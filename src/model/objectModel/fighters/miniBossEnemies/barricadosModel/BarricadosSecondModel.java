package model.objectModel.fighters.miniBossEnemies.barricadosModel;

import constants.SizeConstants;
import controller.game.Game;
import controller.game.ObjectController;
import controller.game.enums.ModelType;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.frameModel.FrameModelBuilder;
import utils.Math;
import utils.Vector;

public class BarricadosSecondModel extends BarricadosModel{

    public BarricadosSecondModel(Game game,Vector position , String id){
        super(game);
        this.position = position;
        this.HP = 10000000;
        this.id = id;
        this.vulnerableToEpsilonBullet = false;
        this.vulnerableToEpsilonMelee = false;
        isMotionless = true;
        type = ModelType.barricadosTheSecond;
        initVertices();
        initFrame();
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
