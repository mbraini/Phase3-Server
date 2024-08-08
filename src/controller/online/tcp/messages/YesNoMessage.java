package controller.online.tcp.messages;

import controller.online.annotations.SkippedByJson;
import controller.online.dataBase.OnlineData;
import controller.online.tcp.ServerMessageType;
import utils.TCPMessager;

public abstract class YesNoMessage extends ClientMessage{

    @SkippedByJson
    protected TCPMessager receiverMessager;
    public YesNoMessage(String receiver) {
        super(receiver);
        messageType = ServerMessageType.yes_no_message;
    }

    public void deliverMessage() {
        OnlineData.getTCPClient(receiver).getTcpMessager().sendMessage(messageType);
    }

}
