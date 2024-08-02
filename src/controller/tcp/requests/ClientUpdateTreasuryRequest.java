package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.TCPClientRequest;

public class ClientUpdateTreasuryRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    public ClientUpdateTreasuryRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.updateTreasury);
        if (squad.getOwner().equals(tcpClient.getUsername()))
            tcpClient.getTcpMessager().sendMessage(true);
        else
            tcpClient.getTcpMessager().sendMessage(false);
        tcpClient.getTcpMessager().sendMessage(squad.getTreasury().getXp());
        tcpClient.getTcpMessager().sendMessage(squad.getTreasury().getPalioxisCount());
        tcpClient.getTcpMessager().sendMessage(squad.getTreasury().getAdonisCount());
        tcpClient.getTcpMessager().sendMessage(squad.getTreasury().getGefjonCount());
    }
}
