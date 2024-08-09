package controller.game.manager;


import controller.game.onlineGame.Game;

public class GameManager {

    private WaveSpawner waveSpawner;
    private GameManagerThread gameManagerThread;
    private Game game;

    public GameManager(Game game){
        this.game = game;
        gameManagerThread = new GameManagerThread(game);
        this.waveSpawner = new WaveSpawner(game);
    }

    public GameManagerThread getGameManagerThread() {
        return gameManagerThread;
    }

    public void setGameManagerThread(GameManagerThread gameManagerThread) {
        this.gameManagerThread = gameManagerThread;
    }

    public WaveSpawner getWaveSpawner() {
        return waveSpawner;
    }

    public void setWaveSpawner(WaveSpawner waveSpawner) {
        this.waveSpawner = waveSpawner;
    }
}
