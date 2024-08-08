package controller.online.tcp.requests;

import com.google.gson.Gson;
import controller.game.GameType;
import controller.online.dataBase.OnlineData;
import controller.online.client.TCPClient;
import controller.online.tcp.TCPClientRequest;
import controller.online.tcp.messages.invitePlayer.ClientInviteGameRequestMessage;

public class ClientInviteGameRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientInviteGameRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        gson = new Gson();
    }

    @Override
    public void checkRequest() {

        String JGameType = tcpClient.getTcpMessager().readMessage();
        String invitedPlayer = tcpClient.getTcpMessager().readMessage();
        GameType gameType = gson.fromJson(JGameType ,GameType.class);

        OnlineData.getTCPClient(invitedPlayer).addMessage(
                new ClientInviteGameRequestMessage(invitedPlayer ,tcpClient.getUsername() ,gameType)
        );
    }
}
