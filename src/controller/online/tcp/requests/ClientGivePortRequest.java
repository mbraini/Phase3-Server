package controller.online.tcp.requests;

import controller.game.Game;
import controller.game.player.Player;
import controller.online.dataBase.OnlineData;
import controller.online.client.TCPClient;
import controller.online.tcp.TCPClientRequest;
import controller.online.tcp.messages.ServerGivePortRequest;

public class ClientGivePortRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientGivePortRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {

        int abilityPort = Integer.valueOf(tcpClient.getTcpMessager().readMessage());
        int effectPort = Integer.valueOf(tcpClient.getTcpMessager().readMessage());
        int framePort = Integer.valueOf(tcpClient.getTcpMessager().readMessage());
        int objectPort = Integer.valueOf(tcpClient.getTcpMessager().readMessage());
        int variablesPort = Integer.valueOf(tcpClient.getTcpMessager().readMessage());

        Game game = OnlineData.getOnlineGame(tcpClient.getUsername());
        Player player = OnlineData.getPlayer(tcpClient.getUsername());

        player.setAbilityPort(abilityPort);
        player.setEffectPort(effectPort);
        player.setFramePort(framePort);
        player.setObjectPort(objectPort);
        player.setVariablesPort(variablesPort);

        new ServerGivePortRequest(tcpClient).sendRequest();
    }
}
