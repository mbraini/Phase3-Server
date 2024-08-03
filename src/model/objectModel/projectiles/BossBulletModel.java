package model.objectModel.projectiles;

import constants.DamageConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import model.interfaces.collisionInterfaces.IsCircle;
import utils.Math;
import utils.Vector;

public class BossBulletModel extends EnemyBulletModel implements IsCircle {

    public BossBulletModel(Vector position , Vector direction , String id){
        this.position = position;
        this.velocity = Math.VectorWithSize(direction , VelocityConstants.OMENOCT_BULLET_VELOCITY);
        this.acceleration = new Vector(0 ,0);
        damage = DamageConstants.OMENOCT_BULLET_DAMAGE;

        this.id = id;
        this.HP = 1;
    }

    @Override
    public double getRadios() {
        return SizeConstants.BOSS_BULLET_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

}
