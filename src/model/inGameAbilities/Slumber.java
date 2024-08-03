package model.inGameAbilities;

import constants.RefreshRateConstants;
import constants.TimeConstants;
import controller.game.enums.InGameAbilityType;
import controller.game.manager.GameState;
import controller.online.annotations.SkippedByJson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Slumber extends InGameAbility{

    private int timePassed;
    @SkippedByJson
    private Timer timer;

    public Slumber(){
        type = InGameAbilityType.slumber;
        xpCost = 150;
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getGame().getGameState().isPause())
                    return;
                timePassed += RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE;
                if (timePassed >= TimeConstants.SLUMBER_DURATION){
                    isAvailable = true;
                    isActive = false;
                    timePassed = 0;
                    player.getGame().getGameState().setDizzy(false);
                    InGameAbilityHandler.permitAll();
                    timer.stop();
                }
            }
        });
    }


    @Override
    public void performAbility() {
        isActive = true;
        isAvailable = false;
        player.getGame().getGameState().setDizzy(true);
        InGameAbilityHandler.disableAll();
        timer.start();
    }

    @Override
    public void setUp() {
        initTimer();
        if (timePassed <= TimeConstants.SLUMBER_DURATION && isActive) {
            player.getGame().getGameState().setDizzy(true);
            timer.start();
        }
    }

    public int getTimePassed() {
        return timePassed;
    }
}
