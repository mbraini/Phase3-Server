package controller.online.tcp.messages;

import controller.game.player.Player;
import controller.game.player.udp.ClientGameInfoReceiver;
import controller.online.OnlineData;
import controller.online.client.TCPClient;
import controller.online.tcp.ServerMessageType;

public class ServerGivePortRequest {

    private TCPClient tcpClient;

    public ServerGivePortRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public void sendRequest() {
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.givePorts);
        int port = OnlineData.getAvailablePort();
        tcpClient.getTcpMessager().sendMessage(port);
        Player player = OnlineData.getPlayer(tcpClient.getUsername());
        new ClientGameInfoReceiver(port ,player).start();
    }

}
