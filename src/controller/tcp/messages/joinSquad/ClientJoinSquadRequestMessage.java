package controller.tcp.messages.joinSquad;

import com.google.gson.Gson;
import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.messages.ClientMessageRecponceType;
import controller.tcp.messages.YesNoMessage;
import utils.TCPMessager;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientJoinSquadRequestMessage extends YesNoMessage {

    private TCPClient requester;
    private Squad squad;
    protected MessageThread messageThread;
    private int port;
    private Gson gson;

    public ClientJoinSquadRequestMessage(TCPClient receiver, Squad squad , TCPClient requester) {
        super(receiver);
        this.squad = squad;
        this.requester = requester;
        messageThread = new MessageThread();
        initGson();
        initPort();
    }

    private void initPort() {
        port = OnlineData.getAvailablePort();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void deliverMessage() {
        super.deliverMessage();
        messageThread.start();
        squad.getOwner().getTcpMessager().sendMessage(port);
    }

    public TCPClient getRequester() {
        return requester;
    }

    public void setRequester(TCPClient requester) {
        this.requester = requester;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public class MessageThread extends Thread {

        @Override
        public void run() {
            initMessager();
            receiverMessager.sendMessage(
                    "player " + "'" + requester.getUsername() + "'" + " has requested to join your squad"
                            + ". do you accept?"
            );
            String JRecponce = receiverMessager.readMessage();
            ClientMessageRecponceType ownerRecponce = gson.fromJson(JRecponce , ClientMessageRecponceType.class);
            if (ownerRecponce.equals(ClientMessageRecponceType.yes)) {
                squad.addMember(requester);
            }
            requester.addMessage(
                    new ClientJoinSquadRecponceMessage(requester , squad,ownerRecponce)
            );
        }

        private void initMessager() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                receiverMessager = new TCPMessager(socket);
            }
            catch (Exception e) {

            }
        }
    }

}
