package controller.online.tcp.requests;

import constants.CostConstants;
import controller.online.dataBase.OnlineData;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;
import controller.online.tcp.messages.updateXP.ClientUpdateXPMessage;

import java.util.ArrayList;

public class ClientCreateSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientCreateSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {

        String squadName = tcpClient.getTcpMessager().readMessage();
        ArrayList<Squad> squads;
        synchronized (OnlineData.getSquads()) {
            squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        }

        for (Squad squad : squads) {
            if (squad.getName().equals(squadName)) {
                tcpClient.getTcpMessager().sendMessage(ServerMessageType.createSquadRecponce);
                tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
                return;
            }
        }
        if (OnlineData.getGameClient(tcpClient.getUsername()).getXp() < CostConstants.SQUAD_XP_COST) {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.createSquadRecponce);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
            return;
        }
        Squad newSquad = new Squad(squadName);
        newSquad.addMember(tcpClient.getUsername());
        newSquad.setOwner(tcpClient.getUsername());
        synchronized (OnlineData.getSquads()) {
            OnlineData.addSquad(newSquad);
        }
        OnlineData.getGameClient(tcpClient.getUsername()).setXp(
                OnlineData.getGameClient(tcpClient.getUsername()).getXp() - CostConstants.SQUAD_XP_COST
        );

        tcpClient.getTcpMessager().sendMessage(ServerMessageType.createSquadRecponce);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);

        new ClientUpdateXPMessage(tcpClient).sendRequest();
    }
}
