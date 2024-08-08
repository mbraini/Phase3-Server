package controller.game;

import controller.game.manager.GameState;
import controller.game.player.Player;
import controller.online.OnlineData;
import controller.online.client.TCPClient;
import controller.online.tcp.ServerMessageType;
import model.ModelData;
import model.objectModel.ObjectModel;
import model.objectModel.PortalModel;
import model.objectModel.fighters.EpsilonModel;

import java.util.ArrayList;

public class ModelRequestController {

    private Player player;

    public ModelRequestController(Player player) {
        this.player = player;
    }

    public static void playSound(String path) {
//        Sound sound = null;
//        try {
//            sound = new Sound(path);
//        } catch (UnsupportedAudioFileException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }
//        sound.play();
    }

    public void reorderKeys() {
        OnlineData.getTCPClient(player.getUsername()).getTcpMessager().sendMessage(ServerMessageType.reorderKeys);
    }

    public void randomizeKeys() {
        OnlineData.getTCPClient(player.getUsername()).getTcpMessager().sendMessage(ServerMessageType.randomizeKeys);
    }
}
