package model.logics;


import model.interfaces.movementIntefaces.ImpactAble;
import model.objectModel.ObjectModel;
import utils.Math;
import utils.Vector;

public class Dash {
    private final ObjectModel oigModel;
    private final Vector direction;
    private final int time;

    private final double distance;

    private final double theta;
    private final boolean grow;
    public Dash(ObjectModel oigModel, Vector direction, int time, double distance , double theta , boolean grow) {
        this.oigModel = oigModel;
        this.direction = Math.VectorWithSize(direction ,1);
        this.time = time;
        this.distance = distance;
        this.theta = theta;
        this.grow = grow;
    }

    public void startDash() {

        /////////////////////////////Calculation
        double a = (2 * distance) / java.lang.Math.pow(time, 2);
        double alpha = (2 * theta) / java.lang.Math.pow(time, 2);
        if (!grow) {
            oigModel.setVelocity(Math.ScalarInVector(time * a, direction));
            oigModel.setAcceleration(Math.ScalarInVector(-a, direction));
            oigModel.setOmega(time * alpha);
            oigModel.setAlpha(-alpha);
        }
        else {
            oigModel.setVelocity(0 ,0);
            oigModel.setAcceleration(Math.ScalarInVector(a ,direction));
            oigModel.setOmega(0);
            oigModel.setAlpha(alpha);
        }
        if (oigModel instanceof ImpactAble) {
            ((ImpactAble) oigModel).setImpacted(true);
        }
        oigModel.getMovementManager().setUpDownAccTime(time);
        oigModel.getMovementManager().setLeftRightAccTime(time);
        oigModel.getMovementManager().setRotateAccTime(time);

    }
}
