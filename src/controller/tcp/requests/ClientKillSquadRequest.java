package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;

import java.util.ArrayList;

public class ClientKillSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientKillSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        String squadName = tcpClient.getTcpMessager().readMessage();
        Squad squad = OnlineData.getSquad(squadName);

        ArrayList<String> members = (ArrayList<String>) squad.getMembers().clone();
        for (String member : members) {
            squad.removeMember(member);
            OnlineData.putClientSquadMap(member ,null);
        }
        OnlineData.removeSquad(squad);

        tcpClient.getTcpMessager().sendMessage(ServerMessageType.killSquad);
        tcpClient.getTcpMessager().sendMessage(squadName);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }
}
