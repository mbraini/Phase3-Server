package controller.game.player.udp;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.player.Player;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.EpsilonModel;
import utils.area.Area;

import java.util.ArrayList;

public class EffectViewSender extends Thread{

    private Game game;
    private Player player;
    private Gson gson;

    public EffectViewSender(Game game ,Player player) {
        this.game = game;
        this.player = player;
        gson = new Gson();
    }


    @Override
    public void run() {
        while (!isInterrupted()) {
            ArrayList<EffectView> effectViews = new ArrayList<>();
            ArrayList<EffectModel> effectModels;
            synchronized (game.getModelData().getModels()) {
                effectModels = (ArrayList<EffectModel>) game.getModelData().getEffectModels().clone();
            }

            for (EffectModel effectModel : effectModels) {
                EffectView effectView = new EffectView(
                        effectModel.getArea(),
                        effectModel.getTheta(),
                        effectModel.getR(),
                        effectModel.getG(),
                        effectModel.getB()
                );
                effectViews.add(effectView);
            }

            String JEffects = gson.toJson(effectViews);

        }
    }

    private class EffectView {
        private Area area;
        private int R;
        private int G;
        private int B;
        private double theta;

        public EffectView(Area area, double theta, int R, int G, int B) {
            this.area = area;
            this.theta = theta;
            this.R = R;
            this.G = G;
            this.B = B;
        }
    }

}
