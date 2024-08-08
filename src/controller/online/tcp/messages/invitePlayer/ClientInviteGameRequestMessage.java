package controller.online.tcp.messages.invitePlayer;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.GameType;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import controller.online.dataBase.OnlineData;
import controller.online.client.ClientState;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.messages.ClientMessageRecponceType;
import controller.online.tcp.messages.YesNoMessage;
import utils.TCPMessager;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientInviteGameRequestMessage extends YesNoMessage {

    @SkippedByJson
    protected MessageThread messageThread;
    @SkippedByJson
    private Gson gson;
    private int port;
    private String requester;
    private GameType gameType;

    public ClientInviteGameRequestMessage(String receiver ,String requester ,GameType gameType) {
        super(receiver);
        this.requester = requester;
        this.gameType = gameType;
    }

    private void initPort() {
        port = OnlineData.getAvailablePort();
    }

    private void initGson() {
        gson = new Gson();
    }


    @Override
    public void deliverMessage() {
        messageThread = new MessageThread();
        initGson();
        initPort();
        super.deliverMessage();
        messageThread.start();
        OnlineData.getTCPClient(receiver).getTcpMessager().sendMessage(port);
    }

    public class MessageThread extends Thread {

        @Override
        public void run() {
            initMessager();
            receiverMessager.sendMessage(
                    "player " + "'" + requester + "'" + " has invited you to a "
                           + gameType.toString() + " game" + ". do you accept?"
            );
            String JRecponce = receiverMessager.readMessage();
            ClientMessageRecponceType ownerRecponce = gson.fromJson(JRecponce , ClientMessageRecponceType.class);
            if (ownerRecponce.equals(ClientMessageRecponceType.yes)) {
                ClientState requesterState = OnlineData.getTCPClient(requester).getClientState();
                ClientState receiverState = OnlineData.getTCPClient(receiver).getClientState();
                if (!requesterState.equals(ClientState.online) || !receiverState.equals(ClientState.online)) {
                    return;
                }
                Game game = new Game(gameType);
                Player requesterPlayer = new Player(game ,requester);
                OnlineData.putClientPlayer(requester , requesterPlayer);
                OnlineData.putClientOnlineGame(requester ,game);
                OnlineData.getTCPClient(requester).getTcpMessager().sendMessage(ServerMessageType.getPorts);

                Player receiverPlayer = new Player(game ,receiver);
                OnlineData.putClientPlayer(receiver ,receiverPlayer);
                OnlineData.putClientOnlineGame(receiver ,game);
                OnlineData.getTCPClient(receiver).getTcpMessager().sendMessage(ServerMessageType.getPorts);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                game.start();
            }
            else {
            }
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
