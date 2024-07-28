package controller.tcp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.client.Client;
import utils.TCPMessager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TCPServiceListener {

    private final Client client;
    private Gson gson;

    public TCPServiceListener(Client client) {
        this.client = client;
        initGson();
    }

    private void initGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        gson = builder.create();
    }

    private void checkClients() {

    }

    public void listen() {
        while (client.getTcpMessager().getSocket().isConnected()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String clientRequest;
            if (client.getTcpMessager().hasMessage())
                clientRequest = client.getTcpMessager().readMessage();
            else
                continue;
            ClientRequestType requestType = gson.fromJson(clientRequest , ClientRequestType.class);
            switch (requestType) {
                case signUp :
                    new SingUpRequest(client).checkRequest();
                    break;
            }
        }
        client.getTcpMessager().close();
    }
}
