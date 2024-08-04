package controller.game;

import controller.game.manager.GameManager;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.ModelData;
import model.ModelRequests;
import model.threads.FrameThread;
import model.threads.GameLoop;

import java.util.ArrayList;

public class Game {

    private GameType gameType;
    private String id;
    private ArrayList<Player> players;
    private ModelData modelData;
    private GameState gameState;
    private ModelRequests modelRequests;
    private GameManager gameManager;
    private GameLoop gameLoop;
    private FrameThread frameThread;

    public Game() {
        initControllers();
        initDataBase();
        initThreads();
    }

    private void initControllers() {
    }

    private void initDataBase() {
        modelData = new ModelData(this);
        gameState = new GameState(this);
        modelRequests = new ModelRequests(this);
    }

    private void initThreads() {
        gameLoop = new GameLoop(this);
        frameThread = new FrameThread(this);
        gameManager = new GameManager(this);
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ModelData getModelData() {
        return modelData;
    }

    public void setModelData(ModelData modelData) {
        this.modelData = modelData;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ModelRequests getModelRequests() {
        return modelRequests;
    }

    public void setModelRequests(ModelRequests modelRequests) {
        this.modelRequests = modelRequests;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public FrameThread getFrameThread() {
        return frameThread;
    }

    public void setFrameThread(FrameThread frameThread) {
        this.frameThread = frameThread;
    }

    public void start() {
        gameLoop.start();
        frameThread.start();
        gameManager.getGameManagerThread().start();
        gameManager.getWaveSpawner().getSpawner().start();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }





}
