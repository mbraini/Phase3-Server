package controller.online.tcp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.online.OnlineData;
import controller.online.client.TCPClient;
import controller.online.tcp.requests.*;
import controller.online.tcp.requests.getAllSquadRequest.ClientGetAllSquadsRequest;
import controller.online.tcp.requests.updateBattleSquad.ClientUpdateBattleSquadRequest;
import controller.online.tcp.requests.updateHasSquad.ClientUpdateHasSquadRequest;

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
        tcpClient.getConnectionChecker().start();
        while (true) {
            try {
                Thread.sleep(1000);
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
                case leaveSquad:
                    new ClientLeaveSquadRequest(tcpClient).checkRequest();
                    break;
                case kickOut:
                    new ClientKickOutSquadRequest(tcpClient).checkRequest();
                    break;
                case killSquad:
                    new ClientKillSquadRequest(tcpClient).checkRequest();
                    break;
                case updateHasSquad:
                    new ClientUpdateHasSquadRequest(tcpClient).checkRequest();
                    break;
                case hasSquadBattle:
                    new ClientHasBattleSquadRequest(tcpClient).checkRequest();
                    break;
                case updateBattleSquad:
                    new ClientUpdateBattleSquadRequest(tcpClient).checkRequest();
                    break;
                case updateTreasury:
                    new ClientUpdateTreasuryRequest(tcpClient).checkRequest();
                    break;
                case updateTreasuryShop:
                    new ClientUpdateTreasuryShopRequest(tcpClient).checkRequest();
                    break;
                case donateXP:
                    new ClientDonateXPRequest(tcpClient).checkRequest();
                    break;
                case buyCall:
                    new ClientBuyCallRequest(tcpClient).checkRequest();
                    break;
                case givePorts:
                    new ClientGivePortRequest(tcpClient).checkRequest();
                    break;
                case buyInGameAbility:
                    new ClientInGameAbilityBuyRequest(tcpClient).checkRequest();
                    break;
            }
        }
    }
}
