package model.objectModel.fighters.finalBoss.abilities.squeeze;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import model.objectModel.fighters.finalBoss.bossHelper.HandModel;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

public class SqueezeNavigator {

    private FrameModel epsilonFrame;
    private HandModel leftHand;
    private HandModel rightHand;

    public SqueezeNavigator(FrameModel epsilonFrame , HandModel leftHand , HandModel rightHand){
        this.epsilonFrame = epsilonFrame;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    public void navigate(){
        Vector leftFrameCenter = new Vector(
                epsilonFrame.getPosition().x,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
        );
        Vector rightFrameCenter = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
        );
        Vector leftHandPlacer = Math.VectorAdd(
                leftFrameCenter,
                new Vector(
                        -SizeConstants.HAND_DIMENSION.width / 2d,
                        0
                )
        );

        Vector rightHandPlacer = Math.VectorAdd(
                rightFrameCenter,
                new Vector(
                        SizeConstants.HAND_DIMENSION.width / 2d,
                        0
                )
        );

        Vector leftHandDirection = Math.VectorAdd(
                leftHandPlacer,
                Math.ScalarInVector(-1 ,leftHand.getPosition())
        );
        Vector rightHandDirection = Math.VectorAdd(
                rightHandPlacer,
                Math.ScalarInVector(-1 ,rightHand.getPosition())
        );
        leftHandDirection = Math.VectorWithSize(
                leftHandDirection,
                VelocityConstants.HAND_SQUEEZE_NAVIGAE_VELOCITY
        );
        rightHandDirection = Math.VectorWithSize(
                rightHandDirection,
                VelocityConstants.HAND_SQUEEZE_NAVIGAE_VELOCITY
        );
        leftHand.setVelocity(leftHandDirection);
        rightHand.setVelocity(rightHandDirection);

    }

    public boolean hasArrived(){
        Vector leftFrameCenter = new Vector(
                epsilonFrame.getPosition().x,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
        );
        Vector rightFrameCenter = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
        );
        Vector leftHandPlacer = Math.VectorAdd(
                leftFrameCenter,
                new Vector(
                        -SizeConstants.HAND_DIMENSION.width / 2d,
                        0
                )
        );

        Vector rightHandPlacer = Math.VectorAdd(
                rightFrameCenter,
                new Vector(
                        SizeConstants.HAND_DIMENSION.width / 2d,
                        0
                )
        );

        Vector distanceVLeft = Math.VectorAdd(
                leftHand.getPosition(),
                Math.ScalarInVector(-1 ,leftHandPlacer)
        );
        Vector distanceVRight = Math.VectorAdd(
                rightHand.getPosition(),
                Math.ScalarInVector(-1 ,rightHandPlacer)
        );
        double leftDistance = Math.VectorSize(distanceVLeft);
        double rightDistance = Math.VectorSize(distanceVRight);
        double minDistance = VelocityConstants.HAND_SQUEEZE_NAVIGAE_VELOCITY * RefreshRateConstants.UPS;
        if (leftDistance <= minDistance && rightDistance <= minDistance)
            return true;
        return false;
    }


}
