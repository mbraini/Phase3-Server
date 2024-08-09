package controller.online.tcp.messages.giveStats;

public class StatsHelper {

    private String username;
    private int survivalTime;
    private int successfulBullets;
    private int totalBullets;
    private int xpGained;
    private int mostXpGained;
    private int mostSurvivalTime;

    public int getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(int survivalTime) {
        this.survivalTime = survivalTime;
    }

    public int getSuccessfulBullets() {
        return successfulBullets;
    }

    public void setSuccessfulBullets(int successfulBullets) {
        this.successfulBullets = successfulBullets;
    }

    public int getTotalBullets() {
        return totalBullets;
    }

    public void setTotalBullets(int totalBullets) {
        this.totalBullets = totalBullets;
    }

    public int getXpGained() {
        return xpGained;
    }

    public void setXpGained(int xpGained) {
        this.xpGained = xpGained;
    }

    public int getMostXpGained() {
        return mostXpGained;
    }

    public void setMostXpGained(int mostXpGained) {
        this.mostXpGained = mostXpGained;
    }

    public int getMostSurvivalTime() {
        return mostSurvivalTime;
    }

    public void setMostSurvivalTime(int mostSurvivalTime) {
        this.mostSurvivalTime = mostSurvivalTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
