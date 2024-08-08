package controller.online.dataBase;

import controller.game.Game;
import controller.game.player.Player;
import controller.online.client.GameClient;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineData {

    private static int availablePort = 8000;
    private volatile static ArrayList<String> clientUsernames = new ArrayList<>();
    private volatile static ArrayList<Squad> squads = new ArrayList<>();
    private volatile static HashMap<String , TCPClient> clientTCPMap = new HashMap<>();
    private volatile static HashMap<String ,Squad> clientSquadMap = new HashMap<>();
    private volatile static HashMap<String , GameClient> clientGameMap = new HashMap<>();
    private volatile static HashMap<String , Game> clientOnlineGameMap = new HashMap<>();
    private volatile static HashMap<String , Player> clientPlayerMap = new HashMap<>();
    private final static Object lock = new Object();


    public static void addClient(TCPClient tcpClient) {
        synchronized (lock) {
            for (int i = 0; i < clientUsernames.size(); i++) {
                if (clientUsernames.get(i).equals(tcpClient.getUsername())) {
                    TCPClient lastTCP = clientTCPMap.remove(tcpClient.getUsername());
                    if (lastTCP != null) {
                        tcpClient.setMessages(lastTCP.getMessages());
                    }
                    clientTCPMap.put(tcpClient.getUsername(), tcpClient);
                    return;
                }
            }
        }
        clientTCPMap.put(tcpClient.getUsername() ,tcpClient);
        clientUsernames.add(tcpClient.getUsername());
        putClientGameMap(tcpClient.getUsername() ,new GameClient(tcpClient.getUsername()));
    }

    public static void putClientGameMap(String username ,GameClient gameClient) {
        synchronized (lock) {
            clientGameMap.put(username, gameClient);
        }
    }

    public static GameClient getGameClient(String username) {
        synchronized (lock) {
            return clientGameMap.get(username);
        }
    }



    public static ArrayList<Squad> getSquads() {
        synchronized (lock) {
            return squads;
        }
    }

    public static void addSquad(Squad squad) {
        synchronized (lock) {
            squads.add(squad);
        }
    }

    public static int getAvailablePort() {
        availablePort++;
        return availablePort;
    }


    public static TCPClient getTCPClient(String username) {
        synchronized (lock) {
            return clientTCPMap.get(username);
        }
    }

    public static void putClientSquadMap(String username ,Squad squad) {
        synchronized (lock) {
            clientSquadMap.remove(username);
            clientSquadMap.put(username, squad);
        }
    }

    public static Squad getClientSquad(String username) {
        synchronized (lock) {
            return clientSquadMap.get(username);
        }
    }

    public static Squad getSquad(String squadName) {
        synchronized (lock) {
            for (Squad squad : squads) {
                if (squad.getName().equals(squadName))
                    return squad;
            }
        }
        return null;
    }

    public static ArrayList<String> getClientUsernames() {
        synchronized (lock) {
            return clientUsernames;
        }
    }

    public static void removeSquad(Squad squad) {
        synchronized (lock) {
            for (Squad dataSquad : squads) {
                if (dataSquad.getName().equals(squad.getName())) {
                    squads.remove(dataSquad);
                    return;
                }
            }
        }
    }

    public static void putClientOnlineGame(String username ,Game game) {
        synchronized (lock) {
            clientOnlineGameMap.remove(username);
            clientOnlineGameMap.put(username, game);
        }
    }

    public static Game getOnlineGame(String username) {
        synchronized (lock) {
            return clientOnlineGameMap.get(username);
        }
    }

    public static void putClientPlayer(String username ,Player player) {
        synchronized (lock) {
        clientPlayerMap.remove(username);
        clientPlayerMap.put(username ,player);
        }
    }

    public static Player getPlayer(String username) {
        synchronized (lock) {
            return clientPlayerMap.get(username);
        }
    }

    public static Object getLock() {
        return lock;
    }

    public static HashMap<String, Squad> getClientSquadMap() {
        return clientSquadMap;
    }

    public static HashMap<String, GameClient> getClientGameMap() {
        return clientGameMap;
    }

    public static void setClientUsernames(ArrayList<String> clientUsernames) {
        OnlineData.clientUsernames = clientUsernames;
    }

    public static void setSquads(ArrayList<Squad> squads) {
        OnlineData.squads = squads;
    }

    public static void setClientSquadMap(HashMap<String, Squad> clientSquadMap) {
        OnlineData.clientSquadMap = clientSquadMap;
    }

    public static void setClientGameMap(HashMap<String, GameClient> clientGameMap) {
        OnlineData.clientGameMap = clientGameMap;
    }
}
