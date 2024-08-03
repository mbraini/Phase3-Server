package model.logics;


import model.objectModel.frameModel.FrameModel;
import utils.Vector;

public class FrameHitPerformer {
    private FrameModel frame;
    private double time;
    private double upAcceleration;
    private double downAcceleration;
    private double rightAcceleration;
    private double leftAcceleration;

    public FrameHitPerformer(FrameModel frame , double up , double down , double right , double left , double time){
        this.frame = frame;
        this.time = time;

        upAcceleration = -2 * up / (Math.pow(this.time ,2));
        downAcceleration = -2 * down / (Math.pow(this.time ,2));
        rightAcceleration = -2 * right / (Math.pow(this.time ,2));
        leftAcceleration = -2 * left / (Math.pow(this.time ,2));
        this.frame.setUpDownV(-this.time * upAcceleration ,-this.time * downAcceleration);
        this.frame.setLeftRightV(-this.time * leftAcceleration ,-this.time * rightAcceleration);
    }


    public void frameHit() {
        frame.setUpDownA(new Vector(upAcceleration ,downAcceleration));
        frame.setLeftRightA(new Vector(leftAcceleration ,rightAcceleration));
        frame.getMovementManager().setLeftRightAccTime((int) time);
        frame.getMovementManager().setLeftRightAccTimePassed(0);
        frame.getMovementManager().setUpDownAccTime((int) time);
        frame.getMovementManager().setUpDownAccTimePassed(0);
        frame.setResizing(true);
    }
}
