package model.objectModel.projectiles;

import controller.game.Game;
import controller.game.player.Player;
import model.objectModel.ObjectModel;

import java.util.ArrayList;

public abstract class ProjectileModel extends ObjectModel {
    protected double damage;

    public ProjectileModel(Game game) {
        super(game);
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
