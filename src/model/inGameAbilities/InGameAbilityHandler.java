package model.inGameAbilities;

import controller.game.enums.InGameAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.inGameAbilities.Dismay.Dismay;
import model.objectModel.fighters.EpsilonModel;

import java.util.ArrayList;

public class InGameAbilityHandler {

    public synchronized static void initInGameAbilities(Player player) {
        ArrayList<InGameAbility> inGameAbilities = player.getPlayerData().getInGameAbilities();

        inGameAbilities.add(new Banish(player));
        inGameAbilities.add(new Empower(player));
        inGameAbilities.add(new Heal(player));
        inGameAbilities.add(new Dismay(player));
        inGameAbilities.add(new Slumber(player));
        inGameAbilities.add(new Slaughter(player));

    }

    public synchronized static void activateInGameAbility(InGameAbilityType type ,Player player) {
        InGameAbility inGameAbility = getInGameAbility(type ,player);
        inGameAbility.performAbility();
    }

    public synchronized static InGameAbility getInGameAbility(InGameAbilityType type ,Player player) {
        ArrayList<InGameAbility> inGameAbilities = player.getPlayerData().getInGameAbilities();

        for (InGameAbility inGameAbility : inGameAbilities){
            if (inGameAbility.getType() == type)
                return inGameAbility;
        }
        return null;
    }

    public synchronized static void disableAll(Player player){
        ArrayList<InGameAbility> inGameAbilities = player.getPlayerData().getInGameAbilities();

        for (InGameAbility inGameAbility : inGameAbilities){
            if (inGameAbility.getType() != InGameAbilityType.slumber)
                inGameAbility.setAvailable(false);
        }
    }


    public static void permitAll(Player player) {
        ArrayList<InGameAbility> inGameAbilities = player.getPlayerData().getInGameAbilities();

        for (InGameAbility inGameAbility : inGameAbilities)
            inGameAbility.setAvailable(true);
    }

}
