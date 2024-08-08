package controller.game;

import controller.game.player.Player;
import controller.online.dataBase.OnlineData;
import controller.online.tcp.ServerMessageType;

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
