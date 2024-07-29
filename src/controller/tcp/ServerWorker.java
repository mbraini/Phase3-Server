package controller.tcp;

import controller.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker extends Thread{

    public static final ServerWorker serverWorker = getInstance();
    private static ServerSocket serverSocket = getServerSocket();

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
        Socket socket;
        while (true) {
            socket = serverSocket.accept();
            Client client = new Client(socket);
            synchronized (clients) {
                clients.add(client);
                clients.notifyAll();
            }
            System.out.println("A CLIENT!");
        }
    }

    @Override
    public void run() {
        while (true) {
            Client client;
            synchronized (clients) {
                while (clients.isEmpty()) {
                    try {
                        clients.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                client = clients.removeFirst();
                clients.notify();
            }
            new TCPServiceListener(client).listen();
            System.out.println("LEFT :(");
        }
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

}
