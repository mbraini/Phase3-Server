package model.objectModel.fighters.basicEnemies;

import constants.RefreshRateConstants;
import controller.game.Game;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.interfaces.movementIntefaces.MoveAble;
import model.objectModel.fighters.EnemyModel;
import utils.Math;

import java.util.ArrayList;

public abstract class BasicEnemyModel extends EnemyModel implements MoveAble ,HasVertices, IsPolygon {
    public BasicEnemyModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers) {
        super(game ,chasingPlayer ,targetedPlayers);
    }

    @Override
    public void move() {
        if (game.getGameState().isDizzy())
            return;

        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        movementManager.manage(RefreshRateConstants.UPS ,this);
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);


        omega += alpha * RefreshRateConstants.UPS;
        double thetaMoved = ((2 * omega - alpha * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        theta = theta + thetaMoved;
        UpdateVertices(xMoved ,yMoved ,thetaMoved);
    }

    public abstract void initVertices();


}
