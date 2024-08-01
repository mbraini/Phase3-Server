package controller.tcp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.client.TCPClient;
import controller.tcp.requests.ClientCreateSquadRequest;
import controller.tcp.requests.getAllSquadRequest.ClientGetAllSquadsRequest;
import controller.tcp.requests.ClientHasSquadRequest;
import controller.tcp.requests.ClientJoinSquadRequest;
import controller.tcp.requests.ClientLogInRequest;
import controller.tcp.requests.ClientSignUpRequest;
import controller.tcp.requests.getSquadMembers.ClientGetSquadMembersRequest;

public class TCPServiceListener {
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
                    new ClientSignUpRequest(TCPClient).checkRequest();
                    break;
                case logIn:
                    new ClientLogInRequest(TCPClient).checkRequest();
                    break;
                case hasSquad:
                    new ClientHasSquadRequest(TCPClient).checkRequest();
                    break;
                case getAllSquads:
                    new ClientGetAllSquadsRequest(TCPClient).checkRequest();
                    break;
                case createSquad:
                    new ClientCreateSquadRequest(TCPClient).checkRequest();
                    break;
                case joinSquad:
                    new ClientJoinSquadRequest(TCPClient).checkRequest();
                    break;
                case getSquadMembers:
                    new ClientGetSquadMembersRequest(TCPClient).checkRequest();
                    break;
            }
        }
        System.out.println("LOST CLIENT!");
        TCPClient.getTcpMessager().close();
    }

    private boolean isConnectionLost() {
//        try {
//            TCPClient.getTcpMessager().sendMessage(ServerRecponceType.connectionCheck);
//        }
//        catch (Exception e) {
//            return true;
//        }
//        return false;
        return false;
    }
}
