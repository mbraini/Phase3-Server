package controller.online.client;

public class LeadBoard {


    private int xpEarned;
    private int survivalTime;
    private int totalShots;
    private int successfulShots;

    public LeadBoard(int xpGained, double survivalTime, int totalBullets, int successfulBullets) {
        this.xpEarned = xpGained;
        this.survivalTime = (int) survivalTime;
        this.totalShots = totalBullets;
        this.successfulShots = successfulBullets;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(int survivalTime) {
        this.survivalTime = survivalTime;
    }

    public int getTotalShots() {
        return totalShots;
    }

    public void setTotalShots(int totalShots) {
        this.totalShots = totalShots;
    }

    public int getSuccessfulShots() {
        return successfulShots;
    }

    public void setSuccessfulShots(int successfulShots) {
        this.successfulShots = successfulShots;
    }
}
