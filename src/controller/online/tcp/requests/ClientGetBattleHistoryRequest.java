package controller.online.tcp.requests;

import com.google.gson.Gson;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.TCPClientRequest;

public class ClientGetBattleHistoryRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientGetBattleHistoryRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        gson = new Gson();
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        String JHistoryMembers = gson.toJson(squad.getSquadBattleHistory());
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.battleHistory);
        tcpClient.getTcpMessager().sendMessage(JHistoryMembers);
    }
}
