package controller.online.tcp.requests.updateInfo;


public class InGameAbilityBuyHelper {

    InGameAbilityType abilityType;
    int cost;

    public InGameAbilityBuyHelper(InGameAbilityType abilityType ,int cost) {
        this.abilityType = abilityType;
        this.cost = cost;
    }

    public InGameAbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(InGameAbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
