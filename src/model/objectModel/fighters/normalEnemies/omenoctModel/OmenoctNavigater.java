package model.objectModel.fighters.normalEnemies.omenoctModel;

import model.ModelData;
import model.logics.collision.Collision;
import model.objectModel.frameModel.FrameLocations;
import model.objectModel.frameModel.FrameModel;
import utils.FrameHelper.FrameCalculationHelper;
import utils.Math;
import utils.Vector;

public class OmenoctNavigater {

    private Vector position;
    private Vector destination;
    private FrameLocations willAttachTo;

    public OmenoctNavigater(Vector position){
        this.position = position;
        destination = position.clone();
    }

    public void reset(Vector position){
        this.position = position;
        destination = position.clone();
    }


    public void navigateFrame() {
        FrameModel epsilonFrame = ModelData.getLocalFrames().get(ModelData.getModels().getFirst());
        if (epsilonFrame == null)
            return;

        double top = epsilonFrame.getPosition().getY();
        double bottom = epsilonFrame.getPosition().getY() + epsilonFrame.getSize().height;
        double left = epsilonFrame.getPosition().getX();
        double right = epsilonFrame.getPosition().getX() + epsilonFrame.getSize().width;
        double topDistance = java.lang.Math.abs(position.y - top);
        double bottomDistance = java.lang.Math.abs(position.y - bottom);
        double leftDistance = java.lang.Math.abs(position.x - left);
        double rightDistance = java.lang.Math.abs(position.x - right);

        if (epsilonFrame == null)
            return;
        if (hasArrived(epsilonFrame)){
            return;
        }
        if (Collision.isInFrame(epsilonFrame, position)){
            willAttachTo = FrameCalculationHelper.findClosestLocalFrameLocation(epsilonFrame ,position);
            switch (willAttachTo){
                case top :
                    destination = new Vector(position.x ,top);
                    return;
                case bottom:
                    destination = new Vector(position.x ,bottom);
                    return;
                case left:
                    destination = new Vector(left ,position.y);
                    return;
                case right:
                    destination = new Vector(right ,position.y);
                    return;
            }
        }

        else {
            if (position.x >= left && position.x <= right){
                if (topDistance <= bottomDistance){
                    destination = new Vector(position.x ,top);
                    willAttachTo = FrameLocations.top;
                }
                else {
                    destination = new Vector(position.x ,bottom);
                    willAttachTo = FrameLocations.bottom;
                }
                return;
            }
            if (position.y >= top && position.y <= bottom){
                if (leftDistance <= rightDistance){
                    destination = new Vector(left ,position.y);
                    willAttachTo = FrameLocations.left;
                }
                else {
                    destination = new Vector(right ,position.y);
                    willAttachTo = FrameLocations.right;
                }
                return;
            }
            Vector topLeft = epsilonFrame.getPosition().clone();
            Vector topRight = Math.VectorAdd(
                    epsilonFrame.getPosition() ,
                    new Vector(epsilonFrame.getSize().width, 0)
            );
            Vector bottomRight = Math.VectorAdd(
                    epsilonFrame.getPosition() ,
                    new Vector(epsilonFrame.getSize().width, epsilonFrame.getSize().height)
            );

            Vector bottomLeft = Math.VectorAdd(
                    epsilonFrame.getPosition() ,
                    new Vector(0, epsilonFrame.getSize().height)
            );

            double topLeftD = Math.VectorSize(Math.VectorAdd(
                    Math.ScalarInVector(-1 ,topLeft),
                    position
            ));

            double topRightD = Math.VectorSize(Math.VectorAdd(
                    Math.ScalarInVector(-1 ,topRight),
                    position
            ));

            double bottomRightD = Math.VectorSize(Math.VectorAdd(
                    Math.ScalarInVector(-1 ,bottomRight),
                    position
            ));

            double bottomLeftD = Math.VectorSize(Math.VectorAdd(
                    Math.ScalarInVector(-1 ,bottomLeft),
                    position
            ));

            double min = java.lang.Math.min(
                    java.lang.Math.min(topLeftD ,topRightD),
                    java.lang.Math.min(bottomLeftD ,bottomRightD)
            );

            if (min == topLeftD){
                destination = topLeft;
                willAttachTo = FrameLocations.topLeft;
            }

            if (min == topRightD){
                destination = topRight;
                willAttachTo = FrameLocations.topRight;
            }

            if (min == bottomLeftD){
                destination = bottomLeft;
                willAttachTo = FrameLocations.bottomLeft;
            }

            if (min == bottomRightD){
                destination = bottomRight;
                willAttachTo = FrameLocations.bottomRight;
            }
        }
    }

    private boolean hasArrived(FrameModel epsilonFrame) {
        Vector topLeft = epsilonFrame.getPosition().clone();
        Vector topRight = Math.VectorAdd(
                epsilonFrame.getPosition() ,
                new Vector(epsilonFrame.getSize().width, 0)
        );
        Vector bottomRight = Math.VectorAdd(
                epsilonFrame.getPosition() ,
                new Vector(epsilonFrame.getSize().width, epsilonFrame.getSize().height)
        );

        Vector bottomLeft = Math.VectorAdd(
                epsilonFrame.getPosition() ,
                new Vector(0, epsilonFrame.getSize().height)
        );

        if (position.Equals(topLeft) || position.Equals(topRight) ||
                position.Equals(bottomLeft) || position.Equals(bottomRight)){
            return true;
        }

        if (position.x == topLeft.x && position.y > topLeft.y && position.y < bottomLeft.y)
            return true;
        if (position.x == topRight.x && position.y > topRight.y && position.y < bottomRight.y)
            return true;
        if (position.y == topLeft.y && position.x > topLeft.x && position.x < topRight.x)
            return true;
        if (position.y == bottomLeft.y && position.x > topLeft.x && position.x < topRight.x)
            return true;

        return false;
    }


    public Vector getDestination() {
        return destination;
    }


    public FrameLocations getWillAttachTo() {
        return willAttachTo;
    }

}
