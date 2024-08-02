package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.TCPClientRequest;

public class ClientUpdateTreasuryShopRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientUpdateTreasuryShopRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.updateTreasuryShop);
        tcpClient.getTcpMessager().sendMessage(squad.getTreasury().getXp());
        tcpClient.getTcpMessager().sendMessage(squad.getMembers().size() * 100);
    }
}
