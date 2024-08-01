package controller.tcp.messages.joinSquad;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.messages.ClientMessageRecponceType;
import controller.tcp.messages.OKMessage;
import utils.TCPMessager;

public class ClientJoinSquadRecponceMessage extends OKMessage {

    private ClientMessageRecponceType ownerRecponce;

    private Squad squad;

    public ClientJoinSquadRecponceMessage(String receiver , Squad squad, ClientMessageRecponceType ownerRecponce) {
        super(receiver);
        this.ownerRecponce = ownerRecponce;
        this.squad = squad;
    }

    @Override
    public void deliverMessage() {
        super.deliverMessage();

        TCPMessager messager = OnlineData.getTCPClient(receiver).getTcpMessager();
        if (ownerRecponce.equals(ClientMessageRecponceType.yes)) {
            messager.sendMessage(
                    "you'r request for joining the squad " + "'" + squad.getName() + "'"
                    + " has been accepted by the owner"
            );
        }
        else {
            messager.sendMessage(
                    "you'r request for joining the squad " + "'" + squad.getName() + "'"
                            + " has been declined by the owner"
            );
        }
    }
}
