package controller.gameController.listeners.epsilonMovement.epsilonMovementALs;


import controller.gameController.configs.Configs;
import model.objectModel.fighters.EpsilonModel;

public class yStopper extends Thread {
    EpsilonModel epsilon;
    public yStopper(EpsilonModel epsilon){
        this.epsilon = epsilon;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Configs.GameConfigs.EPSILON_DECELERATION_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        epsilon.setAcceleration(epsilon.getAcceleration().x ,0);
        epsilon.setVelocity(epsilon.getVelocity().x ,0);
    }
}