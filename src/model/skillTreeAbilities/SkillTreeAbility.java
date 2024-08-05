package model.skillTreeAbilities;

import constants.RefreshRateConstants;
import controller.game.player.Player;
import controller.game.enums.SkillTreeAbilityType;
import controller.online.annotations.SkippedByJson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SkillTreeAbility {

    protected Player player;
    protected boolean isBought;
    protected int inGameXpCost = 100;
    protected int inGameCoolDownTime = 300000;
    protected boolean canCast = true;
    @SkippedByJson
    protected Timer coolDownTimer;
    protected int coolDownTimePassed;
    protected int unlockXpCost;
    protected SkillTreeAbilityType type;

    public SkillTreeAbility(Player player ,boolean isBought) {
        this.player = player;
        this.isBought = isBought;
    }

    protected abstract void cast();

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public int getInGameXpCost() {
        return inGameXpCost;
    }

    public void setInGameXpCost(int inGameXpCost) {
        this.inGameXpCost = inGameXpCost;
    }

    public int getInGameCoolDownTime() {
        return inGameCoolDownTime;
    }

    public void setInGameCoolDownTime(int inGameCoolDownTime) {
        this.inGameCoolDownTime = inGameCoolDownTime;
    }

    public boolean CanCast() {
        return canCast;
    }

    public void setCanCast(boolean canCast) {
        this.canCast = canCast;
    }

    public Timer getCoolDownTimer() {
        return coolDownTimer;
    }

    public void setCoolDownTimer(Timer coolDownTimer) {
        this.coolDownTimer = coolDownTimer;
    }

    public int getCoolDownTimePassed() {
        return coolDownTimePassed;
    }

    public void setCoolDownTimePassed(int coolDownTimePassed) {
        this.coolDownTimePassed = coolDownTimePassed;
    }

    public int getUnlockXpCost() {
        return unlockXpCost;
    }

    public void setUnlockXpCost(int unlockXpCost) {
        this.unlockXpCost = unlockXpCost;
    }

    public SkillTreeAbilityType getType() {
        return type;
    }

    public void setType(SkillTreeAbilityType type) {
        this.type = type;
    }

    protected void stop() {
        coolDownTimer.stop();
        canCast = true;
    }

    public void setUp(){
        initTimer();
        if (coolDownTimePassed >= 0 && !canCast) {
            coolDownTimer.start();
        }
    }

    protected void initTimer() {
        coolDownTimer = new Timer(RefreshRateConstants.SKILL_TREE_ABILITY_TIMER_REFRESH_RATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getGame().getGameState().isPause())
                    return;
                if (player.getGame().getGameState().isOver())
                    coolDownTimer.stop();
                coolDownTimePassed += RefreshRateConstants.SKILL_TREE_ABILITY_TIMER_REFRESH_RATE;
                if (coolDownTimePassed >= inGameCoolDownTime){
                    canCast = true;
                    coolDownTimePassed = 0;
                    coolDownTimer.stop();
                }
            }
        });
    }

}
