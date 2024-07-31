package controller.tcp.messages;

import controller.client.TCPClient;
import controller.tcp.ServerMessageType;

public abstract class OKMessage extends ClientMessage{
    public OKMessage(TCPClient receiver) {
        super(receiver);
        messageType = ServerMessageType.ok_message;
    }
}
