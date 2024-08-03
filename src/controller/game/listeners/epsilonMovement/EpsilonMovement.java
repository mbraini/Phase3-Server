package controller.game.listeners.epsilonMovement;


import controller.game.configs.Configs;
import controller.game.manager.GameState;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EpsilonMovement extends KeyAdapter {
    private EpsilonModel epsilon;
    private boolean LEFT_PRESSED;
    private boolean RIGHT_PRESSED;
    private boolean UP_PRESSED;
    private boolean DOWN_PRESSED;
    private boolean LEFT_RELEASED;
    private boolean RIGHT_RELEASED;
    private boolean UP_RELEASED;
    private boolean DOWN_RELEASED;
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
        detectKeyPressed(e.getKeyCode());
        addAcc();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (GameState.isInAnimation())
            return;
        detectKeyReleased(e.getKeyCode());
        addDec();
    }

    private void addDec() {
        if (UP_RELEASED || DOWN_RELEASED) {
            double upDownDec = -epsilon.getVelocity().y / Configs.GameConfigs.EPSILON_DECELERATION_TIME;
            if (UP_RELEASED) {
                UP_RELEASED = false;
                epsilon.setAcceleration(epsilon.getAcceleration().x, upDownDec);
            }
            else {
                DOWN_RELEASED = false;
                epsilon.setAcceleration(epsilon.getAcceleration().x, upDownDec);
            }
            epsilon.getMovementManager().setUpDownAccTime(Configs.GameConfigs.EPSILON_DECELERATION_TIME);
            epsilon.getMovementManager().setUpDownAccTimePassed(0);
        }
        if (LEFT_RELEASED || RIGHT_RELEASED) {
            double leftRightDec = -epsilon.getVelocity().x / Configs.GameConfigs.EPSILON_DECELERATION_TIME;
            if (LEFT_RELEASED) {
                LEFT_RELEASED = false;
                epsilon.setAcceleration(leftRightDec ,epsilon.getAcceleration().y);
            }
            else {
                RIGHT_PRESSED = false;
                epsilon.setAcceleration(leftRightDec ,epsilon.getAcceleration().y);
            }
            epsilon.getMovementManager().setLeftRightAccTime(Configs.GameConfigs.EPSILON_DECELERATION_TIME);
            epsilon.getMovementManager().setLeftRightAccTimePassed(0);
        }
    }

    private void addAcc() {
        if (UP_PRESSED && DOWN_PRESSED) {
            stopUpDownEpsilon();
        }
        else {
            if (UP_PRESSED) {
                epsilon.getMovementManager().setUpDownAccTime(100000000);
                epsilon.getMovementManager().setUpDownAccTimePassed(0);
                epsilon.setAcceleration(epsilon.getAcceleration().x ,-Configs.GameConfigs.EPSILON_ACCELERATION);
            }
            if (DOWN_PRESSED) {
                epsilon.getMovementManager().setUpDownAccTime(100000000);
                epsilon.getMovementManager().setUpDownAccTimePassed(0);
                epsilon.setAcceleration(epsilon.getAcceleration().x ,Configs.GameConfigs.EPSILON_ACCELERATION);
            }
        }
        if (LEFT_PRESSED && RIGHT_PRESSED) {
            stopLeftRightEpsilon();
        }
        else {
            if (LEFT_PRESSED) {
                epsilon.getMovementManager().setLeftRightAccTime(10000000);
                epsilon.getMovementManager().setLeftRightAccTimePassed(0);
                epsilon.setAcceleration(-Configs.GameConfigs.EPSILON_ACCELERATION ,epsilon.getAcceleration().y);
            }
            if (RIGHT_PRESSED) {
                epsilon.getMovementManager().setLeftRightAccTime(10000000);
                epsilon.getMovementManager().setLeftRightAccTimePassed(0);
                epsilon.setAcceleration(Configs.GameConfigs.EPSILON_ACCELERATION ,epsilon.getAcceleration().y);
            }
        }
    }

    private void stopLeftRightEpsilon() {
        epsilon.getMovementManager().setLeftRightAccTime(0);
        epsilon.getMovementManager().setLeftRightAccTimePassed(0);
        epsilon.getMovementManager().setRotateAccTime(0);
        epsilon.getMovementManager().setRotateAccTimePassed(0);
        epsilon.setAcceleration(0 ,epsilon.getAcceleration().y);
        epsilon.setVelocity(0 ,epsilon.getVelocity().y);
    }

    private void stopUpDownEpsilon() {
        epsilon.getMovementManager().setUpDownAccTime(0);
        epsilon.getMovementManager().setUpDownAccTimePassed(0);
        epsilon.getMovementManager().setRotateAccTime(0);
        epsilon.getMovementManager().setRotateAccTimePassed(0);
        epsilon.setAcceleration(epsilon.getAcceleration().x ,0);
        epsilon.setVelocity(epsilon.getVelocity().x ,0);
    }

    private void detectKeyPressed(int keyCode) {
        if (keyCode == UP_KEY){
            UP_PRESSED = true;
            UP_RELEASED = false;
        }
        if (keyCode == DOWN_KEY){
            DOWN_PRESSED = true;
            DOWN_RELEASED = false;
        }
        if (keyCode == LEFT_KEY){
            LEFT_PRESSED = true;
            LEFT_RELEASED = false;
        }
        if (keyCode == RIGHT_KEY){
            RIGHT_PRESSED = true;
            RIGHT_RELEASED = false;
        }
    }

    private void detectKeyReleased(int keyCode) {
        if (keyCode == UP_KEY) {
            UP_RELEASED = true;
            UP_PRESSED = false;
        }
        if (keyCode == DOWN_KEY) {
            DOWN_RELEASED = true;
            DOWN_PRESSED = false;
        }
        if (keyCode == LEFT_KEY) {
            LEFT_RELEASED = true;
            LEFT_PRESSED = false;
        }
        if (keyCode == RIGHT_KEY) {
            RIGHT_RELEASED = true;
            RIGHT_PRESSED = false;
        }
    }
}