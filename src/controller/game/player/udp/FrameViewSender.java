package controller.game.player.udp;

import com.google.gson.Gson;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.player.Player;
import controller.online.OnlineData;
import model.objectModel.frameModel.FrameModel;
import utils.Vector;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class FrameViewSender extends Thread{

    private Game game;
    private ArrayList<Player> players;
    private DatagramSocket datagramSocket;
    private Gson gson;

    public FrameViewSender(Game game ,ArrayList<Player> players) {
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
            for (Player player : players) {
                ArrayList<FrameView> frameViews = new ArrayList<>();
                ArrayList<FrameModel> frameModels;

                synchronized (player.getGame().getModelData().getFrames()) {
                    frameModels = (ArrayList<FrameModel>) player.getGame().getModelData().getFrames().clone();
                }

                for (FrameModel frameModel : frameModels) {
                    FrameView frameView = new FrameView(
                            frameModel.getPosition(),
                            new Dimension(
                                    frameModel.getSize().width + SizeConstants.barD.width,
                                    frameModel.getSize().height + SizeConstants.barD.height
                            ),
                            frameModel.getId()
                    );
                    frameViews.add(frameView);
                }
                String JFrames = gson.toJson(frameViews);
                byte[] packetData = JFrames.getBytes();
                String ip = OnlineData.getTCPClient(player.getUsername()).getIp();
                DatagramPacket datagramPacket = new DatagramPacket(
                        packetData,
                        packetData.length,
                        new InetSocketAddress(ip ,player.getFramePort())
                );
                try {
                    datagramSocket.send(datagramPacket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class FrameView {

        private Vector position;
        private Dimension dimension;
        private String id;


        public FrameView(Vector position, Dimension dimension ,String id) {
            this.position = position;
            this.dimension = dimension;
            this.id = id;
        }
    }

}
