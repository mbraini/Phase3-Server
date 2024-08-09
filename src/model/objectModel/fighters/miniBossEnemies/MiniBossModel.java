package model.objectModel.fighters.miniBossEnemies;

import controller.game.onlineGame.Game;
import controller.game.player.Player;
import model.objectModel.fighters.EnemyModel;

import java.util.ArrayList;

public abstract class MiniBossModel extends EnemyModel {

    public MiniBossModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers) {
        super(game ,chasingPlayer ,targetedPlayers);
    }
}
