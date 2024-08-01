package controller.tcp.messages;

import controller.OnlineData;
import controller.client.TCPClient;
import controller.tcp.ServerMessageType;
import utils.TCPMessager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class YesNoMessage extends ClientMessage{

    protected ServerMessageType messageType;
    protected TCPMessager receiverMessager;
    public YesNoMessage(String receiver) {
        super(receiver);
        messageType = ServerMessageType.yes_no_message;
    }

    public void deliverMessage() {
        OnlineData.getTCPClient(receiver).getTcpMessager().sendMessage(messageType);
    }

}
