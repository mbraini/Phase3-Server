package controller.tcp;

import controller.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker {

    private static final ServerSocket serverSocket = getInstance();
    private Socket clientSocket;
    private static final ArrayList<Client> clients = new ArrayList<>();

    private static ServerSocket getInstance() {
        try {
            return new ServerSocket(8090);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Client client = new Client(socket);
            clients.add(client);
            clients.notify();
        }
    }

    private void serve() {
        while (true) {
            try {
                clients.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            new TCPServiceListener(clients.getLast().getClientSocket()).listen();
        }
    }

}
