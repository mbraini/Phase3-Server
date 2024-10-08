package controller.game.player.udp;

import com.google.gson.Gson;
import controller.game.onlineGame.Game;
import controller.game.player.Player;
import controller.online.dataBase.OnlineData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class VariablesSender extends Thread{

    private Game game;
    private ArrayList<Player> players;
    private Gson gson;
    private DatagramSocket datagramSocket;
    private volatile boolean canSend = true;

    public VariablesSender(Game game ,ArrayList<Player> players) {
        this.game = game;
        this.players = (ArrayList<Player>) players.clone();
        try {
            datagramSocket = new DatagramSocket(OnlineData.getAvailablePort());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        gson = new Gson();
    }

    @Override
    public void run() {
        while (canSend) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (players) {
                for (Player player : players) {
                    FrameViewSender.FrameView frameView = null;
                    if (player.getPlayerData().getEpsilonFrame() != null) {
                        frameView = new FrameViewSender.FrameView(
                                player.getPlayerData().getEpsilonFrame().getPosition(),
                                player.getPlayerData().getEpsilonFrame().getSize(),
                                player.getPlayerData().getEpsilonFrame().getId()
                        );
                    }
                    VariablesView variablesView = new VariablesView(
                            (int) player.getGame().getGameState().getTime(),
                            (int) player.getPlayerData().getEpsilon().getHP(),
                            player.getPlayerData().getXp(),
                            player.getGame().getGameState().getWave(),
                            player.getPlayerData().getPauseTimeLeft(),
                            frameView
                    );
                    String JVariables = gson.toJson(variablesView);
                    byte[] packetData = JVariables.getBytes();
                    String ip = OnlineData.getTCPClient(player.getUsername()).getIp();
                    DatagramPacket datagramPacket = new DatagramPacket(
                            packetData,
                            packetData.length,
                            new InetSocketAddress(ip, player.getVariablesPort())
                    );
                    try {
                        datagramSocket.send(datagramPacket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }

    private class VariablesView {
        int time;
        int hp;
        int xp;
        int wave;
        int pauseTimeLeft;
        FrameViewSender.FrameView frameView;

        public VariablesView(int time, int hp, int xp, int wave ,int pauseTimeLeft , FrameViewSender.FrameView frameView) {
            this.time = time;
            this.hp = hp;
            this.xp = xp;
            this.wave = wave;
            this.pauseTimeLeft = pauseTimeLeft;
            this.frameView = frameView;
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        synchronized (this.players) {
            this.players = players;
        }
    }

    public void setCanSend(boolean canSend) {
        this.canSend = canSend;
    }
}
