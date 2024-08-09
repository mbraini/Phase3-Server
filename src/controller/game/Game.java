package controller.game;

import constants.*;
import controller.game.manager.GameManager;
import controller.game.manager.GameState;
import controller.game.player.InfoSender;
import controller.game.player.Player;
import controller.online.client.GameClient;
import controller.online.client.LeadBoard;
import controller.online.dataBase.OnlineData;
import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.squad.SquadBattle;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.messages.giveStats.ServerGiveStatsMessage;
import controller.online.tcp.messages.updateXP.ClientUpdateXPMessage;
import model.ModelData;
import model.ModelRequests;
import model.objectModel.fighters.finalBoss.bossAI.ImaginaryObject;
import model.objectModel.frameModel.FrameModelBuilder;
import model.threads.FrameThread;
import model.threads.GameLoop;
import utils.Helper;
import utils.TCPMessager;
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
    private GameController gameController;
    private PauseWatcher pauseWatcher;
    private double gameSpeed;
    private ArrayList<ImaginaryObject> solidObjects = new ArrayList<>();

    public Game(GameType gameType) {
        this.gameType = gameType;
        players = new ArrayList<>();
        initSpeed();
        initControllers();
        initDataBase();
        initInfoSender();
        initFirstFrame();
        initThreads();
    }

    private void initSpeed() {
        if (gameType.equals(GameType.colosseum))
            gameSpeed = VelocityConstants.COLESSEUM_GAME_SPEED_CONSTANT;
        else {
            gameSpeed = 1;
        }
    }

    private void initFirstFrame() {
        FrameModelBuilder builder = new FrameModelBuilder(
                this,
                new Vector(SizeConstants.SCREEN_SIZE.width / 2d - 350 ,SizeConstants.SCREEN_SIZE.height / 2d - 350),
                new Dimension(700 ,700),
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
        builder.setSolid(true);
        modelData.addFrame(builder.create());
    }

    private void initInfoSender() {
        infoSender = new InfoSender(this ,players);
    }

    private void initControllers() {
        gameController = new GameController(this);
        pauseWatcher = new PauseWatcher();
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
        synchronized (players) {
            for (Player player : players) {
                player.start();
                TCPClient tcpClient = OnlineData.getTCPClient(player.getUsername());
                tcpClient.setClientState(ClientState.busy);
                tcpClient.getTcpMessager().sendMessage(ServerMessageType.waitingRoom);
            }
        }
        waitingRoom();
        gameLoop.start();
        frameThread.start();
        gameManager.getGameManagerThread().start();
        infoSender.start();
        gameManager.getWaveSpawner().getSpawner().start();
    }

    private void waitingRoom() {
        for (int i = 0; i < TimeConstants.WAITING_ROOM_TIME / 1000 ;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (players) {
                for (Player player : players) {
                    TCPClient tcpClient = OnlineData.getTCPClient(player.getUsername());
                    tcpClient.getTcpMessager().sendMessage(ServerMessageType.waitingTime);
                    tcpClient.getTcpMessager().sendMessage(15 - i);
                }
            }
        }
        synchronized (players) {
            for (Player player : players) {
                TCPClient tcpClient = OnlineData.getTCPClient(player.getUsername());
                tcpClient.getTcpMessager().sendMessage(ServerMessageType.endWaitingTime);
            }
        }
    }

    public void addPlayer(Player player) {
        synchronized (players) {
            players.add(player);
            if (gameType.equals(GameType.colosseum) && players.size() == 2) {
                players.getFirst().setTeammate(players.getLast());
                players.getLast().setTeammate(players.getFirst());
            }
        }
        infoSender.addPlayer(player);
    }

    public InfoSender getInfoSender() {
        return infoSender;
    }

    public void setInfoSender(InfoSender infoSender) {
        this.infoSender = infoSender;
    }

    public synchronized Player getPlayer(String username) {
        synchronized (players) {
            for (Player player : players) {
                if (player.getUsername().equals(username))
                    return player;
            }
        }
        return null;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public PauseWatcher getPauseWatcher() {
        return pauseWatcher;
    }

    public void setPauseWatcher(PauseWatcher pauseWatcher) {
        this.pauseWatcher = pauseWatcher;
    }

    public ArrayList<ImaginaryObject> getSolidObjects() {
        return solidObjects;
    }

    public void setSolidObjects(ArrayList<ImaginaryObject> solidObjects) {
        this.solidObjects = solidObjects;
    }

    public synchronized void addSolidObject(ImaginaryObject imaginaryObject) {
        solidObjects.add(imaginaryObject);
    }

    public double getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(double gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public synchronized void removeSolidObject(String id) {
        for (ImaginaryObject object : solidObjects) {
            if (object.getId().equals(id)) {
                solidObjects.remove(object);
                return;
            }
        }
    }

    public void end(Player loser) {
        getGameState().setOver(true);
        calculatePoints(loser);
        setGameHistories();
        updateClientXPs();
        infoSender.end();
        synchronized (players) {
            for (Player player : players) {
                TCPMessager messager = OnlineData.getTCPClient(player.getUsername()).getTcpMessager();
                if (OnlineData.getTCPClient(player.getUsername()).getClientState().equals(ClientState.busy))
                    messager.sendMessage(ServerMessageType.endGame);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                player.end();
            }
            ArrayList<Player> clonePlayers = (ArrayList<Player>) players.clone();
            for (Player player : players) {
                new ServerGiveStatsMessage(OnlineData.getTCPClient(player.getUsername()) ,clonePlayers).sendMessage();
            }
            for (Player player : players) {
                OnlineData.getTCPClient(player.getUsername()).setClientState(ClientState.online);
            }
        }
    }

    private void updateClientXPs() {
        synchronized (players) {
            for (Player player : players) {
                TCPClient tcpClient = OnlineData.getTCPClient(player.getUsername());
                new ClientUpdateXPMessage(tcpClient).sendRequest();
            }
        }
    }

    private void setGameHistories() {
        synchronized (players) {
            for (Player player : players) {
                GameClient gameClient = OnlineData.getGameClient(player.getUsername());
                gameClient.setXp(gameClient.getXp() + player.getPlayerData().getXpGained());
                gameClient.addGameHistory(new LeadBoard(
                        player.getPlayerData().getXpGained(),
                        player.getPlayerData().getSurvivalTime(),
                        player.getPlayerData().getTotalBullets(),
                        player.getPlayerData().getSuccessfulBullets()
                ));
                if (gameClient.getMostXPEarned() < player.getPlayerData().getXpGained())
                    gameClient.setMostXPEarned(player.getPlayerData().getXpGained());
                if (gameClient.getMostSurvivalTime() < player.getPlayerData().getSurvivalTime())
                    gameClient.setMostSurvivalTime((int) player.getPlayerData().getSurvivalTime());
            }
        }
    }

    private void calculatePoints(Player loser) {
        Player loserTeammate;
        if (loser != null)
            loserTeammate = loser.getTeammate();
        else
            loserTeammate = null;
        ArrayList<Player> winners = Helper.findWinners(loser ,loserTeammate ,players);

        if (gameType.equals(GameType.monomachia)) {
            for (Player player : winners) {
                GameClient gameClient = OnlineData.getGameClient(player.getUsername());
                gameClient.setXp(gameClient.getXp() + CostConstants.MONOMACHIA_WINNER_PRIZE);
                Squad squad = OnlineData.getClientSquad(player.getUsername());
                squad.getTreasury().setXp(squad.getTreasury().getXp() + CostConstants.MONOMACHIA_WINNER_PRIZE);
                SquadBattle squadBattle = squad.getSquadBattle();
                squadBattle.setXpEarned(squadBattle.getXpEarned() + CostConstants.MONOMACHIA_WINNER_PRIZE);
            }
            if (loser != null) {
                SquadBattle squadBattle = OnlineData.getClientSquad(winners.getFirst().getUsername()).getSquadBattle();
                squadBattle.setMonomachiaWins(squadBattle.getMonomachiaWins() + 1);
            }
        }
        else {
            for (Player player : winners) {
                GameClient gameClient = OnlineData.getGameClient(player.getUsername());
                gameClient.setXp(gameClient.getXp() + CostConstants.COLOSSEUM_WINNER_PRIZE);
                Squad squad = OnlineData.getClientSquad(player.getUsername());
                squad.getTreasury().setXp(squad.getTreasury().getXp() + CostConstants.COLOSSEUM_WINNER_PRIZE);
                SquadBattle squadBattle = squad.getSquadBattle();
                squadBattle.setXpEarned(squadBattle.getXpEarned() + CostConstants.COLOSSEUM_WINNER_PRIZE);
            }
        }

    }
}
