package controller;

import controller.client.Client;

import java.util.ArrayList;

public class OnlineData {

    private static ArrayList<Client> onlineClients = new ArrayList<>();

    public synchronized static ArrayList<Client> getOnlineClients() {
        return onlineClients;
    }

    public synchronized static void setOnlineClients(ArrayList<Client> onlineClients) {
        OnlineData.onlineClients = onlineClients;
    }

    public synchronized static void addOnlineClient(Client client) {
        onlineClients.add(client);
    }

    public synchronized static void removeOnlineClient(Client client) {
        for (Client onlineClient : onlineClients) {
            if (onlineClient.getUsername().equals(client.getUsername())) {
                onlineClients.remove(onlineClient);
                return;
            }
        }
    }

}
