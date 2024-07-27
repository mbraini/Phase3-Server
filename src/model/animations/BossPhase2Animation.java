package model.animations;

import controller.gameController.manager.GameState;
import model.objectModel.fighters.finalBoss.Boss;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BossPhase2Animation extends TimerAnimation {

    private Boss boss;
    private boolean isDone;
    private Timer timer;

    public BossPhase2Animation(Boss boss) {
        this.boss = boss;
        setUpTimer();
    }

    @Override
    public void StartAnimation() {
        GameState.setIsInAnimation(true);
        boss.spawnPunch();
        timer.start();
    }

    private void setUpTimer() {
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameState.setIsInAnimation(false);
                timer.stop();
            }
        });
    }


}
