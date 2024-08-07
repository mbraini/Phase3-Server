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
import model.logics.Dash;
import model.logics.MovementManager;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class SquarantineModel extends BasicEnemyModel implements Ability , ImpactAble {
    private ArrayList<Vector> vertices;
    private boolean isImpacted = false;
    public SquarantineModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers , Vector position , String id){
        super(game ,chasingPlayer ,targetedPlayers);
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.id = id;
        this.HP = 10;
        this.meleeAttack = DamageConstants.SQURANTINE_DAMAGE;
        this.hasMeleeAttack = true;
        this.vulnerableToEpsilonMelee = true;
        this.vulnerableToEpsilonBullet = true;
        movementManager = new MovementManager();
        type = ModelType.squarantine;
        omega = VelocityConstants.ENEMY_ROTATION_SPEED * game.getGameSpeed();
        initVertices();
    }

    public void initVertices(){
        vertices = new ArrayList<>();
        vertices.add(new Vector(
                position.x + (SizeConstants.Squarantine_DIMENTION.width / 2d) ,
                position.y + (SizeConstants.Squarantine_DIMENTION.height / 2d))
        );
        vertices.add(new Vector(
                position.x + (SizeConstants.Squarantine_DIMENTION.width / 2d) ,
                position.y - (SizeConstants.Squarantine_DIMENTION.height / 2d))
        );
        vertices.add(new Vector(
                position.x - (SizeConstants.Squarantine_DIMENTION.width / 2d) ,
                position.y - (SizeConstants.Squarantine_DIMENTION.height / 2d))
        );
        vertices.add(new Vector(
                position.x - (SizeConstants.Squarantine_DIMENTION.width / 2d) ,
                position.y + (SizeConstants.Squarantine_DIMENTION.height / 2d))
        );
    }

    @Override
    public void UpdateVertices(double xMoved ,double yMoved ,double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(
                    vertices.get(i).getX() + xMoved ,
                    vertices.get(i).getY() + yMoved)
            );
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    @Override
    public void ability() {
        if (isImpacted)
            return;
        new Dash(
                this ,
                Math.VectorAdd(
                        Math.ScalarInVector(-1 ,position) ,
                        chasingPlayer.getPlayerData().getEpsilon().getPosition()
                ) ,
                700 ,
                110 ,
                java.lang.Math.PI,
                false
        ).startDash();
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
        Spawner.addCollectives(game ,targetedPlayers ,position ,1 ,5);
    }
}
