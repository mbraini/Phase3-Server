package controller;

import controller.client.GameClient;
import controller.client.TCPClient;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineData {

    private static final ArrayList<TCPClient> TCPClients = new ArrayList<>();
    private static final HashMap<TCPClient , GameClient> clientMap = new HashMap<>();

    public synchronized static ArrayList<TCPClient> getOnlineClients() {
        return TCPClients;
    }

    public synchronized static void addClient(TCPClient newTCPClient) {
        for (int i = 0 ;i < TCPClients.size() ;i++) {
            if (TCPClients.get(i).getUsername().equals(newTCPClient.getUsername())) {
                TCPClients.set(i, newTCPClient);
                return;
            }
        }
        TCPClients.add(newTCPClient);
        clientMap.put(newTCPClient ,new GameClient());
    }

    public synchronized static void removeClient(TCPClient TCPClient) {
        for (TCPClient onlineTCPClient : TCPClients) {
            if (onlineTCPClient.getUsername().equals(TCPClient.getUsername())) {
                TCPClients.remove(onlineTCPClient);
                return;
            }
        }
    }

    public synchronized static void putClientMap(TCPClient tcpClient ,GameClient gameClient) {
        clientMap.put(tcpClient,gameClient);
    }

    public synchronized static GameClient getGameClient(TCPClient tcpClient) {
        return clientMap.get(tcpClient);
    }

}
