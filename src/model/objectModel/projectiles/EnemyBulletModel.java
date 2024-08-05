package model.objectModel.projectiles;


import controller.game.Game;
import controller.game.player.Player;

import java.util.ArrayList;

public class EnemyBulletModel extends BulletModel{

    public EnemyBulletModel(Game game , ArrayList<Player> targetedPlayers) {
        super(game ,targetedPlayers);
    }

    @Override
    public void die() {
        super.die();
    }
}
