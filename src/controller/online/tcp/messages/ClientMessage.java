package controller.online.tcp.messages;

import controller.online.dataBase.OnlineData;
import controller.online.tcp.ServerMessageType;

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
