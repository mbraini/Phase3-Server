package model.objectModel.fighters.miniBossEnemies.barricadosModel;

import controller.gameController.enums.ModelType;
import utils.Vector;

public class BarricadosFirstModel extends BarricadosModel {

    public BarricadosFirstModel(Vector position ,String id){
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
