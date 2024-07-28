package controller.tcp;

import controller.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker extends Thread{

    public static final ServerWorker serverWorker = getInstance();
    private static final ServerSocket serverSocket = getServerSocket();

    private Socket clientSocket;

    public static final ArrayList<Client> clients = new ArrayList<>();
    private static ServerWorker getInstance() {
        if (serverWorker == null) {
            return new ServerWorker();
        }
        return serverWorker;
    }

    private static ServerSocket getServerSocket() {
        try {
            return new ServerSocket(8090);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Client client = new Client(socket);
            synchronized (clients) {
                clients.add(client);
                clients.notify();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized (clients) {
                while (clients.isEmpty()) {
                    try {
                        clients.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                new TCPServiceListener(clients.getFirst()).listen();
                clients.removeFirst();
                clients.notify();
            }
        }
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

}
