package controller.online.tcp.requests.updateInfo;

import java.util.ArrayList;

public class GameHistoryHelper {

    int xpGained;
    int timePassed;
    ArrayList<InGameAbilityBuyHelper> abilityBuyHelpers;
    ArrayList<InGameSkillTreeBuyHelper> inGameSkillTreeBuyHelpers;
    String hash = "";

    public GameHistoryHelper() {
        abilityBuyHelpers = new ArrayList<>();
        inGameSkillTreeBuyHelpers = new ArrayList<>();
    }

    public int getXpGained() {
        return xpGained;
    }

    public void setXpGained(int xpGained) {
        this.xpGained = xpGained;
    }

    public ArrayList<InGameAbilityBuyHelper> getAbilityBuyHelpers() {
        return abilityBuyHelpers;
    }

    public void setAbilityBuyHelpers(ArrayList<InGameAbilityBuyHelper> abilityBuyHelpers) {
        this.abilityBuyHelpers = abilityBuyHelpers;
    }

    public void addInGameAbilityHistory(InGameAbilityBuyHelper inGameAbilityBuyHelper) {
        abilityBuyHelpers.add(inGameAbilityBuyHelper);
    }

    public void addInGameAbilityHistory(InGameSkillTreeBuyHelper inGameSkillTreeBuyHelper) {
        inGameSkillTreeBuyHelpers.add(inGameSkillTreeBuyHelper);
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ArrayList<InGameSkillTreeBuyHelper> getInGameSkillTreeBuyHelpers() {
        return inGameSkillTreeBuyHelpers;
    }

    public void setInGameSkillTreeBuyHelpers(ArrayList<InGameSkillTreeBuyHelper> inGameSkillTreeBuyHelpers) {
        this.inGameSkillTreeBuyHelpers = inGameSkillTreeBuyHelpers;
    }
}
