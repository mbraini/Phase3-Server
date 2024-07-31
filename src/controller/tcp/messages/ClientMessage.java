package controller.tcp.messages;

import controller.client.TCPClient;
import controller.tcp.ServerMessageType;

public abstract class ClientMessage {

    protected TCPClient receiver;
    protected ServerMessageType messageType;

    public ClientMessage(TCPClient receiver) {
        this.receiver = receiver;
    }

    public void deliverMessage() {
        receiver.getTcpMessager().sendMessage(messageType);
    }

}
