package controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import constants.PathConstants;
import controller.online.annotations.SkippedByJson;
import controller.online.client.ClientState;
import controller.online.client.GameClient;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.ServerCLIListener;
import controller.online.ServerThread;
import controller.online.squad.Squad;
import controller.online.tcp.ServerWorker;
import utils.Helper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Application implements Runnable{

    private Gson gson;

    @Override
    public void run() {
        initGson();
        loadJSonFiles();

        for (int i = 0; i < 20 ;i++) {
            new ServerWorker().start();
        }
        new ServerThread().start();
        ////cli
        new ServerCLIListener().start();
        ////
        try {
            ServerWorker.serverWorker.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initGson() {
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

    private void loadJSonFiles() {
        Type usernamesType = new TypeToken<ArrayList<TCPClient>>(){}.getType();
        Type squadType = new TypeToken<ArrayList<Squad>>(){}.getType();

        StringBuilder JUsernames = Helper.readFile(PathConstants.DATABASE_FOLDER_PATH + "clients.json");
        StringBuilder JSquads = Helper.readFile(PathConstants.DATABASE_FOLDER_PATH + "squads.json");

        ArrayList<TCPClient> tcpClients = gson.fromJson(JUsernames.toString() ,usernamesType);
        HashMap<String ,TCPClient> tcpClientMap = new HashMap<>();
        ArrayList<String> usernames = new ArrayList<>();
        for (TCPClient tcpClient : tcpClients) {
            usernames.add(tcpClient.getUsername());
            tcpClient.setClientState(ClientState.offline);
            tcpClientMap.put(tcpClient.getUsername() ,tcpClient);
        }
        ArrayList<Squad> squads = gson.fromJson(JSquads.toString() ,squadType);
        HashMap<String ,Squad> clientSquadMap = new HashMap<>();

        for (Squad squad : squads) {
            for (String member : squad.getMembers()) {
                clientSquadMap.put(member ,squad);
            }
        }

        HashMap<String ,GameClient> clientGameMap = new HashMap<>();

        for (String username : usernames) {
            try {
                StringBuilder JSon = Helper.readFile(PathConstants.CLIENT_EARNINGS_FOLDER_PATH + "/" + username + "/earnings.json");
                GameClient gameClient = gson.fromJson(JSon.toString() ,GameClient.class);
                clientGameMap.put(username ,gameClient);
            }
            catch (Exception e) {
                clientGameMap.put(username ,new GameClient(username));
            }
        }



        OnlineData.setClientUsernames(usernames);
        OnlineData.setClientSquadMap(clientSquadMap);
        OnlineData.setClientGameMap(clientGameMap);
        OnlineData.setSquads(squads);
        OnlineData.setClientTCPMap(tcpClientMap);
    }

}
