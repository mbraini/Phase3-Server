package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;

public class ClientKickOutSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientKickOutSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {

        String username = tcpClient.getTcpMessager().readMessage();
        Squad squad = OnlineData.getSquad(username);
        squad.removeMember(username);
        OnlineData.putClientSquadMap(username ,null);
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.kickOut);
        tcpClient.getTcpMessager().sendMessage(username);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }
}
