package model.animations;


import controller.gameController.manager.GameState;
import model.interfaces.movementIntefaces.ImpactAble;
import model.objectModel.ObjectModel;
import utils.Math;
import utils.Vector;

import java.util.HashMap;

public class DashAnimation extends ThreadAnimation {
    private final ObjectModel oigModel;
    private final Vector direction;
    private final int time;
    private  int timePassed;

    private final double distance;

    private final double theta;
    static HashMap<ObjectModel ,DashAnimation> dashes = new HashMap<>();
    private final boolean grow;
    private volatile boolean stopBoolean;
    public DashAnimation(ObjectModel oigModel, Vector direction, int time, double distance ,double theta ,boolean grow) {
        this.oigModel = oigModel;
        this.direction = Math.VectorWithSize(direction ,1);
        this.time = time;
        this.distance = distance;
        this.theta = theta;
        this.grow = grow;
    }

    @Override
    public void run() {
        while (!getStopBoolean()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!GameState.isPause()) {
                timePassed += 100;
                if (timePassed >= time) {
                    stopDash(this);
                }
            }
        }
    }

    @Override
    public void StartAnimation() {
        if (dashes.containsKey(oigModel)){
            stopDash(dashes.get(oigModel));
            dashes.remove(oigModel);
        }

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
        //////////////////////////////

        if (oigModel instanceof ImpactAble) {
            ((ImpactAble) oigModel).setImpacted(true);
        }

        dashes.put(oigModel ,this);
        start();
    }

    void stopDash(DashAnimation dashAnimation){
        if (dashAnimation == null)
            return;
        dashAnimation.setStopBoolean(true);
        if (oigModel instanceof ImpactAble) {
            ((ImpactAble) oigModel).setImpacted(false);
        }
        oigModel.setAcceleration(0 ,0);
        oigModel.setVelocity(0 ,0);

        oigModel.setAlpha(0);
        oigModel.setOmega(0);
    }

    public boolean getStopBoolean() {
        return stopBoolean;
    }

    public void setStopBoolean(boolean stopBoolean) {
        this.stopBoolean = stopBoolean;
    }
}
