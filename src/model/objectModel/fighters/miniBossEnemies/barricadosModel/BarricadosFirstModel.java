package model.objectModel.fighters.miniBossEnemies.barricadosModel;

import controller.game.Game;
import controller.game.enums.ModelType;
import utils.Vector;

public class BarricadosFirstModel extends BarricadosModel {

    public BarricadosFirstModel(Game game ,Vector position , String id){
        super(game);
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
