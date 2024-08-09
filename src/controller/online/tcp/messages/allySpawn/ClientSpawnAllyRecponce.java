package controller.online.tcp.messages.allySpawn;

import controller.online.dataBase.OnlineData;
import controller.online.tcp.messages.OKMessage;
import utils.TCPMessager;

public class ClientSpawnAllyRecponce extends OKMessage {

    public ClientSpawnAllyRecponce(String receiver) {
        super(receiver);
    }

    @Override
    public void deliverMessage() {
        super.deliverMessage();
        TCPMessager messager = OnlineData.getTCPClient(receiver).getTcpMessager();
        messager.sendMessage(
                "someone has accepted the call earlier!"
        );
    }
}
