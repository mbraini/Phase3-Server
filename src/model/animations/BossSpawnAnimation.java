package model.animations;

import constants.SizeConstants;
import controller.gameController.manager.GameState;
import model.ModelData;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BossSpawnAnimation extends TimerAnimation {

    private Boss boss;
    private boolean isDone;
    private Timer timer;

    public BossSpawnAnimation(Boss boss) {
        this.boss = boss;
        setUpTimer();
    }

    private void setUpTimer() {
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boss.spawnLeftHand();
                boss.spawnRightHand();
                GameState.setIsInAnimation(false);
                timer.stop();
            }
        });
    }

    private void headAnimation() {
        Vector direction = findDirection();
        new DashAnimation(
                boss.getHead(),
                direction,
                3000,
                Math.VectorSize(direction),
                0,
                false
        ).StartAnimation();
        timer.start();
    }

    private Vector findDirection() {
        Vector headPosition = boss.getHead().getPosition().clone();
        Vector framePosition = ModelData.getEpsilon().getPosition().clone();
        return Math.VectorAdd(
                Math.ScalarInVector(-1 ,headPosition),
                Math.VectorAdd(
                        framePosition,
                        new Vector(
                                0 ,
                                -ModelData.getEpsilonFrame().getSize().height / 2d - SizeConstants.HEAD_DIMENSION.height / 2d
                                - SizeConstants.barD.height - 1
                        )
                )
        );
    }

    private void setUp() {
        FrameModel epsilonFrame = ModelData.getEpsilonFrame();
        epsilonFrame.transfer(
                new Vector(
                        SizeConstants.SCREEN_SIZE.width / 2d - epsilonFrame.getSize().width / 2d,
                        SizeConstants.SCREEN_SIZE.height / 2d - epsilonFrame.getSize().height / 2d
                )
        );
        ModelData.getEpsilon().setPosition(
                SizeConstants.SCREEN_SIZE.width / 2d,
                SizeConstants.SCREEN_SIZE.height / 2d
        );
    }

    @Override
    public void StartAnimation() {
        GameState.setIsInAnimation(true);
        setUp();
        boss.spawnHead();
        headAnimation();
    }

    public boolean isDone() {
        return isDone;
    }
}
