package model.objectModel;

import controller.game.Game;
import controller.game.player.Player;
import model.objectModel.fighters.EpsilonModel;

import java.util.ArrayList;

public abstract class FighterModel extends ObjectModel{
    protected boolean hasMeleeAttack;
    protected int meleeAttack;
    protected ArrayList<Player> targetedPlayers;

    public FighterModel(Game game ,ArrayList<Player> targetedPlayers) {
        super(game);
        this.targetedPlayers = targetedPlayers;
    }


    public boolean isHasMeleeAttack() {
        return hasMeleeAttack;
    }

    public void setHasMeleeAttack(boolean hasMeleeAttack) {
        this.hasMeleeAttack = hasMeleeAttack;
    }

    public int getMeleeAttack() {
        return meleeAttack;
    }

    public void setMeleeAttack(int meleeAttack) {
        this.meleeAttack = meleeAttack;
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
