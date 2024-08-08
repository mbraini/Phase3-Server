package controller.online.tcp.requests;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.PathConstants;
import controller.online.dataBase.OnlineData;
import controller.online.annotations.SkippedByJson;
import controller.online.client.TCPClient;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ClientSignUpRequest extends TCPClientRequest {

    private final TCPClient tcpClient;
    private Gson gson;

    public ClientSignUpRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
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
        ArrayList<TCPClient> tcpClients = OnlineData.getTCPClients();
        for (TCPClient tcpClient : tcpClients) {
            if (tcpClient.getUsername().equals(this.tcpClient.getUsername())){
                this.tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
                return;
            }
        }
        OnlineData.addClient(this.tcpClient);
        setUpFolder();
        OnlineData.addClient(tcpClient);
        this.tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
        OnlineData.getGameClient(this.tcpClient.getUsername()).update(this.tcpClient);
    }

    private void setUpFolder() {
        try {
            Files.createDirectory(Path.of(PathConstants.CLIENT_EARNINGS_FOLDER_PATH + "/" + tcpClient.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getClientInfo() {
        String username = tcpClient.getTcpMessager().readMessage();
        String ip = tcpClient.getTcpMessager().readMessage();
        tcpClient.setUsername(username);
        tcpClient.setIp(ip);
    }
}
