package controller.game.player.udp;

import com.google.gson.Gson;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.player.Player;
import model.objectModel.frameModel.FrameModel;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class FrameViewSender extends Thread{

    private Game game;
    private Player player;
    private Gson gson;

    public FrameViewSender(Game game ,Player player) {
        this.game = game;
        this.player = player;
        gson = new Gson();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            ArrayList<FrameView> frameViews = new ArrayList<>();
            ArrayList<FrameModel> frameModels;

            synchronized (player.getGame().getModelData().getModels()) {
                frameModels = (ArrayList<FrameModel>) player.getGame().getModelData().getFrames().clone();
            }

            for (FrameModel frameModel : frameModels) {
                FrameView frameView = new FrameView(
                        frameModel.getPosition(),
                        new Dimension(
                                frameModel.getSize().width + SizeConstants.barD.width,
                                frameModel.getSize().height + SizeConstants.barD.height
                        )
                );
                frameViews.add(frameView);
            }

            String JFrames = gson.toJson(frameViews);

        }
    }

    public static class FrameView {

        private Vector position;
        private Dimension dimension;


        public FrameView(Vector position, Dimension dimension) {
            this.position = position;
            this.dimension = dimension;
        }
    }

}
