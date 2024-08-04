package controller.game.player;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.player.udp.*;

public class InfoSender {
    private Game game;
    private Gson gson;
    private String username;
    private AbilityViewSender abilitySender;
    private FrameViewSender frameViewSender;
    private ObjectViewSender objectViewSender;
    private EffectViewSender effectViewSender;
    private VariablesSender variablesSender;

    public InfoSender(Game game, String username) {
        this.game = game;
        this.username = username;
    }


}
