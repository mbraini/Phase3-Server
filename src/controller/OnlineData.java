package controller;

import controller.client.Client;

import java.util.ArrayList;

public class OnlineData {

    private static ArrayList<Client> onlineClients = new ArrayList<>();

    public static ArrayList<Client> getOnlineClients() {
        return onlineClients;
    }

    public static void setOnlineClients(ArrayList<Client> onlineClients) {
        OnlineData.onlineClients = onlineClients;
    }
}
