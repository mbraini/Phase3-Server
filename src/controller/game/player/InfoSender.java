package controller.game.player;

import com.google.gson.Gson;
import controller.game.Game;
import controller.game.player.udp.*;

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
        this.players = (ArrayList<Player>) players.clone();
        initSenders();
    }


    public void addPlayer(Player player) {
        players.add(player);
        abilitySender.setPlayers((ArrayList<Player>) players.clone());
        frameViewSender.setPlayers((ArrayList<Player>) players.clone());
        objectViewSender.setPlayers((ArrayList<Player>) players.clone());
        effectViewSender.setPlayers((ArrayList<Player>) players.clone());
        variablesSender.setPlayers((ArrayList<Player>) players.clone());
    }

    public void start() {
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

    public void end() {
        abilitySender.setCanSend(false);
        frameViewSender.setCanSend(false);
        objectViewSender.setCanSend(false);
        effectViewSender.setCanSend(false);
        variablesSender.setCanSend(false);
    }
}
