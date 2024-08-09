package controller.online.tcp.messages.squadBattleNotification;

import controller.online.dataBase.OnlineData;
import controller.online.tcp.messages.ClientMessage;
import controller.online.tcp.messages.OKMessage;

public class ClientSquadBattleNotificationMessage extends OKMessage {

    public String enemySquad;

    public ClientSquadBattleNotificationMessage(String receiver ,String enemySquad) {
        super(receiver);
        this.enemySquad = enemySquad;
    }

    @Override
    public void deliverMessage() {
        super.deliverMessage();
        String message = "you are in battle with '" + enemySquad + "' squad!";
        OnlineData.getTCPClient(receiver).getTcpMessager().sendMessage(message);
    }
}
