package controller.tcp.requests;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import constants.PathConstants;
import controller.OnlineData;
import controller.SkippedByJson;
import controller.client.TCPClient;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;
import utils.Helper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ClientSignUpRequest extends TCPClientRequest {

    private final TCPClient TCPClient;
    private Gson gson;

    public ClientSignUpRequest(TCPClient TCPClient) {
        this.TCPClient = TCPClient;
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
        Type type = new TypeToken<ArrayList<TCPClient>>(){}.getType();
        ArrayList<TCPClient> TCPClients = gson.fromJson(JText.toString() ,type);
        for (TCPClient TCPClient : TCPClients) {
            if (TCPClient.getUsername().equals(this.TCPClient.getUsername())) {
                this.TCPClient.getTcpMessager().sendMessage(ServerRecponceType.error);
                return;
            }
        }
        TCPClients.add(TCPClient);
        Helper.writeFile(PathConstants.SIGNED_UP_CLIENTS_PATH ,gson.toJson(TCPClients));

        setUpFolder();
        OnlineData.addClient(TCPClient);
        this.TCPClient.getTcpMessager().sendMessage(ServerRecponceType.done);
        OnlineData.getGameClient(this.TCPClient.getUsername()).update(this.TCPClient);
    }

    private void setUpFolder() {
        try {
            Files.createDirectory(Path.of(PathConstants.CLIENT_EARNINGS_FOLDER_PATH + TCPClient.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getClientInfo() {
        String username = TCPClient.getTcpMessager().readMessage();
        String ip = TCPClient.getTcpMessager().readMessage();
        TCPClient.setUsername(username);
        TCPClient.setIp(ip);
    }
}
