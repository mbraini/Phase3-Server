package controller.online.tcp.requests;

import controller.game.onlineGame.Game;
import controller.game.onlineGame.GameType;
import controller.game.player.Player;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;
import controller.online.tcp.messages.allySpawn.ClientSpawnAllyMessage;

import java.util.ArrayList;

public class ClientSpawnAllyRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientSpawnAllyRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        Game game = OnlineData.getOnlineGame(tcpClient.getUsername());
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        Player player = OnlineData.getPlayer(tcpClient.getUsername());
        if (player.getTeammate() == null) {
            if (squad.getSquadBattle().hasSpawn(tcpClient.getUsername()) && game.getGameType().equals(GameType.monomachia)) {
                tcpClient.getTcpMessager().sendMessage(ServerMessageType.spawnAlly);
                tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
                notifyAllSquadMembers();
            }
        }
        else {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.spawnAlly);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
        }
    }

    private void notifyAllSquadMembers() {
        ArrayList<String> squadMembers = (ArrayList<String>)
                OnlineData.getClientSquad(tcpClient.getUsername()).getMembers().clone();

        for (String member : squadMembers) {
            if (member.equals(tcpClient.getUsername()))
                continue;
            TCPClient tcpClient = OnlineData.getTCPClient(member);
            tcpClient.addMessage(
                    new ClientSpawnAllyMessage(
                            member,
                            this.tcpClient.getUsername(),
                            GameType.monomachia
                    )
            );
        }
    }
}
