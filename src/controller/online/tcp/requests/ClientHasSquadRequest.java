package controller.online.tcp.requests;

import controller.online.OnlineData;
import controller.online.client.TCPClient;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

public class ClientHasSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientHasSquadRequest(TCPClient tcpClient) {

        this.tcpClient = tcpClient;

    }

    @Override
    public void checkRequest() {
        if (OnlineData.getClientSquad(tcpClient.getUsername()) == null) {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.hasSquadRecponce);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.no);
        }
        else {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.hasSquadRecponce);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.yes);
        }
    }
}
