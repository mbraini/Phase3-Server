package model.objectModel.projectiles;


import constants.SizeConstants;
import constants.VelocityConstants;
import model.ModelData;
import model.interfaces.collisionInterfaces.IsCircle;
import utils.Math;
import utils.Vector;

public class EpsilonBulletModel extends BulletModel implements IsCircle {

    public EpsilonBulletModel(Vector position , Vector direction , String id){
        this.position = position;
        this.velocity = Math.VectorWithSize(direction , VelocityConstants.EPSILON_BULLET_VELOCITY);
        this.acceleration = new Vector(0 ,0);
        damage = ModelData.getEpsilon().getEpsilonBulletDamage();
        setSolid(true);
        this.id = id;
        this.HP = 1;
    }

    @Override
    public double getRadios() {
        return SizeConstants.EPSILON_BULLET_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }


}