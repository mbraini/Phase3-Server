package model.objectModel.projectiles;

import constants.DamageConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import controller.game.enums.ModelType;
import model.interfaces.collisionInterfaces.IsCircle;
import utils.Math;
import utils.Vector;

public class WyrmBulletModel extends EnemyBulletModel implements IsCircle {

    public WyrmBulletModel(Vector position , Vector direction , String id){
        this.position = position;
        this.velocity = Math.VectorWithSize(direction , VelocityConstants.WYRM_BULLET_VELOCITY);
        this.acceleration = new Vector(0 ,0);
        type = ModelType.wyrmBullet;
        damage = DamageConstants.WYRM_RANGE_DAMAGE;
        setSolid(false);
        this.id = id;
        this.HP = 1;
    }

    @Override
    public double getRadios() {
        return SizeConstants.WYRM_BULLET_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }
}
