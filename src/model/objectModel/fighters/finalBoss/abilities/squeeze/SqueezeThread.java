package model.objectModel.fighters.finalBoss.abilities.squeeze;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.TimeConstants;
import controller.game.manager.GameState;
import utils.Math;
import utils.Vector;

public class SqueezeThread extends Thread {

    private Squeeze squeeze;
    private double time;

    public SqueezeThread(Squeeze squeeze){
        this.squeeze = squeeze;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double deltaModel = 0;
        while (!GameState.isOver() && !isInterrupted()) {
            if (GameState.isPause() || GameState.isDizzy()){
                lastTime = System.nanoTime();
                continue;
            }
            if (GameState.isInAnimation()) {
                squeeze.endAbility();
                return;
            }
            long now = System.nanoTime();
            deltaModel += (now - lastTime) / ns;
            lastTime = now;
            if (deltaModel >= RefreshRateConstants.SQUEEZE_THREAD_REFRESH_RATE) {
                squeeze();
                deltaModel = 0;
                time += RefreshRateConstants.SQUEEZE_THREAD_REFRESH_RATE;
            }
        }
    }

    private void squeeze() {
        if (time >= TimeConstants.SQUEEZE_DURATION) {
            squeeze.endAbility();
            return;
        }
        if (!squeeze.hasArrived()){
            squeeze.navigate();
            squeeze.checkArrived();
        }
        else {
            placeHands();
        }
    }

    private void placeHands() {
        Vector lastLeftPosition = squeeze.getBoss().getLeftHand().getPosition().clone();
        Vector lastRightPosition = squeeze.getBoss().getRightHand().getPosition().clone();
        Vector leftFrameCenter = new Vector(
                squeeze.getEpsilonFrame().getPosition().x,
                squeeze.getEpsilonFrame().getPosition().y + squeeze.getEpsilonFrame().getSize().height / 2d
        );
        Vector rightFrameCenter = new Vector(
                squeeze.getEpsilonFrame().getPosition().x + squeeze.getEpsilonFrame().getSize().width,
                squeeze.getEpsilonFrame().getPosition().y + squeeze.getEpsilonFrame().getSize().height / 2d
        );
        Vector leftHandPlacer = Math.VectorAdd(
                leftFrameCenter,
                new Vector(
                        -SizeConstants.HAND_DIMENSION.width / 2d - 2,
                        0
                )
        );

        Vector rightHandPlacer = Math.VectorAdd(
                rightFrameCenter,
                new Vector(
                        SizeConstants.HAND_DIMENSION.width / 2d + 2,
                        0
                )
        );
        squeeze.getBoss().getLeftHand().setPosition(leftHandPlacer);
        squeeze.getBoss().getRightHand().setPosition(rightHandPlacer);
        Vector newLeftPosition = squeeze.getBoss().getLeftHand().getPosition().clone();
        Vector newRightPosition = squeeze.getBoss().getRightHand().getPosition().clone();
        squeeze.getBoss().getLeftHand().UpdateVertices(
                newLeftPosition.x - lastLeftPosition.x,
                newLeftPosition.y - lastLeftPosition.y,
                0
        );
        squeeze.getBoss().getRightHand().UpdateVertices(
                newRightPosition.x - lastRightPosition.x,
                newRightPosition.y - lastRightPosition.y,
                0
        );
    }
}
