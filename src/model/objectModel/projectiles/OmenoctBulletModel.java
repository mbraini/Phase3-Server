package model.objectModel.projectiles;

import constants.DamageConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.game.player.Player;
import model.interfaces.collisionInterfaces.IsCircle;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class OmenoctBulletModel extends EnemyBulletModel implements IsCircle {

    public OmenoctBulletModel(Game game, ArrayList<Player> targetedPlayers , Vector position , Vector direction , String id){
        super(game ,targetedPlayers);
        this.position = position;
        this.velocity = Math.VectorWithSize(direction , VelocityConstants.OMENOCT_BULLET_VELOCITY * game.getGameSpeed());
        this.acceleration = new Vector(0 ,0);
        damage = DamageConstants.OMENOCT_BULLET_DAMAGE;
        setSolid(false);
        type = ModelType.omenoctBullet;

        this.id = id;
        this.HP = 1;
    }

    @Override
    public double getRadios() {
        return SizeConstants.OMENOCT_BULLET_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

}
