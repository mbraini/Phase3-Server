package model.objectModel.fighters.normalEnemies.wyrmModel;

import constants.TimeConstants;
import controller.game.manager.GameState;

public class WyrmThread extends Thread{
    private WyrmModel wyrmModel;
    private boolean interrupt;
    public WyrmThread(WyrmModel wyrmModel){
        this.wyrmModel = wyrmModel;
    }

    @Override
    public void run() {
        while (!GameState.isOver()) {
            try {
                Thread.sleep(TimeConstants.WYRM_SHOOTING_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (GameState.isPause() || GameState.isDizzy()){
                continue;
            }
            if (isInterrupt())
                return;
            shoot();
        }
    }

    private void shoot() {
        if (GameState.isOver())
            return;
        new WyrmShooter(wyrmModel).shoot();
    }


    public boolean isInterrupt() {
        return interrupt;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }
}
