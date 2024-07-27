package model.inGameAbilities;

import constants.RefreshRateConstants;
import constants.TimeConstants;
import controller.gameController.enums.InGameAbilityType;
import controller.gameController.manager.loading.SkippedByJson;
import controller.gameController.manager.GameState;
import model.viewRequests.ShootRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Slaughter extends InGameAbility{

    private int timePassed;
    private boolean isUsed;
    @SkippedByJson
    private Timer timer;

    public Slaughter(){
        type = InGameAbilityType.slaughter;
        xpCost = 200;
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameState.isPause())
                    return;
                timePassed += RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE;
                if (timePassed >= TimeConstants.SLAUGHTER_COOLDOWN){
                    isAvailable = true;
                    isActive = false;
                    timePassed = 0;
                    timer.stop();
                }
            }
        });
    }


    @Override
    public void performAbility() {
        isActive = true;
        isAvailable = false;
        isUsed = false;
        ShootRequest.setSlaughterBulletCount(1);
        timer.start();
    }

    @Override
    public void setUp() {
        initTimer();
        if (timePassed <= TimeConstants.SLAUGHTER_COOLDOWN && isActive){
            if (!isUsed){
                ShootRequest.setSlaughterBulletCount(
                        ShootRequest.getSlaughterBulletCount() + 1
                );
            }
            timer.start();
        }
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }
}
