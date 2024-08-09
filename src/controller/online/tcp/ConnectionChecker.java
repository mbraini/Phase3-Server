package controller.online.tcp;

import com.google.gson.Gson;
import controller.online.dataBase.OnlineData;
import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.tcp.messages.ClientMessageRecponceType;
import utils.TCPMessager;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionChecker extends Thread{

    private TCPClient tcpClient;
    private final TCPMessager connectionMessager;
    private final Gson gson;

    public ConnectionChecker(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        int port = OnlineData.getAvailablePort();
        this.tcpClient.getTcpMessager().sendMessage(port);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            connectionMessager = new TCPMessager(serverSocket.accept());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gson = new Gson();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(3000);
                connectionMessager.sendMessage(ServerMessageType.connectionCheck);
                String JRecponce = connectionMessager.readMessage();
                ClientMessageRecponceType recponce = gson.fromJson(JRecponce , ClientMessageRecponceType.class);
                if (!recponce.equals(ClientMessageRecponceType.connected)) {
                    endWorker();
                    return;
                }
            }
        }
        catch (Exception e) {
            endWorker();
        }
    }

    public void endWorker() {
        tcpClient.setClientState(ClientState.offline);
        new ServerWorker().start();
    }

}
