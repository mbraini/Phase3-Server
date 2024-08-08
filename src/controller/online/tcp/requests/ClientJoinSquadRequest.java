package controller.online.tcp.requests;

import controller.online.dataBase.OnlineData;
import controller.online.dataBase.OnlineDataHelper;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;
import controller.online.tcp.messages.ClientMessage;
import controller.online.tcp.messages.joinSquad.ClientJoinSquadRequestMessage;

import java.util.ArrayList;

public class ClientJoinSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientJoinSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        String squadName = tcpClient.getTcpMessager().readMessage();
        Squad squad = OnlineDataHelper.findSquad(squadName);
        ArrayList<ClientMessage> messages;
        messages = (ArrayList<ClientMessage>) tcpClient.getMessages().clone();
        for (ClientMessage message : messages) {
            if (message instanceof ClientJoinSquadRequestMessage) {
                ClientJoinSquadRequestMessage joinReq = ((ClientJoinSquadRequestMessage) message);
                if (joinReq.getSquad().getName().equals(squad.getName())
                        && joinReq.getRequester().equals(tcpClient.getUsername())
                )
                {
                    tcpClient.getTcpMessager().sendMessage(ServerMessageType.joinSquadRecponce);
                    tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
                   return;
                }
            }
        }
        String ownerUsername = squad.getOwner();
        OnlineData.getTCPClient(ownerUsername).addMessage(
                new ClientJoinSquadRequestMessage(
                        squad.getOwner(),
                        squad,
                        tcpClient.getUsername()
                )
        );
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.joinSquadRecponce);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }
}
