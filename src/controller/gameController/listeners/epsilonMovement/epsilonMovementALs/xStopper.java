package controller.gameController.listeners.epsilonMovement.epsilonMovementALs;


import controller.gameController.configs.Configs;
import model.objectModel.fighters.EpsilonModel;

public class xStopper extends Thread {
    private EpsilonModel epsilon;
    public xStopper(EpsilonModel epsilon){
        this.epsilon = epsilon;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(Configs.GameConfigs.EPSILON_DECELERATION_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        epsilon.setAcceleration(0, epsilon.getAcceleration().y);
        epsilon.setVelocity(0 ,epsilon.getVelocity().y);
    }
}