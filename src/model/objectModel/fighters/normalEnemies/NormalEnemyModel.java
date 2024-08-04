package model.objectModel.fighters.normalEnemies;

import constants.RefreshRateConstants;
import controller.game.Game;
import controller.game.GameData;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.objectModel.fighters.EnemyModel;
import utils.Math;

import java.util.ArrayList;

public abstract class NormalEnemyModel extends EnemyModel implements IsPolygon, HasVertices {

    public NormalEnemyModel(Game game) {
        super(game);
    }

    public void move() {
        if (game.getGameState().isDizzy())
            return;

        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
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
