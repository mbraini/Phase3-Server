package controller.online.tcp.messages;

import controller.online.client.TCPClient;
import controller.online.tcp.ServerMessageType;

public abstract class OKMessage extends ClientMessage{
    public OKMessage(String receiver) {
        super(receiver);
        messageType = ServerMessageType.ok_message;
    }
}
