package model.objectModel.fighters;

import controller.game.onlineGame.Game;
import controller.game.enums.AbstractEnemyType;
import controller.game.player.Player;

import java.util.ArrayList;

public abstract class AbstractEnemy {
    protected String id;
    protected AbstractEnemyType type;
    protected Game game;
    protected Player chasingPlayer;
    protected ArrayList<Player> targetedPlayers;

    public AbstractEnemy(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers) {
        this.game = game;
        this.chasingPlayer = chasingPlayer;
        this.targetedPlayers = targetedPlayers;
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

    public Player getChasingPlayer() {
        return chasingPlayer;
    }

    public void setChasingPlayer(Player chasingPlayer) {
        this.chasingPlayer = chasingPlayer;
    }

    public ArrayList<Player> getTargetedPlayers() {
        return targetedPlayers;
    }

    public void setTargetedPlayers(ArrayList<Player> targetedPlayers) {
        this.targetedPlayers = targetedPlayers;
    }

    public boolean isTargeted(EpsilonModel epsilonModel) {
        synchronized (targetedPlayers) {
            for (Player player : targetedPlayers) {
                if (player.getPlayerData().getEpsilon().getId().equals(epsilonModel.getId()))
                    return true;
            }
        }
        return false;
    }

}
