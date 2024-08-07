package controller.game.player;

import constants.ControllerConstants;
import constants.SizeConstants;
import controller.game.*;
import controller.game.player.udp.ClientGameInfoReceiver;
import controller.online.OnlineData;
import model.objectModel.fighters.EpsilonModel;
import utils.Helper;
import utils.Math;
import utils.Vector;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    private Game game;
    private String username;
    private ClientGameInfoReceiver clientGameInfoReceiver;
    private ViewRequestController viewRequestController;
    private ModelRequestController modelRequestController;
    private PlayerData playerData;
    private Player teammate;
    private int abilityPort;
    private int effectPort;
    private int framePort;
    private int objectPort;
    private int variablesPort;
    private boolean dead = false;

    public Player(Game game ,String username) {
        this.game = game;
        this.username = username;
        initData();
        initEpsilon();
        initControllers();
    }

    private void initControllers() {
        modelRequestController = new ModelRequestController();
        viewRequestController = new ViewRequestController(this);
    }

    private void initData() {
        playerData = new PlayerData();
    }

    public void start() {
        playerData.setXp(OnlineData.getGameClient(username).getXp());
        ArrayList<Player> targetedPlayers = new ArrayList<>();
        synchronized (game.getPlayers()) {
            if (game.getGameType().equals(GameType.monomachia)) {
                for (Player player : game.getPlayers()) {
                    if (player.getUsername().equals(username))
                        continue;
                    targetedPlayers.add(player);
                }
            }
        }
        playerData.getEpsilon().setTargetedPlayers(targetedPlayers);
    }

    private void initEpsilon() {
        Vector randomPosition;
        synchronized (game.getModelData().getFrames()) {
            if (game.getModelData().getFrames().isEmpty()) {
                Random random = new Random();
                int x = random.nextInt(
                        SizeConstants.SCREEN_SIZE.width / 2 - SizeConstants.ONLINE_GAME_INIT_WINDOW_SIZE.width / 2,
                        SizeConstants.SCREEN_SIZE.width / 2 + SizeConstants.ONLINE_GAME_INIT_WINDOW_SIZE.width / 2
                );
                int y = random.nextInt(
                        SizeConstants.SCREEN_SIZE.height / 2 - SizeConstants.ONLINE_GAME_INIT_WINDOW_SIZE.height / 2,
                        SizeConstants.SCREEN_SIZE.height / 2 + SizeConstants.ONLINE_GAME_INIT_WINDOW_SIZE.height / 2
                );
                randomPosition = new Vector(x ,y);
            }
            else {
                Random random = new Random();
                randomPosition = game.getModelData().getFrames().getFirst().getPosition();
                randomPosition = Math.VectorAdd(
                        randomPosition,
                        new Vector(
                                game.getModelData().getFrames().getFirst().getSize().width / 2d,
                                game.getModelData().getFrames().getFirst().getSize().height / 2d
                        )
                );
                int randomX = random.nextInt(-100 ,100);
                int randomY = random.nextInt(-100 ,100);
                randomPosition = Math.VectorAdd(
                        randomPosition,
                        new Vector(randomX / 100d ,randomY / 100d)
                );
            }
        }
        playerData.setEpsilon(
                new EpsilonModel(
                        game ,
                        this,
                        new ArrayList<>(),
                        randomPosition,
                        Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
                )
        );
        game.getModelRequests().addObjectModel(playerData.getEpsilon());
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ViewRequestController getViewRequestController() {
        return viewRequestController;
    }

    public void setViewRequestController(ViewRequestController viewRequestController) {
        this.viewRequestController = viewRequestController;
    }

    public ModelRequestController getModelRequestController() {
        return modelRequestController;
    }

    public void setModelRequestController(ModelRequestController modelRequestController) {
        this.modelRequestController = modelRequestController;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }


    public void startInfoSender() {

    }

    public int getAbilityPort() {
        return abilityPort;
    }

    public void setAbilityPort(int abilityPort) {
        this.abilityPort = abilityPort;
    }

    public int getEffectPort() {
        return effectPort;
    }

    public void setEffectPort(int effectPort) {
        this.effectPort = effectPort;
    }

    public int getFramePort() {
        return framePort;
    }

    public void setFramePort(int framePort) {
        this.framePort = framePort;
    }

    public int getObjectPort() {
        return objectPort;
    }

    public void setObjectPort(int objectPort) {
        this.objectPort = objectPort;
    }

    public int getVariablesPort() {
        return variablesPort;
    }

    public void setVariablesPort(int variablesPort) {
        this.variablesPort = variablesPort;
    }

    public Player getTeammate() {
        return teammate;
    }

    public void setTeammate(Player teammate) {
        this.teammate = teammate;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public ClientGameInfoReceiver getClientGameInfoReceiver() {
        return clientGameInfoReceiver;
    }

    public void setClientGameInfoReceiver(ClientGameInfoReceiver clientGameInfoReceiver) {
        this.clientGameInfoReceiver = clientGameInfoReceiver;
    }

    public void end() {
        clientGameInfoReceiver.setCanReceive(false);
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(OnlineData.getAvailablePort());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        byte[] packetData = new byte[1];
        DatagramPacket datagramPacket = new DatagramPacket(
                packetData,
                packetData.length,
                new InetSocketAddress("127.0.0.1", clientGameInfoReceiver.getPort())
        );
        try {
            for (int i = 0 ;i < 10 ;i++) {
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
