package controller.online.tcp.requests;

import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.tcp.TCPClientRequest;

public class ClientStartingOfflineGameRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientStartingOfflineGameRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        tcpClient.setClientState(ClientState.busy);
    }
}
