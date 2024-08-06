package controller.game;

import controller.game.player.Player;

public class PauseWatcher {

    private Player isPausing;
    private PauseThread pauseThread;

    public Player getIsPausing() {
        return isPausing;
    }

    public void setIsPausing(Player isPausing) {
        this.isPausing = isPausing;
    }

    public PauseThread getPauseThread() {
        return pauseThread;
    }

    public void setPauseThread(PauseThread pauseThread) {
        this.pauseThread = pauseThread;
    }

    public void startPauseWatcher() {
        pauseThread = new PauseThread(isPausing);
        pauseThread.start();
    }

    public static class PauseThread extends Thread{

        private Player player;

        public PauseThread(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            while (player.getGame().getGameState().isPause()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                player.getPlayerData().setPauseTimeLeft(player.getPlayerData().getPauseTimeLeft() - 1000);
                if (player.getPlayerData().getPauseTimeLeft() <= 0) {
                    player.getGame().getGameController().unpause();
                    break;
                }
            }
        }
    }


}
