package controller.game.manager;

import constants.VelocityConstants;
import controller.game.Game;
import model.ModelData;
import model.objectModel.ObjectModel;

import java.util.ArrayList;

public class GameState {

    private double time;
    private int wave = 1;
    private int enemyKilled;
    private double shrinkageVelocity;
    private int enemyCount;
    private double lastWaveTime;
    private int xpGained;
    private int firstWavePR;
    private int secondWavePR;
    private int thirdWavePR;
    private int forthWavePR;
    private int fifthWavePR;
    private volatile boolean isPause;
    private volatile boolean isOver;
    private volatile boolean isDizzy;
    private volatile boolean isInAnimation;
    private Game game;

    public GameState(Game game){
        this.game = game;
    }


    public void reset(){
        enemyKilled = 0;
        enemyCount = 0;
        lastWaveTime = 0;
        xpGained = 0;
        firstWavePR = 0;
        secondWavePR = 0;
        thirdWavePR = 0;
        forthWavePR = 0;
        fifthWavePR = 0;
        shrinkageVelocity = VelocityConstants.FRAME_SHRINKAGE_VELOCITY;
        WaveSpawner.repeatedCount = 0;
        time = 0;
        wave = 1;
        isPause = false;
        isOver = false;
        isDizzy = false;
        isInAnimation = false;
    }


    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    public void update(ArrayList<ObjectModel> models ,double time) {
        setTime(getTime() + time);
        if (wave == 1) {
            firstWavePR = (int) (getTime() - lastWaveTime) * wave / 1000;
        }
        else if (wave == 2) {
            secondWavePR = (int) (getTime() - lastWaveTime) * wave / 1000;
        }
        else if (wave == 3) {
            thirdWavePR = (int) (getTime() - lastWaveTime) * wave / 1000;
        }
        else if (wave == 4) {
            forthWavePR = (int) (getTime() - lastWaveTime) * wave / 1000;
        }
        else if (wave == 5) {
            fifthWavePR = (int) (getTime() - lastWaveTime) * wave / 1000;
        }
    }

    public boolean isDizzy() {
        return isDizzy;
    }

    public void setDizzy(boolean isDizzy) {
        this.isDizzy = isDizzy;
    }

    public int getEnemyKilled() {
        return enemyKilled;
    }

    public void setEnemyKilled(int enemyKilled) {
        this.enemyKilled = enemyKilled;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public int getXpGained() {
        return xpGained;
    }

    public void setXpGained(int xpGained) {
        this.xpGained = xpGained;
    }

    public int getFirstWavePR() {
        return firstWavePR;
    }

    public void setFirstWavePR(int firstWavePR) {
        this.firstWavePR = firstWavePR;
    }

    public int getSecondWavePR() {
        return secondWavePR;
    }

    public void setSecondWavePR(int secondWavePR) {
        this.secondWavePR = secondWavePR;
    }

    public int getThirdWavePR() {
        return thirdWavePR;
    }

    public void setThirdWavePR(int thirdWavePR) {
        this.thirdWavePR = thirdWavePR;
    }

    public int getForthWavePR() {
        return forthWavePR;
    }

    public void setForthWavePR(int forthWavePR) {
        this.forthWavePR = forthWavePR;
    }

    public int getFifthWavePR() {
        return fifthWavePR;
    }

    public void setFifthWavePR(int fifthWavePR) {
        this.fifthWavePR = fifthWavePR;
    }

    public int getAllPR() {
        return firstWavePR + secondWavePR + thirdWavePR + forthWavePR + fifthWavePR;
    }

    public double getLastWaveTime() {
        return lastWaveTime;
    }

    public void setLastWaveTime(double lastWaveTime) {
        this.lastWaveTime = lastWaveTime;
    }

    public void setIsInAnimation(boolean isInAnimation) {
        this.isInAnimation = isInAnimation;
    }

    public boolean isInAnimation() {
        return isInAnimation;
    }

    public double getShrinkageVelocity() {
        return shrinkageVelocity;
    }

    public void setShrinkageVelocity(double shrinkageVelocity) {
        this.shrinkageVelocity = shrinkageVelocity;
    }
}
