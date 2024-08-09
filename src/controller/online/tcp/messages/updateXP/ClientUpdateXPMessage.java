package controller.online.tcp.messages.updateXP;

import controller.online.client.GameClient;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.tcp.ServerMessageType;

public class ClientUpdateXPMessage {

    private TCPClient tcpClient;

    public ClientUpdateXPMessage(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public void sendRequest() {
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.updateXP);
        GameClient gameClient = OnlineData.getGameClient(tcpClient.getUsername());
        tcpClient.getTcpMessager().sendMessage(gameClient.getXp());
    }

}
