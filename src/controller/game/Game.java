package controller.game;

import constants.ControllerConstants;
import constants.SizeConstants;
import controller.game.manager.GameManager;
import controller.game.manager.GameState;
import controller.game.player.InfoSender;
import controller.game.player.Player;
import model.ModelData;
import model.ModelRequests;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.frameModel.FrameModelBuilder;
import model.threads.FrameThread;
import model.threads.GameLoop;
import utils.Helper;
import utils.Vector;

import java.awt.*;
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
    private InfoSender infoSender;

    public Game() {
        players = new ArrayList<>();
        initControllers();
        initDataBase();
        initInfoSender();
        initFirstFrame();
        initThreads();
    }

    private void initFirstFrame() {
        FrameModelBuilder builder = new FrameModelBuilder(
                this,
                new Vector(SizeConstants.SCREEN_SIZE.width / 2d - 350 ,SizeConstants.SCREEN_SIZE.height / 2d - 350),
                new Dimension(700 ,700),
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
        builder.setSolid(true);
        modelRequests.addFrameModel(builder.create());
    }

    private void initInfoSender() {
        infoSender = new InfoSender(this ,players);
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
        infoSender.start();
//        gameManager.getWaveSpawner().getSpawner().start();
    }

    public void addPlayer(Player player) {
        players.add(player);
        infoSender.addPlayer(player);
    }

    public InfoSender getInfoSender() {
        return infoSender;
    }

    public void setInfoSender(InfoSender infoSender) {
        this.infoSender = infoSender;
    }

    public synchronized Player getPlayer(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }
}
