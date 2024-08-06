package model.objectModel.fighters.basicEnemies;

import constants.DamageConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import model.ModelData;
import model.interfaces.Ability;
import model.interfaces.movementIntefaces.ImpactAble;
import model.logics.MovementManager;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class TrigorathModel extends BasicEnemyModel implements Ability, ImpactAble {
    private ArrayList<Vector> vertices;
    private boolean isImpacted = false;
    public TrigorathModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers , Vector position , String id){
        super(game ,chasingPlayer ,targetedPlayers);
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.id = id;
        this.HP = 15;
        this.meleeAttack = DamageConstants.TRIGORATH_DAMAGE;
        this.hasMeleeAttack = true;
        this.vulnerableToEpsilonMelee = true;
        this.vulnerableToEpsilonBullet = true;
        movementManager = new MovementManager();
        type = ModelType.trigorath;
        omega = VelocityConstants.ENEMY_ROTATION_SPEED;
        initVertices();
    }

    public void initVertices(){
        vertices = new ArrayList<>();
        vertices.add(new Vector(
                position.x ,
                position.y - (java.lang.Math.sqrt(3) * SizeConstants.TRIGORATH_DIMENTION.width / 3d))
        );
        vertices.add(new Vector(
                position.x - SizeConstants.TRIGORATH_DIMENTION.width / 2d ,
                position.y + (java.lang.Math.sqrt(3) * SizeConstants.TRIGORATH_DIMENTION.width / 6d))
        );
        vertices.add(new Vector(
                position.x + SizeConstants.TRIGORATH_DIMENTION.width / 2d ,
                position.y + (java.lang.Math.sqrt(3) * SizeConstants.TRIGORATH_DIMENTION.width / 6d))
        );
    }

    @Override
    public void UpdateVertices(double xMoved ,double yMoved ,double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(vertices.get(i).getX() + xMoved ,vertices.get(i).getY() + yMoved));
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }


    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }


    @Override
    public void ability() {
        Vector epsilonPosition = chasingPlayer.getPlayerData().getEpsilon().getPosition();
        velocity = new Vector(epsilonPosition.x - getPosition().x ,epsilonPosition.y - getPosition().y);
        double distance = Math.VectorSize(
                Math.VectorAdd(
                        Math.ScalarInVector(-1 ,position) ,
                        game.getModelData().getModels().getFirst().getPosition()
                )
        );
        /////////////todo
        if (distance >= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 160){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 1.6);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 1.6;
        }
        if (distance >= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 280){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 1.4);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 1.4;
        }
        else if (distance >= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 240){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 1.2);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 1.2;
        }
        else if (distance >= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 200){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 1.1);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 1.1;
        }
        else if (distance >= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 160){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED;
        }
        else if (distance<= SizeConstants.TRIGORATH_DIMENTION.width * 2 / 3d + SizeConstants.EPSILON_DIMENSION.width / 2d + 40){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 0.3);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 0.3;
        }
        else if (distance <= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 80){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 0.5);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 0.5;
        }
        else if (distance <= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 120){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 0.8);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 0.8;
        }
        else if (distance <= SizeConstants.TRIGORATH_DIMENTION.width + SizeConstants.EPSILON_DIMENSION.width + 160){
            velocity = Math.VectorWithSize(velocity , VelocityConstants.ENEMY_LINEAR_SPEED * 0.9);
            omega = VelocityConstants.ENEMY_ROTATION_SPEED * 0.9;
        }
        ////////////todo
    }

    @Override
    public boolean hasAbility() {
        return !isImpacted;
    }

    @Override
    public boolean isImpacted() {
        return isImpacted;
    }

    @Override
    public void setImpacted(boolean impact) {
        isImpacted = impact;
    }

    @Override
    public void die() {
        super.die();
        Spawner.addCollectives(game ,targetedPlayers ,position ,2 ,5);
    }
}
