package controller.tcp.requests;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;
import controller.tcp.messages.ClientMessage;
import controller.tcp.messages.joinSquad.ClientJoinSquadRequestMessage;

import java.util.ArrayList;

public class ClientJoinSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;

    public ClientJoinSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void checkRequest() {
        String squadName = tcpClient.getTcpMessager().readMessage();
        Squad squad = findSquad(squadName);
        ArrayList<ClientMessage> messages;
        messages = (ArrayList<ClientMessage>) tcpClient.getMessages().clone();
        for (ClientMessage message : messages) {
            if (message instanceof ClientJoinSquadRequestMessage) {
                ClientJoinSquadRequestMessage joinReq = ((ClientJoinSquadRequestMessage) message);
                if (joinReq.getSquad().getName().equals(squad.getName())
                        && joinReq.getRequester().getUsername().equals(tcpClient.getUsername())
                )
                {
                    tcpClient.getTcpMessager().sendMessage(ServerMessageType.joinSquadRecponce);
                    tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
                   return;
                }
            }
        }
        squad.getOwner().addMessage(
                new ClientJoinSquadRequestMessage(
                        squad.getOwner(),
                        squad,
                        tcpClient
                )
        );
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.joinSquadRecponce);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }

    private Squad findSquad(String squadName) {
        ArrayList<Squad> squads;
        synchronized (OnlineData.getSquads()) {
            squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        }

        for (Squad squad : squads) {
            if (squad.getName().equals(squadName))
                return squad;
        }
        return null;
    }
}
