package controller.online.tcp.requests;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.online.dataBase.OnlineData;
import controller.online.annotations.SkippedByJson;
import controller.online.client.TCPClient;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;
import controller.online.tcp.messages.updateXP.ClientUpdateXPMessage;

import java.util.ArrayList;

public class ClientLogInRequest extends TCPClientRequest {
    private final TCPClient tcpClient;
    private Gson gson;

    public ClientLogInRequest(TCPClient tcpClient) {
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

        ArrayList<TCPClient> TCPClients = OnlineData.getTCPClients();
        for (TCPClient TCPClient : TCPClients) {
            if (TCPClient.getUsername().equals(this.tcpClient.getUsername())) {
                this.tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
                new ClientUpdateXPMessage(tcpClient).sendRequest();
                OnlineData.addClient(this.tcpClient);
                return;
            }
        }
        this.tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
    }

    private void getClientInfo() {
        String username = tcpClient.getTcpMessager().readMessage();
        String ip = tcpClient.getTcpMessager().readMessage();
        tcpClient.setUsername(username);
        tcpClient.setIp(ip);
    }
}
