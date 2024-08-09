package controller.online.tcp.requests;

import constants.CostConstants;
import controller.online.dataBase.OnlineData;
import controller.online.client.GameClient;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

import java.util.HashMap;

public class ClientDonateXPRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientDonateXPRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        int donatedAmount = Integer.valueOf(tcpClient.getTcpMessager().readMessage());
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        HashMap<String ,Integer> map = squad.getTreasury().getDonateMap();
        int xpDonatedBefore;
        if (map.get(tcpClient.getUsername()) == null)
            xpDonatedBefore = 0;
        else
            xpDonatedBefore = map.get(tcpClient.getUsername());
        if (squad.getSquadBattle().getInBattleWith() != null) {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.donateXP);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
            return;
        }
        if (OnlineData.getGameClient(tcpClient.getUsername()).getXp() < donatedAmount){
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.donateXP);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
            return;
        }
        if (xpDonatedBefore + donatedAmount <= CostConstants.MAXIMUM_MEMBER_DONATION) {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.donateXP);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
            map.remove(tcpClient.getUsername());
            map.put(tcpClient.getUsername() ,xpDonatedBefore + donatedAmount);
            GameClient gameClient = OnlineData.getGameClient(tcpClient.getUsername());
            gameClient.setXp(gameClient.getXp() - donatedAmount);
            squad.getTreasury().setXp(squad.getTreasury().getXp() + donatedAmount);
        }
        else {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.donateXP);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
        }
    }
}
