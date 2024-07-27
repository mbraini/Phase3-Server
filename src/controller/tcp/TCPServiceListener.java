package controller.tcp;

import java.net.Socket;

public class TCPServiceListener {

    private Socket clientSocket;

    public TCPServiceListener(Socket socket) {
        this.clientSocket = socket;
        checkClients();
    }

    private void checkClients() {

    }

}
