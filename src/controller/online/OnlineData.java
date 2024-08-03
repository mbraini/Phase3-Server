package controller.online;

import controller.online.client.GameClient;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;

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
                TCPClient lastTCP = clientTCPMap.remove(tcpClient.getUsername());
                tcpClient.setMessages(lastTCP.getMessages());
                clientTCPMap.put(tcpClient.getUsername() ,tcpClient);
                return;
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

    public synchronized static ArrayList<Integer> getAvailablePorts() {
        return availablePorts;
    }

    public synchronized static void setAvailablePorts(ArrayList<Integer> availablePorts) {
        OnlineData.availablePorts = availablePorts;
    }

    public synchronized static TCPClient getTCPClient(String username) {
        return clientTCPMap.get(username);
    }

    public synchronized static void putClientSquadMap(String username ,Squad squad) {
        clientSquadMap.remove(username);
        clientSquadMap.put(username ,squad);
    }

    public synchronized static Squad getClientSquad(String username) {
        return clientSquadMap.get(username);
    }

    public synchronized static Squad getSquad(String squadName) {
        for (Squad squad : squads) {
            if (squad.getName().equals(squadName))
                return squad;
        }
        return null;
    }

    public static ArrayList<String> getClientUsernames() {
        return clientUsernames;
    }

    public static synchronized void removeSquad(Squad squad) {
        for (Squad dataSquad : squads) {
            if (dataSquad.getName().equals(squad.getName())) {
                squads.remove(dataSquad);
                return;
            }
        }
    }
}
