package model.inGameAbilities;

import controller.game.player.Player;
import controller.game.enums.InGameAbilityType;
import controller.online.annotations.SkippedByJson;

public abstract class InGameAbility {

    protected InGameAbilityType type;
    protected boolean isAvailable = true;
    protected boolean isActive = false;
    protected int xpCost;
    @SkippedByJson
    protected Player player;

    public InGameAbility(Player player) {
        this.player = player;
    }

    public abstract void performAbility();
    public abstract void setUp();

    public InGameAbilityType getType() {
        return type;
    }

    public void setType(InGameAbilityType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getXpCost() {
        return xpCost;
    }

    public void setXpCost(int xpCost) {
        this.xpCost = xpCost;
    }
}
