package controller.tcp;

import controller.OnlineData;
import controller.client.TCPClient;

public class ClientHasSquadRequest extends TCPClientRequest{

    private TCPClient tcpClient;

    public ClientHasSquadRequest(TCPClient tcpClient) {

        this.tcpClient = tcpClient;

    }

    @Override
    public void checkRequest() {
        if (tcpClient.getSquad() == null)
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.no);
        else
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.yes);
    }
}
