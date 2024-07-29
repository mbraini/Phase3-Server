package controller.tcp;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import constants.PathConstants;
import controller.SkippedByJson;
import controller.client.Client;
import utils.Helper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ClientSignUpRequest extends TCPClientRequest{

    private final Client client;
    private Gson gson;

    public ClientSignUpRequest(Client client) {
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

        StringBuilder JText = Helper.readFile(PathConstants.SIGNED_UP_CLIENTS_PATH);
        Type type = new TypeToken<ArrayList<Client>>(){}.getType();
        ArrayList<Client> clients = gson.fromJson(JText.toString() ,type);
        for (Client client : clients) {
            if (client.getUsername().equals(this.client.getUsername())) {
                this.client.getTcpMessager().sendMessage(ServerMessageType.error);
                return;
            }
        }
        clients.add(client);
        Helper.writeFile(PathConstants.SIGNED_UP_CLIENTS_PATH ,gson.toJson(clients));

        setUpFolder();
        this.client.getTcpMessager().sendMessage(ServerMessageType.done);
    }

    private void setUpFolder() {
        try {
            Files.createDirectory(Path.of(PathConstants.CLIENT_EARNINGS_FOLDER_PATH + client.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getClientInfo() {
        String username = client.getTcpMessager().readMessage();
        String ip = client.getTcpMessager().readMessage();
        client.setUsername(username);
        client.setIp(ip);
    }
}
