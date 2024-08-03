package controller.game.manager;

import constants.VelocityConstants;
import model.ModelData;
import model.objectModel.ObjectModel;

import java.util.ArrayList;

public class GameState {

    private static double time;
    private static int xp = 1000;
    private static double hp;
    private static int wave = 1;
    private static double shrinkageVelocity;
    private static int enemyKilled;
    private static int totalBullets;
    private static int successfulBullets;
    private static int enemyCount;
    private static double lastWaveTime;
    private static int xpGained;
    private static int firstWavePR;
    private static int secondWavePR;
    private static int thirdWavePR;
    private static int forthWavePR;
    private static int fifthWavePR;
    private volatile static boolean isPause;
    private volatile static boolean isOver;
    private volatile static boolean isDizzy;
    private volatile static boolean isInAnimation;

    public GameState(){
        /////todo
    }


    public static void reset(){
        enemyKilled = 0;
        enemyCount = 0;
        successfulBullets = 0;
        totalBullets = 0;
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
        hp = 100;
        wave = 1;
        isPause = false;
        isOver = false;
        isDizzy = false;
        isInAnimation = false;
    }


    public static double getTime() {
        return time;
    }

    public static void setTime(double time) {
        GameState.time = time;
    }

    public static int getXp() {
        return xp;
    }

    public static void setXp(int xp) {
        GameState.xp = xp;
    }

    public static double getHp() {
        return hp;
    }

    public static void setHp(double hp) {
        GameState.hp = hp;
    }

    public static int getWave() {
        return wave;
    }

    public static void setWave(int wave) {
        GameState.wave = wave;
    }

    public static boolean isPause() {
        return isPause;
    }

    public static void setPause(boolean isPause) {
        GameState.isPause = isPause;
    }

    public static boolean isOver() {
        return isOver;
    }

    public static void setOver(boolean isOver) {
        GameState.isOver = isOver;
    }

    public static void update(ArrayList<ObjectModel> models ,double time) {
        setHp(ModelData.getEpsilon().getHP());
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

    public static boolean isDizzy() {
        return isDizzy;
    }

    public static void setDizzy(boolean isDizzy) {
        GameState.isDizzy = isDizzy;
    }

    public static int getEnemyKilled() {
        return enemyKilled;
    }

    public static void setEnemyKilled(int enemyKilled) {
        GameState.enemyKilled = enemyKilled;
    }

    public static int getTotalBullets() {
        return totalBullets;
    }

    public static void setTotalBullets(int totalBullets) {
        GameState.totalBullets = totalBullets;
    }

    public static int getSuccessfulBullets() {
        return successfulBullets;
    }

    public static void setSuccessfulBullets(int successfulBullets) {
        GameState.successfulBullets = successfulBullets;
    }

    public static int getEnemyCount() {
        return enemyCount;
    }

    public static void setEnemyCount(int enemyCount) {
        GameState.enemyCount = enemyCount;
    }

    public static int getXpGained() {
        return xpGained;
    }

    public static void setXpGained(int xpGained) {
        GameState.xpGained = xpGained;
    }

    public static int getFirstWavePR() {
        return firstWavePR;
    }

    public static void setFirstWavePR(int firstWavePR) {
        GameState.firstWavePR = firstWavePR;
    }

    public static int getSecondWavePR() {
        return secondWavePR;
    }

    public static void setSecondWavePR(int secondWavePR) {
        GameState.secondWavePR = secondWavePR;
    }

    public static int getThirdWavePR() {
        return thirdWavePR;
    }

    public static void setThirdWavePR(int thirdWavePR) {
        GameState.thirdWavePR = thirdWavePR;
    }

    public static int getForthWavePR() {
        return forthWavePR;
    }

    public static void setForthWavePR(int forthWavePR) {
        GameState.forthWavePR = forthWavePR;
    }

    public static int getFifthWavePR() {
        return fifthWavePR;
    }

    public static void setFifthWavePR(int fifthWavePR) {
        GameState.fifthWavePR = fifthWavePR;
    }

    public static int getAllPR() {
        return firstWavePR + secondWavePR + thirdWavePR + forthWavePR + fifthWavePR;
    }

    public static double getLastWaveTime() {
        return lastWaveTime;
    }

    public static void setLastWaveTime(double lastWaveTime) {
        GameState.lastWaveTime = lastWaveTime;
    }

    public static void setIsInAnimation(boolean isInAnimation) {
        GameState.isInAnimation = isInAnimation;
    }

    public static boolean isInAnimation() {
        return isInAnimation;
    }

    public static double getShrinkageVelocity() {
        return shrinkageVelocity;
    }

    public static void setShrinkageVelocity(double shrinkageVelocity) {
        GameState.shrinkageVelocity = shrinkageVelocity;
    }
}
