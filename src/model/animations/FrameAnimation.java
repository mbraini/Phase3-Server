package model.animations;


import model.objectModel.frameModel.FrameModel;
import utils.Vector;

public class FrameAnimation extends ThreadAnimation {
    private FrameModel frame;
    private double time;
    private double upAcceleration;
    private double downAcceleration;
    private double rightAcceleration;
    private double leftAcceleration;

    public FrameAnimation(FrameModel frame , double up ,double down ,double right ,double left ,double time){
        this.frame = frame;
        this.time = time;

        upAcceleration = -2 * up / (Math.pow(this.time ,2));
        downAcceleration = -2 * down / (Math.pow(this.time ,2));
        rightAcceleration = -2 * right / (Math.pow(this.time ,2));
        leftAcceleration = -2 * left / (Math.pow(this.time ,2));
        this.frame.setUpDownV(-this.time * upAcceleration ,-this.time * downAcceleration);
        this.frame.setLeftRightV(-this.time * leftAcceleration ,-this.time * rightAcceleration);
    }


    @Override
    public void StartAnimation() {
        frame.setUpDownA(new Vector(upAcceleration ,downAcceleration));
        frame.setLeftRightA(new Vector(leftAcceleration ,rightAcceleration));
        frame.setResizing(true);
        start();
    }

    @Override
    public void run() {
        try {
            sleep((int)time);
        }
        catch (Exception e) {

        }
        frame.setUpDownA(0 ,0);
        frame.setLeftRightA(0 ,0);
        frame.setUpDownV(0 ,0);
        frame.setLeftRightV(0 ,0);
        frame.setResizing(false);
    }

    public boolean isDone(){
        if (frame.isResizing())
            return false;
        return true;
    }
}
