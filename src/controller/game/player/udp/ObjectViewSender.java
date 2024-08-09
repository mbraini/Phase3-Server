package controller.game.player.udp;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.game.interfaces.SizeChanger;
import controller.game.player.Player;
import controller.online.dataBase.OnlineData;
import model.objectModel.ObjectModel;
import utils.Vector;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class ObjectViewSender extends Thread{

    private Game game;
    private ArrayList<Player> players;
    private Gson gson;
    private DatagramSocket datagramSocket;
    private volatile boolean canSend = true;

    public ObjectViewSender(Game game ,ArrayList<Player> players) {
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
                    ArrayList<View> views = new ArrayList<>();
                    ArrayList<ObjectModel> models;

                    synchronized (player.getGame().getModelData().getModels()) {
                        models = (ArrayList<ObjectModel>) player.getGame().getModelData().getModels().clone();
                    }

                    for (ObjectModel model : models) {
                        View view = new View(
                                model.getPosition(),
                                model.getTheta(),
                                model.isHovering(),
                                model.getType(),
                                model.getId()
                        );
                        if (model instanceof SizeChanger) {
                            view = new View(
                                    model.getPosition(),
                                    model.getTheta(),
                                    model.isHovering(),
                                    ((SizeChanger) model).getSize(),
                                    model.getType(),
                                    model.getId()
                            );
                        }
                        views.add(view);
                    }

                    String JViews = gson.toJson(views);
                    byte[] packetData = JViews.getBytes();
                    String ip = OnlineData.getTCPClient(player.getUsername()).getIp();
                    DatagramPacket datagramPacket = new DatagramPacket(
                            packetData,
                            packetData.length,
                            new InetSocketAddress(ip, player.getObjectPort())
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


    private class View {

        private Vector position;
        private double theta;
        private boolean hovering;
        private Dimension size;
        private ModelType modelType;
        private String id;


        public View(Vector position, double theta, boolean hovering ,ModelType modelType ,String id) {
            this.position = position;
            this.theta = theta;
            this.modelType = modelType;
            this.hovering = hovering;
            this.id = id;
        }

        public View(Vector position, double theta, boolean hovering ,Dimension size ,ModelType modelType ,String id) {
            this.position = position;
            this.theta = theta;
            this.hovering = hovering;
            this.modelType = modelType;
            this.size = size;
            this.id = id;
        }


    }

    private class EpsilonView {
        private Vector position;
        private double theta;
        private boolean hovering;
        private Dimension size;
        private ModelType modelType;
        private String id;
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
