package model.inGameAbilities;

import constants.RefreshRateConstants;
import constants.TimeConstants;
import controller.game.enums.InGameAbilityType;
import controller.game.manager.GameState;
import controller.online.annotations.SkippedByJson;
import model.viewRequests.ShootRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Empower extends InGameAbility{
    @SkippedByJson
    private Timer timer;
    private int timePassed;

    public Empower(){
        type = InGameAbilityType.empower;
        xpCost = 75;
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getGame().getGameState().isPause())
                    return;
                timePassed += RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE;
                if (timePassed >= TimeConstants.EMPOWER_DURATION) {
                    isAvailable = true;
                    isActive = false;
                    ShootRequest.setExtraAim(0);
                    timePassed = 0;
                    timer.stop();
                }
            }
        });
    }

    @Override
    public void performAbility() {
        ShootRequest.setExtraAim(2);
        isActive = true;
        isAvailable = false;
        timer.start();
    }

    @Override
    public void setUp() {
        initTimer();
        if (timePassed <= TimeConstants.EMPOWER_DURATION && isActive) {
            ShootRequest.setExtraAim(2);
            timer.start();
        }
    }
}
