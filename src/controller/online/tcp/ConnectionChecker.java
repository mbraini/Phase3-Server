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
//    private final Timer timer;
    private volatile boolean received;

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
//        timer = new Timer(7000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!received) {
//                    endWorker();
//                }
//            }
//        });
        gson = new Gson();
    }

    @Override
    public void run() {
        try {
            while (true) {
                received = false;
                Thread.sleep(3000);
//                timer.start();

                connectionMessager.sendMessage(ServerMessageType.connectionCheck);
                String JRecponce = connectionMessager.readMessage();

//                timer.stop();
                received = true;

                ClientMessageRecponceType recponce = gson.fromJson(JRecponce , ClientMessageRecponceType.class);
                if (!recponce.equals(ClientMessageRecponceType.connected)) {
                    endWorker();
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
