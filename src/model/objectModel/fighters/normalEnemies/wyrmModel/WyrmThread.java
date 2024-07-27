package model.objectModel.fighters.normalEnemies.wyrmModel;

import constants.RefreshRateConstants;
import constants.TimeConstants;
import controller.gameController.manager.GameState;
import utils.Math;
import utils.Vector;

public class WyrmThread extends Thread{
    private final static double thetaD = java.lang.Math.PI / RefreshRateConstants.WYRM_THETA_UPDATE_COUNT;
    private WyrmModel wyrmModel;
    private Vector origin;
    private double time;
    public WyrmThread(WyrmModel wyrmModel , Vector origin){
        this.wyrmModel = wyrmModel;
        this.origin = origin.clone();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double deltaModel = 0;
        while (!GameState.isOver()) {
            if (GameState.isPause() || GameState.isDizzy()){
                lastTime = System.nanoTime();
                continue;
            }
            if (isInterrupted())
                return;
            long now = System.nanoTime();
            deltaModel += (now - lastTime) / ns;
            lastTime = now;
            if (deltaModel >= RefreshRateConstants.WYRM_THREAD_REFRESH_RATE) {
                rotateModel();
                shootIf();
                deltaModel = 0;
                time += RefreshRateConstants.WYRM_THREAD_REFRESH_RATE;
            }
        }
    }

    private void shootIf() {
        if (time % TimeConstants.WYRM_SHOOTING_TIME != 0)
            return;
        if (GameState.isOver())
            return;
        new WyrmShooter(wyrmModel).shoot();
    }

    private void rotateModel() {
        Vector newPosition;
        if (wyrmModel.isPositiveDirection()) {
            newPosition = Math.RotateByTheta(wyrmModel.getPosition(), origin, thetaD);
            wyrmModel.setTheta(wyrmModel.getTheta() + thetaD);
        }
        else {
            newPosition = Math.RotateByTheta(wyrmModel.getPosition() ,origin ,-thetaD);
            wyrmModel.setTheta(wyrmModel.getTheta() - thetaD);
        }
        Vector previousPosition = wyrmModel.getPosition().clone();
        wyrmModel.setPosition(newPosition);
        Vector moved = Math.VectorAdd(
                newPosition,
                Math.ScalarInVector(-1 ,previousPosition)
        );
        wyrmModel.UpdateVertices(moved.x ,moved.y ,thetaD);
    }
}
