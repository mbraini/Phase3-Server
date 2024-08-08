package controller.online.tcp.messages.joinSquad;

import com.google.gson.Gson;
import controller.online.annotations.SkippedByJson;
import controller.online.dataBase.OnlineData;
import controller.online.squad.Squad;
import controller.online.tcp.messages.ClientMessageRecponceType;
import controller.online.tcp.messages.YesNoMessage;
import utils.TCPMessager;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientJoinSquadRequestMessage extends YesNoMessage {

    private String requester;
    private Squad squad;
    private String squadName;
    @SkippedByJson
    protected MessageThread messageThread;
    private int port;
    @SkippedByJson
    private Gson gson;

    public ClientJoinSquadRequestMessage(String receiver, String squadName , String requester) {
        super(receiver);
        this.requester = requester;
        this.squadName = squadName;
    }

    private void initPort() {
        port = OnlineData.getAvailablePort();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void deliverMessage() {
        this.squad = OnlineData.getSquad(squadName);
        messageThread = new MessageThread();
        initGson();
        initPort();
        super.deliverMessage();
        messageThread.start();
        String ownerUsername = squad.getOwner();
        OnlineData.getTCPClient(ownerUsername).getTcpMessager().sendMessage(port);
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
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
                    "player " + "'" + requester + "'" + " has requested to join your squad"
                            + ". do you accept?"
            );
            String JRecponce = receiverMessager.readMessage();
            ClientMessageRecponceType ownerRecponce = gson.fromJson(JRecponce , ClientMessageRecponceType.class);
            if (ownerRecponce.equals(ClientMessageRecponceType.yes)) {
                squad.addMember(requester);
            }
            OnlineData.getTCPClient(requester).addMessage(
                    new ClientJoinSquadRecponceMessage(requester , squad ,ownerRecponce)
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
