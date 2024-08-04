package model.objectModel.fighters;

import controller.game.Game;
import controller.game.enums.AbstractEnemyType;

public abstract class AbstractEnemy {
    protected String id;
    protected AbstractEnemyType type;
    protected Game game;

    public AbstractEnemy(Game game) {
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AbstractEnemyType getType() {
        return type;
    }

    public void setType(AbstractEnemyType type) {
        this.type = type;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
