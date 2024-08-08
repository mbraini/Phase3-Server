package controller.online.tcp.requests;

import controller.online.dataBase.OnlineData;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

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
