package model.objectModel.fighters.miniBossEnemies.blackOrbModel;

import constants.SizeConstants;
import controller.gameController.manager.Spawner;
import constants.ControllerConstants;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.frameModel.FrameModelBuilder;
import utils.Helper;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class FrameSpawner  {
    private ArrayList<FrameModel> frameModels;
    private BlackOrbModel blackOrbModel;
    private int counter;
    public FrameSpawner(BlackOrbModel blackOrbModel ,int count){
        this.blackOrbModel = blackOrbModel;
        this.counter = count;
    }


    public void spawn() {
        double theta = counter * java.lang.Math.PI * 2 / 5;
        Vector initialPosition = Math.VectorAdd(
                blackOrbModel.getCenter(),
                Math.VectorWithSize(
                        new Vector(0 ,1),
                        SizeConstants.BLACK_ORB_DIAGONAL_SIZE
                )
        );

        Vector frameCenter = Math.RotateByTheta(
                initialPosition,
                blackOrbModel.getCenter(),
                theta
        );

        Vector framePosition = Math.VectorAdd(
                frameCenter,
                new Vector(
                        -SizeConstants.BLACK_ORB_FRAME_DIMENSION.width / 2d,
                        -SizeConstants.BLACK_ORB_FRAME_DIMENSION.height / 2d
                )
        );

        FrameModelBuilder builder = new FrameModelBuilder(
                framePosition,
                SizeConstants.BLACK_ORB_FRAME_DIMENSION,
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );

        builder.setIsometric(true);
        builder.setSolid(false);
        FrameModel frameModel = builder.create();

        Spawner.addFrame(frameModel);
        blackOrbModel.addFrame(frameModel);
    }

}
