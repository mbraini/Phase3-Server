package controller.game.player.udp;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.interfaces.SizeChanger;
import controller.game.player.Player;
import model.objectModel.ObjectModel;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class ObjectViewSender extends Thread{

    private Game game;
    private Player player;
    private Gson gson;

    public ObjectViewSender(Game game ,Player player) {
        this.game = game;
        this.player = player;
        gson = new Gson();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<ObjectModel> models;

            synchronized (player.getGame().getModelData().getModels()) {
                models = (ArrayList<ObjectModel>) player.getGame().getModelData().getModels().clone();
            }

            for (ObjectModel model : models) {
                View view = new View(
                        model.getPosition(),
                        model.getTheta(),
                        model.isHovering()
                );
                if (model instanceof SizeChanger) {
                    view = new View(
                            model.getPosition(),
                            model.getTheta(),
                            model.isHovering(),
                            ((SizeChanger) model).getSize()
                    );
                }
                views.add(view);
            }

            String JViews = gson.toJson(views);
        }
    }


    private class View {

        private Vector position;
        private double theta;
        private boolean hovering;
        private Dimension size;


        public View(Vector position, double theta, boolean hovering) {
            this.position = position;
            this.theta = theta;
            this.hovering = hovering;
        }

        public View(Vector position, double theta, boolean hovering ,Dimension size) {
            this.position = position;
            this.theta = theta;
            this.hovering = hovering;
            this.size = size;
        }


    }

}
