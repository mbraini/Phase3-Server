package controller;

import controller.client.GameClient;
import controller.client.TCPClient;
import controller.squad.Squad;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineData {

    private volatile static ArrayList<TCPClient> TCPClients = new ArrayList<>();
    private volatile static ArrayList<Squad> squads = new ArrayList<>();
    private volatile static HashMap<TCPClient , GameClient> clientGameMap = new HashMap<>();

    public synchronized static ArrayList<TCPClient> getOnlineClients() {
        return TCPClients;
    }

    public synchronized static void addClient(TCPClient newTCPClient) {
        for (int i = 0 ;i < TCPClients.size() ;i++) {
            if (TCPClients.get(i).getUsername().equals(newTCPClient.getUsername())) {
                GameClient gameClient = clientGameMap.remove(TCPClients.get(i));
                newTCPClient.setSquad(TCPClients.get(i).getSquad());
                TCPClients.set(i, newTCPClient);
                putClientGameMap(newTCPClient ,gameClient);
                return;
            }
        }
        TCPClients.add(newTCPClient);
        putClientGameMap(newTCPClient ,new GameClient(newTCPClient.getUsername()));
    }

    public synchronized static void removeClient(TCPClient TCPClient) {
        for (TCPClient onlineTCPClient : TCPClients) {
            if (onlineTCPClient.getUsername().equals(TCPClient.getUsername())) {
                TCPClients.remove(onlineTCPClient);
                return;
            }
        }
    }

    public synchronized static void putClientGameMap(TCPClient tcpClient ,GameClient gameClient) {
        clientGameMap.put(tcpClient,gameClient);
    }

    public synchronized static GameClient getGameClient(TCPClient tcpClient) {
        return clientGameMap.get(tcpClient);
    }



    public synchronized static ArrayList<Squad> getSquads() {
        return squads;
    }

    public synchronized static void addSquad(Squad squad) {
        squads.add(squad);
    }
}
