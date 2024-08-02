package controller.tcp.requests;

import com.google.gson.Gson;
import constants.CostConstants;
import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.CallType;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;

public class ClientBuyCallRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientBuyCallRequest(TCPClient tcpClient){
        this.tcpClient = tcpClient;
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void checkRequest() {
        String JCallType = tcpClient.getTcpMessager().readMessage();
        CallType callType = gson.fromJson(JCallType ,CallType.class);
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.buyCall);
        switch (callType) {
            case adonis :
                checkAdonis(squad);
                break;
            case palioxis:
                checkPalioxis(squad);
                break;
            case gefjon:
                checkGefjon(squad);
                break;
        }

    }

    private void checkGefjon(Squad squad) {
        tcpClient.getTcpMessager().sendMessage(CallType.gefjon);
        if (squad.getTreasury().getXp() >= CostConstants.GEFJON_XP_COST) {
            squad.getTreasury().setXp(squad.getTreasury().getXp() - CostConstants.GEFJON_XP_COST);
            squad.getTreasury().setGefjonCount(squad.getTreasury().getGefjonCount() + 1);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
            return;
        }
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
    }

    private void checkPalioxis(Squad squad) {
        int palioxisCost = squad.getMembers().size() * 100;
        tcpClient.getTcpMessager().sendMessage(CallType.palioxis);
        if (squad.getTreasury().getXp() >= palioxisCost) {
            squad.getTreasury().setXp(squad.getTreasury().getXp() - palioxisCost);
            squad.getTreasury().setPalioxisCount(squad.getTreasury().getPalioxisCount() + 1);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
            return;
        }
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
    }

    private void checkAdonis(Squad squad) {
        tcpClient.getTcpMessager().sendMessage(CallType.adonis);
        if (squad.getTreasury().getXp() >= CostConstants.ADONIS_XP_COST) {
            squad.getTreasury().setXp(squad.getTreasury().getXp() - CostConstants.ADONIS_XP_COST);
            squad.getTreasury().setAdonisCount(squad.getTreasury().getAdonisCount() + 1);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
            return;
        }
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
    }
}
