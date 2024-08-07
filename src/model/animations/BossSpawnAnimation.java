package model.animations;

import constants.SizeConstants;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.ModelData;
import model.logics.Dash;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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
                boss.getGame().getGameState().setIsInAnimation(false);
                timer.stop();
            }
        });
    }

    private void headAnimation() {
        Vector direction = findDirection();
        new Dash(
                boss.getHead(),
                direction,
                3000,
                Math.VectorSize(direction),
                0,
                false
        ).startDash();
        timer.start();
    }

    private Vector findDirection() {
        Vector headPosition = boss.getHead().getPosition().clone();
        FrameModel epsilonFrame;
        synchronized (boss.getGame().getModelData().getFrames()) {
            epsilonFrame = boss.getGame().getModelData().getFrames().getFirst();
        }
        return Math.VectorAdd(
                Math.ScalarInVector(-1 ,headPosition),
                Math.VectorAdd(
                        epsilonFrame.getPosition(),
                        new Vector(
                                0 ,
                                -epsilonFrame.getSize().height / 2d - SizeConstants.HEAD_DIMENSION.height / 2d
                                - SizeConstants.barD.height - 1
                        )
                )
        );
    }

    private void setUp() {
        FrameModel epsilonFrame;
        synchronized (boss.getGame().getModelData().getFrames()) {
            epsilonFrame = boss.getGame().getModelData().getFrames().getFirst();
        }
        epsilonFrame.transfer(
                new Vector(
                        SizeConstants.SCREEN_SIZE.width / 2d - epsilonFrame.getSize().width / 2d,
                        SizeConstants.SCREEN_SIZE.height / 2d - epsilonFrame.getSize().height / 2d
                )
        );
        synchronized (boss.getGame().getPlayers()) {
            Random random = new Random();
            Vector middle = new Vector(
                    SizeConstants.SCREEN_SIZE.width / 2d,
                    SizeConstants.SCREEN_SIZE.height / 2d
            );
            for (Player player : boss.getGame().getPlayers()) {
                Vector randomVector = new Vector(
                        random.nextInt(-100 ,100),
                        random.nextInt(-100 ,100)
                );
                player.getPlayerData().getEpsilon().setPosition(
                        Math.VectorAdd(middle ,randomVector)
                );
            }
        }
    }

    @Override
    public void StartAnimation() {
        boss.getGame().getGameState().setIsInAnimation(true);
        setUp();
        boss.spawnHead();
        headAnimation();
    }

    public boolean isDone() {
        return isDone;
    }
}
