package controller.game.manager;



public class GameManager {

    private WaveSpawner waveSpawner;
    private GameManagerThread gameManager;

    public GameManager(){
        gameManager = new GameManagerThread();
        this.waveSpawner = new WaveSpawner();
    }

    public GameManagerThread getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManagerThread gameManager) {
        this.gameManager = gameManager;
    }

    public WaveSpawner getWaveSpawner() {
        return waveSpawner;
    }

    public void setWaveSpawner(WaveSpawner waveSpawner) {
        this.waveSpawner = waveSpawner;
    }
}
