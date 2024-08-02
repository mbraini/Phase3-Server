package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;

public class ClientHasBattleSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientHasBattleSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        if (squad.getSquadBattle().getInBattleWith() != null) {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.hasSquadBattle);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.yes);
        }
        else {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.hasSquadBattle);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.no);
        }
    }
}
