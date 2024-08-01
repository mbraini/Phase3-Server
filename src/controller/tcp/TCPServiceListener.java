package controller.tcp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.client.TCPClient;
import controller.tcp.requests.*;
import controller.tcp.requests.getAllSquadRequest.ClientGetAllSquadsRequest;
import controller.tcp.requests.getSquadMembers.ClientGetSquadMembersRequest;

public class TCPServiceListener {
    private final TCPClient tcpClient;
    private Gson gson;

    public TCPServiceListener(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
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
            if (tcpClient.getTcpMessager().hasMessage())
                clientRequest = tcpClient.getTcpMessager().readMessage();
            else
                continue;
            ClientRequestType requestType = gson.fromJson(clientRequest , ClientRequestType.class);
            switch (requestType) {
                case signUp :
                    new ClientSignUpRequest(tcpClient).checkRequest();
                    break;
                case logIn:
                    new ClientLogInRequest(tcpClient).checkRequest();
                    break;
                case hasSquad:
                    new ClientHasSquadRequest(tcpClient).checkRequest();
                    break;
                case getAllSquads:
                    new ClientGetAllSquadsRequest(tcpClient).checkRequest();
                    break;
                case createSquad:
                    new ClientCreateSquadRequest(tcpClient).checkRequest();
                    break;
                case joinSquad:
                    new ClientJoinSquadRequest(tcpClient).checkRequest();
                    break;
                case getSquadMembers:
                    new ClientGetSquadMembersRequest(tcpClient).checkRequest();
                    break;
                case leaveSquad:
                    new ClientLeaveSquadRequest(tcpClient).checkRequest();
            }
        }
        System.out.println("LOST CLIENT!");
        tcpClient.getTcpMessager().close();
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
