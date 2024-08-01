package controller.tcp.messages;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.tcp.ServerMessageType;

public abstract class ClientMessage {

    protected String receiver;
    protected ServerMessageType messageType;

    public ClientMessage(String receiver) {
        this.receiver = receiver;
    }

    public void deliverMessage() {
        OnlineData.getTCPClient(receiver).getTcpMessager().sendMessage(messageType);
    }

}
