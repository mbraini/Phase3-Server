package controller.tcp;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.SkippedByJson;
import controller.client.Client;
import utils.Helper;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SingUpRequest extends TCPClientRequest{

    private final Client client;
    private Gson gson;

    public SingUpRequest(Client client) {
        this.client = client;
        initGson();
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
                return aClass.getAnnotation(SkippedByJson.class) != null;
            }
        });
        gson = builder.create();
    }


    @Override
    public void checkRequest() {
        getClientInfo();

        StringBuilder JText = Helper.readFile("src/controller/tcp/clients.json");
        Type type = new TypeToken<ArrayList<Client>>(){}.getType();
        ArrayList<Client> clients = gson.fromJson(JText.toString() ,type);
        for (Client client : clients) {
            if (client.getUsername().equals(this.client.getUsername())) {
                this.client.getTcpMessager().sendMessage(ServerMessageType.error);
                return;
            }
        }
        clients.add(client);
        Helper.writeFile("src/controller/tcp/clients.json" ,gson.toJson(clients));

    }

    private void getClientInfo() {
        String username = client.getTcpMessager().readMessage();
        String ip = client.getTcpMessager().readMessage();
        client.setUsername(username);
        client.setIp(ip);
    }
}
