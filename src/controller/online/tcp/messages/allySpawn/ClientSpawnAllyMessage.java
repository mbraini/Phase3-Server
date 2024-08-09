package controller.online.tcp.messages.allySpawn;

import com.google.gson.Gson;
import controller.game.onlineGame.Game;
import controller.game.onlineGame.GameType;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.squad.SquadBattle;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.messages.ClientMessageRecponceType;
import controller.online.tcp.messages.YesNoMessage;
import utils.TCPMessager;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientSpawnAllyMessage extends YesNoMessage {

    private String requester;
    private int port;
    private GameType gameType;
    @SkippedByJson
    private MessageThread messageThread;
    @SkippedByJson
    private Gson gson;

    public ClientSpawnAllyMessage(String receiver , String requester , GameType gameType) {
        super(receiver);
        this.requester = requester;
        this.gameType = gameType;
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

    private void initPort() {
        port = OnlineData.getAvailablePort();
    }

    private void initGson() {
        gson = new Gson();
    }

    public class MessageThread extends Thread {

        @Override
        public void run() {
            initMessager();
            receiverMessager.sendMessage(
                    "player " + "'" + requester + "'" + " has requested for your help in a"
                            + gameType + " game. do you accept?"
            );
            String JRecponce = receiverMessager.readMessage();
            ClientMessageRecponceType ownerRecponce = gson.fromJson(JRecponce , ClientMessageRecponceType.class);
            SquadBattle squadBattle = OnlineData.getClientSquad(requester).getSquadBattle();
            if (ownerRecponce.equals(ClientMessageRecponceType.yes) && squadBattle.hasSpawn(requester)) {
                squadBattle.castSpawn(requester);

                Game game = OnlineData.getOnlineGame(requester);
                Player receiverPlayer = new Player(game ,receiver);
                Player requesterPlayer = OnlineData.getPlayer(requester);

                receiverPlayer.setTeammate(requesterPlayer);
                requesterPlayer.setTeammate(receiverPlayer);

                receiverPlayer.start();
                OnlineData.getTCPClient(receiverPlayer.getUsername()).setClientState(ClientState.busy);

                OnlineData.putClientPlayer(receiver , receiverPlayer);
                OnlineData.putClientOnlineGame(receiver ,game);
                OnlineData.getTCPClient(receiver).getTcpMessager().sendMessage(ServerMessageType.getPorts);
            }
            else {
                TCPClient tcpClient = OnlineData.getTCPClient(receiver);
                tcpClient.addMessage(
                        new ClientSpawnAllyRecponce(tcpClient.getUsername())
                );
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
