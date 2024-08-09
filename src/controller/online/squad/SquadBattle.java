package controller.online.squad;


import java.util.ArrayList;
import java.util.HashMap;

public class SquadBattle {

    private String inBattleWith;
    private int xpEarned;
    private int monomachiaWins;
    private HashMap<String ,Integer> summonMap = new HashMap<>();

    public SquadBattle() {

    }

    public SquadBattle(String inBattleWith , boolean hasAdonis , ArrayList<String> members) {
        this.inBattleWith = inBattleWith;
        if (hasAdonis) {
            for (String member : members) {
                summonMap.put(member, 1);
            }
        }
        else {
            for (String member : members) {
                summonMap.put(member, 0);
            }
        }
    }

    public String getInBattleWith() {
        return inBattleWith;
    }

    public void setInBattleWith(String inBattleWith) {
        this.inBattleWith = inBattleWith;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }

    public int getMonomachiaWins() {
        return monomachiaWins;
    }

    public void setMonomachiaWins(int monomachiaWins) {
        this.monomachiaWins = monomachiaWins;
    }

    public boolean hasSpawn(String username) {
        if (summonMap.get(username) == 1)
            return true;
        else
            return false;
    }

    public void castSpawn(String username) {
        summonMap.remove(username);
        summonMap.put(username ,0);
    }

}
