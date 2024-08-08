package controller.online.tcp.requests;

import controller.online.dataBase.OnlineData;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.TCPClientRequest;

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
