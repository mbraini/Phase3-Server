package controller.online;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.PathConstants;
import constants.RefreshRateConstants;
import controller.online.annotations.SkippedByJson;
import controller.online.client.ClientState;
import controller.online.client.GameClient;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.squad.Squad;
import controller.online.tcp.messages.ClientMessage;
import utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerThread extends Thread {

    private final Gson gson;

    public ServerThread() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(SkippedByJson.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                if (aClass.getAnnotation(SkippedByJson.class) == null)
                    return false;
                return true;
            }
        });
        gson = builder.create();
    }


    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(RefreshRateConstants.SERVER_THREAD_REFRESH_RATE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateServer();
        }

    }

    private void updateServer() {
        saveData();
        sendMessagesIf();
    }

    private void saveData() {
        ArrayList<Squad> squads;
        ArrayList<TCPClient> tcpClients;
        HashMap<String , GameClient> clientGameMap;
        synchronized (OnlineData.getLock()) {
            squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
            tcpClients = (ArrayList<TCPClient>) OnlineData.getTCPClients().clone();
            clientGameMap = (HashMap<String, GameClient>) OnlineData.getClientGameMap().clone();
        }
        Helper.writeFile(PathConstants.DATABASE_FOLDER_PATH + "squads.json" ,gson.toJson(squads));
        Helper.writeFile(PathConstants.DATABASE_FOLDER_PATH + "clients.json" ,gson.toJson(tcpClients));
        try {
            for (TCPClient tcpClient : tcpClients) {
                Helper.writeFile(
                        PathConstants.CLIENT_EARNINGS_FOLDER_PATH + "/" + tcpClient.getUsername() + "/earnings.json" ,
                        gson.toJson(clientGameMap.get(tcpClient.getUsername()))
                );
            }
        }
        catch (Exception e) {

        }
    }

    private void sendMessagesIf() {
        ArrayList<String> clientUsernames;
        synchronized (OnlineData.getClientUsernames()) {
            clientUsernames = (ArrayList<String>) OnlineData.getClientUsernames().clone();
        }
        for (String username : clientUsernames) {
            TCPClient tcpClient = OnlineData.getTCPClient(username);
            if (tcpClient == null)
                continue;
            if (tcpClient.getClientState().equals(ClientState.online)) {
                if (!tcpClient.getMessages().isEmpty()) {
                    for (ClientMessage clientMessage : tcpClient.getMessages()) {
                        clientMessage.deliverMessage();
                    }
                    tcpClient.setMessages(new ArrayList<>());
                }
            }
        }
    }
}
