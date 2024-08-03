package model.logics;

import model.interfaces.movementIntefaces.ImpactAble;
import model.objectModel.ObjectModel;
import model.objectModel.frameModel.FrameModel;

public class MovementManager {

    private int upDownAccTimePassed;
    private int leftRightAccTimePassed;
    private int upDownAccTime;
    private int leftRightAccTime;
    private int rotateAccTime;
    private int rotateAccTimePassed;

    public int getUpDownAccTimePassed() {
        return upDownAccTimePassed;
    }

    public void setUpDownAccTimePassed(int upDownAccTimePassed) {
        this.upDownAccTimePassed = upDownAccTimePassed;
    }

    public int getLeftRightAccTimePassed() {
        return leftRightAccTimePassed;
    }

    public void setLeftRightAccTimePassed(int leftRightAccTimePassed) {
        this.leftRightAccTimePassed = leftRightAccTimePassed;
    }

    public int getUpDownAccTime() {
        return upDownAccTime;
    }

    public void setUpDownAccTime(int upDownAccTime) {
        this.upDownAccTime = upDownAccTime;
    }

    public int getLeftRightAccTime() {
        return leftRightAccTime;
    }

    public void setLeftRightAccTime(int leftRightAccTime) {
        this.leftRightAccTime = leftRightAccTime;
    }

    public int getRotateAccTime() {
        return rotateAccTime;
    }

    public void setRotateAccTime(int rotateAccTime) {
        this.rotateAccTime = rotateAccTime;
    }

    public int getRotateAccTimePassed() {
        return rotateAccTimePassed;
    }

    public void setRotateAccTimePassed(int rotateAccTimePassed) {
        this.rotateAccTimePassed = rotateAccTimePassed;
    }

    public void manage(int time , ObjectModel model) {
        if (model instanceof FrameModel) {
            frameManager(time ,(FrameModel) model);
        }
        else {
            objectManager(time ,model);
        }
    }

    private void frameManager(int time, FrameModel frame) {
        if (upDownAccTime > 0) {
            upDownAccTimePassed += time;
            if (upDownAccTimePassed >= upDownAccTime) {
                upDownAccTime = 0;
                upDownAccTimePassed = 0;
                frame.setUpDownA(0 ,0);
                frame.setUpDownV(0 ,0);
                frame.setResizing(false);
            }
        }
        if (leftRightAccTime > 0) {
            leftRightAccTimePassed += time;
            if (leftRightAccTimePassed >= leftRightAccTime) {
                leftRightAccTime = 0;
                leftRightAccTimePassed = 0;
                frame.setLeftRightA(0 ,0);
                frame.setLeftRightV(0 ,0);
                frame.setResizing(false);
            }
        }

    }

    private void objectManager(int time ,ObjectModel model) {
        if (upDownAccTime > 0) {
            upDownAccTimePassed += time;
            if (upDownAccTimePassed >= upDownAccTime) {
                upDownAccTime = 0;
                upDownAccTimePassed = 0;
                model.setAcceleration(model.getAcceleration().x, 0);
                model.setVelocity(model.getVelocity().x, 0);
                if (model instanceof ImpactAble) {
                    ((ImpactAble) model).setImpacted(false);
                }
            }
        }
        if (leftRightAccTime > 0) {
            leftRightAccTimePassed += time;
            if (leftRightAccTimePassed >= leftRightAccTime) {
                leftRightAccTime = 0;
                leftRightAccTimePassed = 0;
                model.setAcceleration(0, model.getAcceleration().y);
                model.setVelocity(0, model.getVelocity().y);
                if (model instanceof ImpactAble) {
                    ((ImpactAble) model).setImpacted(false);
                }
            }
        }
        if (rotateAccTime > 0) {
            rotateAccTimePassed += time;
            if (rotateAccTimePassed >= rotateAccTime) {
                rotateAccTime = 0;
                rotateAccTimePassed = 0;
                model.setAlpha(0);
                model.setOmega(0);
            }
        }
    }
}
