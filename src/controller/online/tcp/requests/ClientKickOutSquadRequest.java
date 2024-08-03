package controller.online.tcp.requests;

import controller.online.OnlineData;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

public class ClientKickOutSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientKickOutSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {

        String username = tcpClient.getTcpMessager().readMessage();
        Squad squad = OnlineData.getClientSquad(username);
        squad.removeMember(username);
        OnlineData.putClientSquadMap(username ,null);
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.kickOut);
        tcpClient.getTcpMessager().sendMessage(username);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }
}
