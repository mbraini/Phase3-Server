package model.inGameAbilities.Dismay;

import constants.ControllerConstants;
import constants.RefreshRateConstants;
import constants.TimeConstants;
import controller.game.enums.InGameAbilityType;
import controller.game.manager.GameState;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
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

    public Dismay(Player player){
        super(player);
        this.epsilon = player.getPlayerData().getEpsilon();
        type = InGameAbilityType.dismay;
        xpCost = 120;
        initTimer();
        initProtector();
    }

    private void initProtector() {
        protectorModel = new EpsilonProtectorModel(
                player.getGame(),
                epsilon,
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
    }

    private void initTimer() {
        timer = new Timer(RefreshRateConstants.IN_GAME_ABILITY_TIMER_REFRESH_RATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getGame().getGameState().isPause())
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
        this.epsilon =player.getPlayerData().getEpsilon();
        protectorModel.setEpsilon(epsilon);
        if (timePassed <= TimeConstants.DISMAY_DURATION && isActive) {
            Spawner.spawnProtector(protectorModel);
            timer.start();
        }
    }
}
