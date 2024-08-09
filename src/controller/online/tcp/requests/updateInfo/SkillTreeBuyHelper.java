package controller.online.tcp.requests.updateInfo;


public class SkillTreeBuyHelper {

    int xpCost;
    SkillTreeAbilityType abilityType;
    String hash = "";

    public SkillTreeBuyHelper(int xpCost ,SkillTreeAbilityType abilityType) {
        this.abilityType = abilityType;
        this.xpCost = xpCost;
    }

    public int getXpCost() {
        return xpCost;
    }

    public void setXpCost(int xpCost) {
        this.xpCost = xpCost;
    }

    public SkillTreeAbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(SkillTreeAbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
