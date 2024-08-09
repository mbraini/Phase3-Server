package controller.online.tcp.messages.giveStats;

import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import controller.online.client.ClientState;
import controller.online.client.GameClient;
import controller.online.client.LeadBoard;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.tcp.ServerMessageType;

import java.util.ArrayList;

public class ServerGiveStatsMessage{

    @SkippedByJson
    private TCPClient tcpClient;
    @SkippedByJson
    private ArrayList<Player> players;

    public ServerGiveStatsMessage(TCPClient tcpClient , ArrayList<Player> players) {
        this.tcpClient = tcpClient;
        this.players = players;
    }


    public void sendMessage() {
        ArrayList<StatsHelper> statsHelpers = new ArrayList<>();
        for (Player player : players) {
            TCPClient tcpClient = OnlineData.getTCPClient(player.getUsername());
            GameClient gameClient = OnlineData.getGameClient(player.getUsername());
            if (!tcpClient.getClientState().equals(ClientState.busy))
                continue;
            StatsHelper helper = new StatsHelper();
            if (player.getPlayerData().getSurvivalTime() == 0) {
                helper.setSurvivalTime((int) player.getGame().getGameState().getTime() / 1000);
            } else {
                helper.setSurvivalTime((int) player.getPlayerData().getSurvivalTime() / 1000);
            }
            helper.setXpGained(player.getPlayerData().getXpGained());
            helper.setSuccessfulBullets(player.getPlayerData().getSuccessfulBullets());
            helper.setTotalBullets(player.getPlayerData().getTotalBullets());
            helper.setMostXpGained(gameClient.getMostXPEarned());
            helper.setMostSurvivalTime(gameClient.getMostSurvivalTime() / 1000);
            helper.setUsername(player.getUsername());
            statsHelpers.add(helper);
        }
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.giveStats);
        tcpClient.getTcpMessager().sendMessage(statsHelpers);
    }

}
