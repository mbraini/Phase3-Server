package model.objectModel;


import constants.*;
import controller.game.Game;
import controller.game.ObjectController;
import controller.game.enums.ModelType;
import controller.game.player.Player;
import model.interfaces.Ability;
import model.interfaces.Fader;
import model.interfaces.collisionInterfaces.CollisionDetector;
import model.interfaces.collisionInterfaces.IsCircle;
import model.interfaces.movementIntefaces.MoveAble;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectiveModel extends ObjectModel implements IsCircle, Ability, MoveAble, CollisionDetector, Fader {
    int value;
    double time;
    boolean hasAbility = false;
    private ArrayList<Player> pickers;
    private Player picker;

    public CollectiveModel(Game game , ArrayList<Player> pickers, Vector position , String id , int value){
        super(game);
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.pickers = pickers;
        this.id = id;
        this.HP = 1;
        type = ModelType.collective;
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
        Vector epsilonPosition = picker.getPlayerData().getEpsilon().getPosition().clone();
        Vector distance = Math.VectorAdd(
                epsilonPosition,
                Math.ScalarInVector(-1, position)
        );
        velocity = Math.VectorWithSize(
                distance,
                VelocityConstants.COLLECTIVE_VELOCITY
        );
        if (Math.VectorSize(distance) <= SizeConstants.EPSILON_DIMENSION.width) {
            picker.getPlayerData().setXp(picker.getPlayerData().getXp() + value);
            picker.getPlayerData().setXpGained(picker.getPlayerData().getXpGained() + value);
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
        boolean flag = false;
        synchronized (pickers) {
            for (Player picker : pickers) {
                Vector epsilonPosition = picker.getPlayerData().getEpsilon().getPosition().clone();
                Vector distance = Math.VectorAdd(
                        epsilonPosition,
                        Math.ScalarInVector(-1, position)
                );
                if (Math.VectorSize(distance) <= getRadios()) {
                    flag = true;
                    this.picker = picker;
                    break;
                }
            }
        }
        if (flag) {
            hasAbility = true;
        }
    }

    @Override
    public void addTime(double time) {
        this.time += time;
        System.out.println(time);
    }

    @Override
    public void fadeIf() {
        if (time >= TimeConstants.COLLECTIVE_FADE_TIME){
            die();
        }
    }
}
