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
        while (!wyrmModel.getGame().getGameState().isOver()) {
            try {
                Thread.sleep(TimeConstants.WYRM_SHOOTING_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (wyrmModel.getGame().getGameState().isPause() || wyrmModel.getGame().getGameState().isDizzy()){
                continue;
            }
            if (isInterrupt())
                return;
            shoot();
        }
    }

    private void shoot() {
        if (wyrmModel.getGame().getGameState().isOver())
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
