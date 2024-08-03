package model.objectModel.fighters;

import controller.game.enums.AbstractEnemyType;

public abstract class AbstractEnemy {
    protected String id;
    protected AbstractEnemyType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
