package controller.tcp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.client.Client;

public class TCPServiceListener {
    private static final Object signedUpClientsLock = new Object();
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
        while (true) {
            if (isConnectionLost())
                break;
            try {
                Thread.sleep(500);
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
                    synchronized (signedUpClientsLock) {
                        new ClientSignUpRequest(client).checkRequest();
                    }
                    break;
                case logIn:
                    synchronized (signedUpClientsLock) {
                        new ClientLogInRequest(client).checkRequest();
                    }
                    break;
            }
        }
        client.getTcpMessager().close();
    }

    private boolean isConnectionLost() {
//        try {
//            client.getTcpMessager().sendMessage(ServerMessageType.connectionCheck);
//        }
//        catch (Exception e) {
//            return true;
//        }
//        return false;
        return false;
    }
}
