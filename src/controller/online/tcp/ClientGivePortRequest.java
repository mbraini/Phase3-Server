package controller.online.tcp;

import controller.game.Game;
import controller.game.player.Player;
import controller.online.OnlineData;
import controller.online.client.TCPClient;

public class ClientGivePortRequest extends TCPClientRequest{

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
        Player player = game.getPlayer(tcpClient.getUsername());

        player.setAbilityPort(abilityPort);
        player.setEffectPort(effectPort);
        player.setFramePort(framePort);
        player.setObjectPort(objectPort);
        player.setVariablesPort(variablesPort);

        System.out.println("done");
    }
}
