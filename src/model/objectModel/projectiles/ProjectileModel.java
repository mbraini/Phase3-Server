package model.objectModel.projectiles;

import controller.game.onlineGame.Game;
import model.objectModel.ObjectModel;

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
