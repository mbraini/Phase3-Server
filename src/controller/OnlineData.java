package controller;

import controller.client.GameClient;
import controller.client.TCPClient;
import controller.squad.Squad;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineData {

    private volatile static ArrayList<Integer> availablePorts = new ArrayList<>();
    private volatile static ArrayList<String> clientUsernames = new ArrayList<>();
    private volatile static ArrayList<Squad> squads = new ArrayList<>();
    private volatile static HashMap<String , TCPClient> clientTCPMap = new HashMap<>();
    private volatile static HashMap<String ,Squad> clientSquadMap = new HashMap<>();
    private volatile static HashMap<String , GameClient> clientGameMap = new HashMap<>();


    public synchronized static void addClient(TCPClient tcpClient) {
        for (int i = 0 ;i < clientUsernames.size() ;i++) {
            if (clientUsernames.get(i).equals(tcpClient.getUsername())) {
                clientTCPMap.remove(tcpClient.getUsername());
                clientTCPMap.put(tcpClient.getUsername() ,tcpClient);
            }
        }
        clientTCPMap.put(tcpClient.getUsername() ,tcpClient);
        clientUsernames.add(tcpClient.getUsername());
        putClientGameMap(tcpClient.getUsername() ,new GameClient(tcpClient.getUsername()));
    }

    public synchronized static void putClientGameMap(String username ,GameClient gameClient) {
        clientGameMap.put(username,gameClient);
    }

    public synchronized static GameClient getGameClient(String username) {
        return clientGameMap.get(username);
    }



    public synchronized static ArrayList<Squad> getSquads() {
        return squads;
    }

    public synchronized static void addSquad(Squad squad) {
        squads.add(squad);
    }

    public synchronized static int getAvailablePort() {
        return availablePorts.removeFirst();
    }

    public static ArrayList<Integer> getAvailablePorts() {
        return availablePorts;
    }

    public static void setAvailablePorts(ArrayList<Integer> availablePorts) {
        OnlineData.availablePorts = availablePorts;
    }

    public static TCPClient getTCPClient(String username) {
        return clientTCPMap.get(username);
    }

    public static void putClientSquadMap(String username ,Squad squad) {
        clientSquadMap.remove(username);
        clientSquadMap.put(username ,squad);
    }

    public static Squad getSquad(String username) {
        return clientSquadMap.get(username);
    }

    public static ArrayList<String> getClientUsernames() {
        return clientUsernames;
    }
}
