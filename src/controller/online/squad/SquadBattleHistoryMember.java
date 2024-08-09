package controller.online.squad;

public class SquadBattleHistoryMember {

    private String mySquadName;
    private String enemySquadName;
    private boolean hasWon;

    public SquadBattleHistoryMember(String mySquadName, String enemySquadName, boolean hasWon) {
        this.mySquadName = mySquadName;
        this.enemySquadName = enemySquadName;
        this.hasWon = hasWon;
    }

    public String getMySquadName() {
        return mySquadName;
    }

    public void setMySquadName(String mySquadName) {
        this.mySquadName = mySquadName;
    }

    public String getEnemySquadName() {
        return enemySquadName;
    }

    public void setEnemySquadName(String enemySquadName) {
        this.enemySquadName = enemySquadName;
    }

    public boolean isHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }
}
