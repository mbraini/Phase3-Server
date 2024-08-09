package controller.online.tcp.requests;

import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.tcp.TCPClientRequest;

public class ClientEndingOfflineGameRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientEndingOfflineGameRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        tcpClient.setClientState(ClientState.online);
    }
}
