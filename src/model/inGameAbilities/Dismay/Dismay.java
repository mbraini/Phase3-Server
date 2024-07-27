package model.inGameAbilities.Dismay;

import constants.RefreshRateConstants;
import constants.TimeConstants;
import controller.gameController.enums.InGameAbilityType;
import controller.gameController.manager.Spawner;
import controller.gameController.manager.loading.SkippedByJson;
import constants.ControllerConstants;
import controller.gameController.manager.GameState;
import model.ModelData;
import model.inGameAbilities.InGameAbility;
import model.objectModel.fighters.EpsilonModel;
import utils.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dismay extends InGameAbility {

    private int timePassed;
    private EpsilonProtectorModel protectorModel;
    private EpsilonModel epsilon;
    @SkippedByJson
    private Timer timer;

    public Dismay(EpsilonModel epsilon){
        this.epsilon = epsilon;
        type = InGameAbilityType.dismay;
        xpCost = 120;
        initTimer();
        initProtector();
    }

    private void initProtector() {
        protectorModel = new EpsilonProtectorModel(
                epsilon,
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
    }

    private void initTimer() {
        timer = new Timer(RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameState.isPause())
                    return;
                timePassed += RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE;
                if (timePassed >= TimeConstants.DISMAY_DURATION){
                    isAvailable = true;
                    isActive = false;
                    timePassed = 0;
                    protectorModel.die();
                    timer.stop();
                }
            }
        });
    }

    @Override
    public void performAbility() {
        Spawner.spawnProtector(protectorModel);
        isActive = true;
        isAvailable = false;
        timer.start();
    }

    @Override
    public void setUp() {
        initTimer();
        this.epsilon =ModelData.getEpsilon();
        protectorModel.setEpsilon(epsilon);
        if (timePassed <= TimeConstants.DISMAY_DURATION && isActive) {
            Spawner.spawnProtector(protectorModel);
            timer.start();
        }
    }
}
