package controller.tcp.messages.joinSquad;

import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.messages.ClientMessageRecponceType;
import controller.tcp.messages.OKMessage;

public class ClientJoinSquadRecponceMessage extends OKMessage {

    private ClientMessageRecponceType ownerRecponce;

    private Squad squad;

    public ClientJoinSquadRecponceMessage(TCPClient receiver , Squad squad, ClientMessageRecponceType ownerRecponce) {
        super(receiver);
        this.ownerRecponce = ownerRecponce;
        this.squad = squad;
    }

    @Override
    public void deliverMessage() {
        super.deliverMessage();

        if (ownerRecponce.equals(ClientMessageRecponceType.yes)) {
            receiver.getTcpMessager().sendMessage(
                    "you'r request for joining the squad " + "'" + squad.getName() + "'"
                    + " has been accepted by the owner"
            );
        }
        else {
            receiver.getTcpMessager().sendMessage(
                    "you'r request for joining the squad " + "'" + squad.getName() + "'"
                            + " has been declined by the owner"
            );
        }
    }
}
