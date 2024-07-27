package model.objectModel.projectiles;

import model.objectModel.ObjectModel;

public abstract class ProjectileModel extends ObjectModel {
    protected double damage;

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
