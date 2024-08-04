package model.objectModel.effects;

import controller.game.Game;

public abstract class AoeEffectModel extends EffectModel{
    protected double damage;
    protected double damageTime;


    public AoeEffectModel(Game game) {
        super(game);
    }
}
