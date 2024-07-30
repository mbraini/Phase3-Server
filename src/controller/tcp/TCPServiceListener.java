package controller.tcp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.OnlineData;
import controller.client.TCPClient;

public class TCPServiceListener {
    private static final Object signedUpClientsLock = new Object();
    private final TCPClient TCPClient;
    private Gson gson;

    public TCPServiceListener(TCPClient TCPClient) {
        this.TCPClient = TCPClient;
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
            if (TCPClient.getTcpMessager().hasMessage())
                clientRequest = TCPClient.getTcpMessager().readMessage();
            else
                continue;
            ClientRequestType requestType = gson.fromJson(clientRequest , ClientRequestType.class);
            switch (requestType) {
                case signUp :
                    synchronized (signedUpClientsLock) {
                        new ClientSignUpRequest(TCPClient).checkRequest();
                    }
                    break;
                case logIn:
                    synchronized (signedUpClientsLock) {
                        new ClientLogInRequest(TCPClient).checkRequest();
                    }
                    break;
                case hasSquad:
                    new ClientHasSquadRequest(TCPClient).checkRequest();
            }
        }
        TCPClient.getTcpMessager().close();
    }

    private boolean isConnectionLost() {
//        try {
//            TCPClient.getTcpMessager().sendMessage(ServerMessageType.connectionCheck);
//        }
//        catch (Exception e) {
//            return true;
//        }
//        return false;
        return false;
    }
}
