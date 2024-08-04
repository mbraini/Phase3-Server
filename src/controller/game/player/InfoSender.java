package controller.game.player;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.player.udp.*;
import controller.online.OnlineData;

import java.util.ArrayList;

public class InfoSender {
    private Game game;
    private Gson gson;
    private ArrayList<Player> players;
    private AbilityViewSender abilitySender;
    private FrameViewSender frameViewSender;
    private ObjectViewSender objectViewSender;
    private EffectViewSender effectViewSender;
    private VariablesSender variablesSender;


    public InfoSender(Game game, ArrayList<Player> players) {
        this.game = game;
        this.players = players;
    }


    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        initSenders();
        abilitySender.start();
        frameViewSender.start();
        objectViewSender.start();
        effectViewSender.start();
        variablesSender.start();
    }

    private void initSenders() {
        abilitySender = new AbilityViewSender(game ,players);
        frameViewSender = new FrameViewSender(game ,players);
        objectViewSender = new ObjectViewSender(game ,players);
        effectViewSender = new EffectViewSender(game ,players);
        variablesSender = new VariablesSender(game ,players);
    }

}
