package controller.tcp;

import controller.client.TCPClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker extends Thread{

    public static final ServerWorker serverWorker = getInstance();
    private static ServerSocket serverSocket = getServerSocket();

    public static final ArrayList<TCPClient> TCP_CLIENTS = new ArrayList<>();
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
            TCPClient TCPClient = new TCPClient(socket);
            synchronized (TCP_CLIENTS) {
                TCP_CLIENTS.add(TCPClient);
                TCP_CLIENTS.notifyAll();
            }
            System.out.println("A CLIENT!");
        }
    }

    @Override
    public void run() {
        while (true) {
            TCPClient TCPClient;
            synchronized (TCP_CLIENTS) {
                while (TCP_CLIENTS.isEmpty()) {
                    try {
                        TCP_CLIENTS.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                TCPClient = TCP_CLIENTS.removeFirst();
                TCP_CLIENTS.notify();
            }
            new TCPServiceListener(TCPClient).listen();
            System.out.println("LEFT :(");
        }
    }

    public static ArrayList<TCPClient> getClients() {
        return TCP_CLIENTS;
    }

}
