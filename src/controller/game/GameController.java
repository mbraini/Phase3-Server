package controller.game;

import controller.game.player.Player;
import controller.online.OnlineData;
import controller.online.tcp.ServerMessageType;
import utils.TCPMessager;

public class GameController {

    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void pause(Player pausePlayer) {
        game.getGameState().setPause(true);
        game.getPauseWatcher().setIsPausing(pausePlayer);
        game.getPauseWatcher().startPauseWatcher();
        synchronized (game.getPlayers()) {
            for (Player player : game.getPlayers()) {
                TCPMessager messager = OnlineData.getTCPClient(player.getUsername()).getTcpMessager();
                messager.sendMessage(ServerMessageType.gamePause);
            }
        }
    }

    public void unpause() {
        game.getGameState().setPause(false);
        synchronized (game.getPlayers()) {
            for (Player player : game.getPlayers()) {
                TCPMessager messager = OnlineData.getTCPClient(player.getUsername()).getTcpMessager();
                messager.sendMessage(ServerMessageType.gameUnpause);
            }
        }
    }

}
