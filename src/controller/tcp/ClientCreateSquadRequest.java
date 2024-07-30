package controller.tcp;

import constants.CostConstants;
import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import utils.Helper;

import java.util.ArrayList;

public class ClientCreateSquadRequest extends TCPClientRequest{

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
                tcpClient.getTcpMessager().sendMessage(ServerMessageType.error);
                return;
            }
        }
        Squad newSquad = new Squad(squadName);
        newSquad.addMember(tcpClient);
        synchronized (OnlineData.getSquads()) {
            OnlineData.addSquad(newSquad);
        }
        OnlineData.getGameClient(tcpClient).setXp(
                OnlineData.getGameClient(tcpClient).getXp() - CostConstants.SQUAD_XP_COST
        );
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.done);
    }
}
