package controller.game.player.udp;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.TimeConstants;
import controller.game.Game;
import controller.game.enums.InGameAbilityType;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;
import controller.online.OnlineData;
import controller.online.annotations.SkippedByJson;
import model.inGameAbilities.InGameAbility;
import model.inGameAbilities.Slaughter;
import model.inGameAbilities.Slumber;
import model.skillTreeAbilities.SkillTreeAbility;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class AbilityViewSender extends Thread{

    private Game game;
    private ArrayList<Player> players;
    private Gson gson;
    private DatagramSocket datagramSocket;
    private volatile boolean canSend = true;

    public AbilityViewSender(Game game , ArrayList<Player> players) {
        this.game = game;
        this.players = (ArrayList<Player>) players.clone();
        try {
            datagramSocket = new DatagramSocket(OnlineData.getAvailablePort());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        initGson();
    }

    private void initGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(SkippedByJson.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                if (aClass.getAnnotation(SkippedByJson.class) == null)
                    return false;
                return true;
            }
        });
        gson = builder.create();
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
                    ArrayList<InGameAbility> inGameAbilities;
                    ArrayList<SkillTreeAbility> skillTreeAbilities;
                    synchronized (player.getPlayerData().getInGameAbilities()) {
                        inGameAbilities = (ArrayList<InGameAbility>) player.getPlayerData().getInGameAbilities().clone();
                        skillTreeAbilities = (ArrayList<SkillTreeAbility>) player.getPlayerData().getSkillTreeAbilities().clone();
                    }
                    ArrayList<AbilityView> abilityViews = new ArrayList<>();
                    for (SkillTreeAbility skillTreeAbility : skillTreeAbilities) {
                        abilityViews.add(new AbilityView(
                                skillTreeAbility.getInGameCoolDownTime(),
                                skillTreeAbility.getCoolDownTimePassed(),
                                skillTreeAbility.isBought() && skillTreeAbility.CanCast(),
                                skillTreeAbility.getType()
                        ));
                    }
                    for (InGameAbility inGameAbility : inGameAbilities) {
                        if (inGameAbility.getType().equals(InGameAbilityType.slaughter)) {
                            Slaughter slaughter = (Slaughter) (inGameAbility);
                            abilityViews.add(new AbilityView(
                                    TimeConstants.SLAUGHTER_COOLDOWN,
                                    slaughter.getTimePassed(),
                                    slaughter.isAvailable(),
                                    slaughter.getType()
                            ));
                        }
                        if (inGameAbility.getType().equals(InGameAbilityType.slumber)) {
                            Slumber slumber = (Slumber) (inGameAbility);
                            abilityViews.add(new AbilityView(
                                    TimeConstants.SLUMBER_DURATION,
                                    slumber.getTimePassed(),
                                    slumber.isAvailable(),
                                    slumber.getType()
                            ));
                        }
                    }

                    String JAbilities = gson.toJson(abilityViews);

                    byte[] packetData = JAbilities.getBytes();
                    String ip = OnlineData.getTCPClient(player.getUsername()).getIp();
                    DatagramPacket datagramPacket = new DatagramPacket(
                            packetData,
                            packetData.length,
                            new InetSocketAddress(ip, player.getAbilityPort())
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

    private class AbilityView {
        private int coolDownTime;
        private int timePassed;
        private boolean isAvailable;
        private SkillTreeAbilityType skillTreeAbilityType;
        private InGameAbilityType inGameAbilityType;

        public AbilityView(int coolDownTime , int timePassed , boolean isAvailable , SkillTreeAbilityType skillTreeAbilityType) {
            this.coolDownTime = coolDownTime;
            this.timePassed = timePassed;
            this.isAvailable = isAvailable;
            this.skillTreeAbilityType = skillTreeAbilityType;
        }

        public AbilityView(int coolDownTime , int timePassed , boolean isAvailable , InGameAbilityType inGameAbilityType) {
            this.coolDownTime = coolDownTime;
            this.timePassed = timePassed;
            this.isAvailable = isAvailable;
            this.inGameAbilityType = inGameAbilityType;
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

    public void setCanSend(boolean canSend) {
        this.canSend = canSend;
    }
}
