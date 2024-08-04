package controller.game;

import controller.game.manager.GameState;
import model.inGameAbilities.InGameAbility;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.frameModel.FrameModel;
import model.skillTreeAbilities.SkillTreeAbility;

import java.util.ArrayList;

public class PlayerData {

    private ArrayList<InGameAbility> inGameAbilities;
    private ArrayList<SkillTreeAbility> skillTreeAbilities = new ArrayList<>();
    private EpsilonModel epsilon;
    private FrameModel epsilonFrame;
    private int xp = 1000;
    private double hp;
    private int enemyKilled;
    private int totalBullets;
    private int successfulBullets;
    public EpsilonModel getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(EpsilonModel epsilon) {
        this.epsilon = epsilon;
    }

    public FrameModel getEpsilonFrame() {
        return epsilonFrame;
    }

    public void setEpsilonFrame(FrameModel epsilonFrame) {
        this.epsilonFrame = epsilonFrame;
    }

    public ArrayList<InGameAbility> getInGameAbilities() {
        return inGameAbilities;
    }

    public void setInGameAbilities(ArrayList<InGameAbility> inGameAbilities) {
        this.inGameAbilities = inGameAbilities;
    }

    public ArrayList<SkillTreeAbility> getSkillTreeAbilities() {
        return skillTreeAbilities;
    }

    public void setSkillTreeAbilities(ArrayList<SkillTreeAbility> skillTreeAbilities) {
        this.skillTreeAbilities = skillTreeAbilities;
    }


    public int getEnemyKilled() {
        return enemyKilled;
    }

    public void setEnemyKilled(int enemyKilled) {
        this.enemyKilled = enemyKilled;
    }

    public int getTotalBullets() {
        return totalBullets;
    }

    public void setTotalBullets(int totalBullets) {
        this.totalBullets = totalBullets;
    }

    public int getSuccessfulBullets() {
        return successfulBullets;
    }

    public void setSuccessfulBullets(int successfulBullets) {
        this.successfulBullets = successfulBullets;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }
}
