package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;

public class ClientLeaveSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientLeaveSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getSquad(tcpClient.getUsername());
        squad.removeMember(tcpClient.getUsername());
        OnlineData.putClientSquadMap(tcpClient.getUsername() ,null);
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.leaveSquad);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }
}
