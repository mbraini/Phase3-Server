package controller.game.player;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.ModelRequestController;
import controller.game.PlayerData;
import controller.game.ViewRequestController;
import model.objectModel.ObjectModel;
import model.objectModel.effects.EffectModel;
import model.objectModel.frameModel.FrameModel;

import java.util.ArrayList;

public class Player {

    private Game game;
    private String username;
    private ViewRequestController viewRequestController;
    private ModelRequestController modelRequestController;
    private PlayerData playerData;
    private InfoSender infoSender;

    public Player(String username) {
        this.username = username;
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



}
