package model.objectModel.effects;

import controller.game.Game;
import controller.game.player.Player;

import java.util.ArrayList;

public abstract class AoeEffectModel extends EffectModel{
    protected double damage;
    protected double damageTime;
    protected ArrayList<Player> targetedPlayers;

    public AoeEffectModel(Game game ,ArrayList<Player> targetedPlayers) {
        super(game);
        this.targetedPlayers = targetedPlayers;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamageTime() {
        return damageTime;
    }

    public void setDamageTime(double damageTime) {
        this.damageTime = damageTime;
    }

    public ArrayList<Player> getTargetedPlayers() {
        return targetedPlayers;
    }

    public void setTargetedPlayers(ArrayList<Player> targetedPlayers) {
        this.targetedPlayers = targetedPlayers;
    }
}
