package controller.gameController.listeners.epsilonMovement;


import controller.gameController.configs.Configs;
import controller.gameController.manager.GameState;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;
import controller.gameController.listeners.epsilonMovement.epsilonMovementALs.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class EpsilonMovement extends KeyAdapter {
    public static HashSet<Integer> keys = new HashSet<>();
    private EpsilonModel epsilon;
    private Vector direction = new Vector(0 ,0);
    public static xStopper xStopper;
    public static  yStopper yStopper;
    public static int LEFT_KEY = 37;
    public static int UP_KEY = 38;
    public static int RIGHT_KEY = 39;
    public static int DOWN_KEY = 40;

    public EpsilonMovement(){
        this.epsilon = (EpsilonModel) (ModelData.getModels().getFirst());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (GameState.isInAnimation())
            return;
        keys.add(e.getKeyCode());
        CalculateDirection();
        changeDirection();
    }

    private void CalculateDirection() {
        Vector newDirection = new Vector(0 ,0);
        if (keys.contains(LEFT_KEY)){
            newDirection.setX(newDirection.getX() - 1);
        }
        if (keys.contains(UP_KEY)){
            newDirection.setY(newDirection.getY() - 1);
        }
        if (keys.contains(RIGHT_KEY)){
            newDirection.setX(newDirection.getX() + 1);
        }
        if (keys.contains(DOWN_KEY)){
            newDirection.setY(newDirection.getY() + 1);
        }
        direction = new Vector(newDirection.x ,newDirection.y);
    }

    private void changeDirection() {
        if (direction.Equals(new Vector(0 ,0))) {
            epsilon.setAcceleration(0 ,0);
            return;
        }
        epsilon.setAcceleration(Math.VectorWithSize(direction , Configs.GameConfigs.EPSILON_ACCELERATION));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (GameState.isInAnimation())
            return;
        keys.remove(e.getKeyCode());
        CalculateDirection();
        double xVelocity = epsilon.getVelocity().x;
        double yVelocity = epsilon.getVelocity().y;
        if (xVelocity != 0 && direction.x == 0){
            epsilon.setAcceleration(-xVelocity / Configs.GameConfigs.EPSILON_DECELERATION_TIME ,epsilon.getAcceleration().y);
            xStopper = new xStopper(epsilon);
            xStopper.start();
        }
        if (yVelocity != 0 && direction.y == 0){
            epsilon.setAcceleration(epsilon.getAcceleration().x ,-yVelocity / Configs.GameConfigs.EPSILON_DECELERATION_TIME);
            yStopper = new yStopper(epsilon);
            yStopper.start();
        }
    }
}