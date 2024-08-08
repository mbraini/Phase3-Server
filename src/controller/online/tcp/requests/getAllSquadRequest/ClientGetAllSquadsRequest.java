package controller.online.tcp.requests.getAllSquadRequest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.online.dataBase.OnlineData;
import controller.online.annotations.SkippedForClient;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.TCPClientRequest;

import java.util.ArrayList;

public class ClientGetAllSquadsRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientGetAllSquadsRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        initGson();
    }

    private void initGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(SkippedForClient.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                if (aClass.getAnnotation(SkippedForClient.class) == null)
                    return false;
                return true;
            }
        });
        gson = builder.create();
    }

    @Override
    public void checkRequest() {
        ArrayList<Squad> squads;
        synchronized (OnlineData.getSquads()) {
            squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        }
        ArrayList<GetAllSquadHelper> helpers = new ArrayList<>();
        for (Squad squad : squads) {
            helpers.add(new GetAllSquadHelper(squad.getName() ,squad.getMembers().size()));
        }
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.getAllSquadsRecponce);
        tcpClient.getTcpMessager().sendMessage(gson.toJson(helpers));
    }
}
