package model.objectModel;


import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.TimeConstants;
import constants.VelocityConstants;
import controller.game.ObjectController;
import controller.game.manager.GameState;
import model.ModelData;
import model.interfaces.Ability;
import model.interfaces.Fader;
import model.interfaces.collisionInterfaces.CollisionDetector;
import model.interfaces.collisionInterfaces.IsCircle;
import model.interfaces.movementIntefaces.MoveAble;
import utils.Math;
import utils.Vector;

public class CollectiveModel extends ObjectModel implements IsCircle, Ability, MoveAble, CollisionDetector, Fader {
    int value;
    double time;
    boolean hasAbility = false;

    public CollectiveModel(Vector position , String id , int value){
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.id = id;
        this.HP = 1;
        this.value = value;
    }

    @Override
    public double getRadios() {
        return SizeConstants.COLLECTIVE_ABILITY_ACTIVATION_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void ability() {
        Vector epsilonPosition = ModelData.getModels().getFirst().getPosition();
        Vector distance = Math.VectorAdd(
                epsilonPosition,
                Math.ScalarInVector(-1 ,position)
        );
        velocity = Math.VectorWithSize(
                distance,
                VelocityConstants.COLLECTIVE_VELOCITY
        );
        if (Math.VectorSize(distance) <= SizeConstants.EPSILON_DIMENSION.width) {
            GameState.setXp(GameState.getXp() + value);
            GameState.setXpGained(GameState.getXpGained() + value);
            die();
            hasAbility = false;
        }
    }

    @Override
    public boolean hasAbility() {
        return hasAbility;
    }

    @Override
    public void move() {
        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);
    }

    @Override
    public void die() {
        ObjectController.removeObject(this);
    }

    @Override
    public void detect() {
        hasAbility = true;
    }

    @Override
    public void addTime(double time) {
        this.time += time;
    }

    @Override
    public void fadeIf() {
        if (time >= TimeConstants.COLLECTIVE_FADE_TIME){
            die();
        }
    }
}
