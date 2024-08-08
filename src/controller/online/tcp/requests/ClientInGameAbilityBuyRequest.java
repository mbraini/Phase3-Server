package controller.online.tcp.requests;

import com.google.gson.Gson;
import controller.game.enums.InGameAbilityType;
import controller.game.player.Player;
import controller.online.dataBase.OnlineData;
import controller.online.client.TCPClient;
import controller.online.tcp.TCPClientRequest;

public class ClientInGameAbilityBuyRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientInGameAbilityBuyRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        gson = new Gson();
    }

    @Override
    public void checkRequest() {

        String JInGameAbilityType = tcpClient.getTcpMessager().readMessage();
        InGameAbilityType abilityType = gson.fromJson(JInGameAbilityType , InGameAbilityType.class);

        Player player = OnlineData.getPlayer(tcpClient.getUsername());
        player.getViewRequestController().inGameAbilityRequest(
                abilityType,
                player
        );

    }
}
