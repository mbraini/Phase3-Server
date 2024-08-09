package controller.online.tcp.requests.updateInfo;


public class InGameSkillTreeBuyHelper {

    SkillTreeAbilityType abilityType;
    int cost;

    public InGameSkillTreeBuyHelper(SkillTreeAbilityType abilityType ,int cost){
        this.abilityType = abilityType;
        this.cost = cost;
    }

    public SkillTreeAbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(SkillTreeAbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
