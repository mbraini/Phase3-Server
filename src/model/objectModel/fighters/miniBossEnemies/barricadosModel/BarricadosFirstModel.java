package model.objectModel.fighters.miniBossEnemies.barricadosModel;

import controller.game.onlineGame.Game;
import controller.game.enums.ModelType;
import controller.game.player.Player;
import utils.Vector;

import java.util.ArrayList;

public class BarricadosFirstModel extends BarricadosModel {

    public BarricadosFirstModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers , Vector position , String id){
        super(game ,chasingPlayer ,targetedPlayers);
        this.position = position;
        this.HP = 10000000;
        this.id = id;
        this.vulnerableToEpsilonBullet = false;
        this.vulnerableToEpsilonMelee = false;
        isMotionless = true;
        type = ModelType.barricadosTheFirst;
        initVertices();
    }

    @Override
    public void die() {
        super.die();
    }
}
