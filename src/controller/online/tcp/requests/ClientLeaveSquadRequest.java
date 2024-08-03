package controller.online.tcp.requests;

import controller.online.OnlineData;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

public class ClientLeaveSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientLeaveSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        squad.removeMember(tcpClient.getUsername());
        OnlineData.putClientSquadMap(tcpClient.getUsername() ,null);
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.leaveSquad);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }
}
