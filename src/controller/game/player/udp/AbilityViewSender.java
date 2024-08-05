package controller.game.player.udp;

import com.google.gson.Gson;
import constants.TimeConstants;
import controller.game.Game;
import controller.game.enums.InGameAbilityType;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.InfoSender;
import controller.game.player.Player;
import model.inGameAbilities.InGameAbility;
import model.inGameAbilities.Slaughter;
import model.inGameAbilities.Slumber;
import model.skillTreeAbilities.SkillTreeAbility;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class AbilityViewSender extends Thread{

    private Game game;
    private ArrayList<Player> players;
    private Gson gson;

    public AbilityViewSender(Game game , ArrayList<Player> players) {
        this.game = game;
        this.players = (ArrayList<Player>) players.clone();
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
                    if (inGameAbility.getType() == InGameAbilityType.slaughter) {
                        Slaughter slaughter = (Slaughter) (inGameAbility);
                        abilityViews.add(new AbilityView(
                                TimeConstants.SLAUGHTER_COOLDOWN,
                                slaughter.getTimePassed(),
                                slaughter.isAvailable(),
                                slaughter.getType()
                        ));
                    }
                    if (inGameAbility.getType() == InGameAbilityType.slumber) {
                        Slumber slaughter = (Slumber) (inGameAbility);
                        abilityViews.add(new AbilityView(
                                TimeConstants.SLAUGHTER_COOLDOWN,
                                slaughter.getTimePassed(),
                                slaughter.isAvailable(),
                                slaughter.getType()
                        ));
                    }
                }

//                String JAbilities = gson.toJson(abilityViews); ///todo
            }
        }
    }

    private class AbilityView {
        private int coolDownTime;
        private int timePassed;
        private boolean isAvailable;
        private int theta;
        private Image image;
        private Vector position;
        private Color color;
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

}
