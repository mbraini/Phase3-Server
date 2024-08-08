package controller.online.tcp.messages.giveStats;

import controller.game.player.Player;
import controller.online.GameStats;
import controller.online.annotations.SkippedByJson;
import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.tcp.ServerMessageType;

public class ServerGiveStatsMessage{

    @SkippedByJson
    private TCPClient tcpClient;
    @SkippedByJson
    private Player player;

    public ServerGiveStatsMessage(TCPClient tcpClient , Player player) {
        this.tcpClient = tcpClient;
        this.player = player;
    }


    public void sendMessage() {
        if (!tcpClient.getClientState().equals(ClientState.busy))
            return;
        StatsHelper helper = new StatsHelper();
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.giveStats);
        if (player.getPlayerData().getSurvivalTime() == 0) {
            helper.setSurvivalTime((int) player.getGame().getGameState().getTime() / 1000);
        }
        else {
            helper.setSurvivalTime((int) player.getPlayerData().getSurvivalTime() / 1000);
        }
        helper.setXpGained(player.getPlayerData().getXpGained());
        helper.setSuccessfulBullets(player.getPlayerData().getSuccessfulBullets());
        helper.setTotalBullets(player.getPlayerData().getTotalBullets());
        helper.setMostXpGained(GameStats.mostXpGained);
        helper.setMostSurvivalTime(GameStats.mostSurvivalTime);

        tcpClient.getTcpMessager().sendMessage(helper);
        tcpClient.setClientState(ClientState.online);
    }

}
