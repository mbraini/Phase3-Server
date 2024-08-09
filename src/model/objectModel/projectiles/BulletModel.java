package model.objectModel.projectiles;

import constants.RefreshRateConstants;
import controller.game.onlineGame.Game;
import controller.game.onlineGame.ObjectController;
import controller.game.player.Player;
import model.interfaces.movementIntefaces.MoveAble;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;

import java.util.ArrayList;

public abstract class BulletModel extends ProjectileModel implements MoveAble {
    protected double hp = 1;
    protected ArrayList<Player> targetedPlayers;
    public BulletModel(Game game , ArrayList<Player> targetedPlayers) {
        super(game);
        this.targetedPlayers = targetedPlayers;
    }

    @Override
    public void move() {
        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);
    }

    @Override
    public void die() {
        ObjectController.removeObject(this);
    }

    public boolean isTargeting(EpsilonModel epsilonModel) {
        synchronized (targetedPlayers) {
            for (Player player : targetedPlayers) {
                if (player.getPlayerData().getEpsilon().getId().equals(epsilonModel.getId()))
                    return true;
            }
        }
        return false;
    }

}
