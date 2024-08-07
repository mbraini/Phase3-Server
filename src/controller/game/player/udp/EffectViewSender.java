package controller.game.player.udp;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.enums.EffectType;
import controller.game.player.Player;
import controller.online.OnlineData;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.EpsilonModel;
import utils.area.Area;
import utils.area.Circle;
import utils.area.Polygon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class EffectViewSender extends Thread{

    private Game game;
    private ArrayList<Player> players;
    private Gson gson;
    private DatagramSocket datagramSocket;

    public EffectViewSender(Game game ,ArrayList<Player> players) {
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
        while (!isInterrupted()) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (players) {
                for (Player player : players) {
                    ArrayList<EffectView> effectViews = new ArrayList<>();
                    ArrayList<EffectModel> effectModels;
                    synchronized (game.getModelData().getEffectModels()) {
                        effectModels = (ArrayList<EffectModel>) game.getModelData().getEffectModels().clone();
                    }
                    for (EffectModel effectModel : effectModels) {
                        EffectView effectView;
                        if (effectModel == null)
                            continue;
                        if (effectModel.getArea() instanceof Circle) {
                            effectView = new EffectView(
                                    (Circle) effectModel.getArea(),
                                    effectModel.getTheta(),
                                    effectModel.getR(),
                                    effectModel.getG(),
                                    effectModel.getB(),
                                    effectModel.getEffectType(),
                                    effectModel.getId()
                            );
                        } else {
                            effectView = new EffectView(
                                    (Polygon) effectModel.getArea(),
                                    effectModel.getTheta(),
                                    effectModel.getR(),
                                    effectModel.getG(),
                                    effectModel.getB(),
                                    effectModel.getEffectType(),
                                    effectModel.getId()
                            );
                        }
                        effectViews.add(effectView);
                    }
                    String JEffects = gson.toJson(effectViews);
                    byte[] packetData = JEffects.getBytes();
                    String ip = OnlineData.getTCPClient(player.getUsername()).getIp();
                    DatagramPacket datagramPacket = new DatagramPacket(
                            packetData,
                            packetData.length,
                            new InetSocketAddress(ip, player.getEffectPort())
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

    private class EffectView {
        private EffectType effectType;
        private Circle circle;
        private Polygon polygon;
        private int R;
        private int G;
        private int B;
        private double theta;
        private String id;

        public EffectView(Circle circle ,double theta, int R, int G, int B , EffectType effectType ,String id) {
            this.circle = circle;
            this.theta = theta;
            this.R = R;
            this.G = G;
            this.B = B;
            this.effectType = effectType;
            this.id = id;
        }

        public EffectView(Polygon polygon ,double theta, int R, int G, int B , EffectType effectType ,String id) {
            this.polygon = polygon;
            this.theta = theta;
            this.R = R;
            this.G = G;
            this.B = B;
            this.effectType = effectType;
            this.id = id;
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
}
