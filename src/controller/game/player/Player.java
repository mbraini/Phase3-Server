package controller.game.player;

import constants.ControllerConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.ModelRequestController;
import controller.game.PlayerData;
import controller.game.ViewRequestController;
import model.objectModel.fighters.EpsilonModel;
import utils.Helper;
import utils.Vector;

public class Player {

    private Game game;
    private String username;
    private ViewRequestController viewRequestController;
    private ModelRequestController modelRequestController;
    private PlayerData playerData;
    private int abilityPort;
    private int effectPort;
    private int framePort;
    private int objectPort;
    private int variablesPort;

    public Player(Game game ,String username) {
        this.game = game;
        this.username = username;
        initData();
        initEpsilon();
    }

    private void initData() {
        viewRequestController = new ViewRequestController();
        modelRequestController = new ModelRequestController();
        playerData = new PlayerData();
    }

    private void initEpsilon() {
//        playerData.setEpsilon(
//                new EpsilonModel(
//                        game ,
//                        new Vector(SizeConstants.SCREEN_SIZE.width / 2d ,SizeConstants.SCREEN_SIZE.height / 2d),
//                        Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
//                )
//        );
//        game.getModelRequests().addObjectModel(playerData.getEpsilon());
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
}
