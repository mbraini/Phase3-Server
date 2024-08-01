package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;

public class ClientHasSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientHasSquadRequest(TCPClient tcpClient) {

        this.tcpClient = tcpClient;

    }

    @Override
    public void checkRequest() {
        if (OnlineData.getSquad(tcpClient.getUsername()) == null) {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.hasSquadRecponce);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.no);
        }
        else {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.hasSquadRecponce);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.yes);
        }
    }
}
