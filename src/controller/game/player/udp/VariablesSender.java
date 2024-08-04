package controller.game.player.udp;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.player.Player;

import java.util.ArrayList;

public class VariablesSender extends Thread{

    private Game game;
    private Player player;
    private Gson gson;

    public VariablesSender(Game game ,Player player) {
        this.game = game;
        this.player = player;
        gson = new Gson();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            FrameViewSender.FrameView frameView = new FrameViewSender.FrameView(
                    player.getPlayerData().getEpsilonFrame().getPosition(),
                    player.getPlayerData().getEpsilonFrame().getSize()
            );
            VariablesView variablesView = new VariablesView(
                    (int)player.getGame().getGameState().getTime(),
                    (int)player.getPlayerData().getEpsilon().getHP(),
                    player.getPlayerData().getXp(),
                    player.getGame().getGameState().getWave(),
                    frameView
            );
            String JVariables = gson.toJson(variablesView);
        }

    }

    private class VariablesView {
        int time;
        int hp;
        int xp;
        int wave;
        FrameViewSender.FrameView frameView;

        public VariablesView(int time, int hp, int xp, int wave , FrameViewSender.FrameView frameView) {
            this.time = time;
            this.hp = hp;
            this.xp = xp;
            this.wave = wave;
            this.frameView = frameView;
        }
    }

}
