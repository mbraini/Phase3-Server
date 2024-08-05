package model.viewRequests;

import controller.game.enums.InGameAbilityType;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.inGameAbilities.InGameAbility;
import model.inGameAbilities.InGameAbilityHandler;

public class InGameAbilityRequests {


    public static void abilityRequest(InGameAbilityType type , Player player){

        if (canUse(type ,player)) {
            InGameAbilityHandler.activateInGameAbility(type ,player);
            player.getPlayerData().setXp(
                    player.getPlayerData().getXp() - InGameAbilityHandler.getInGameAbility(type ,player).getXpCost()
            );
        }

    }

    private static boolean canUse(InGameAbilityType type ,Player player) {
        InGameAbility inGameAbility = InGameAbilityHandler.getInGameAbility(type ,player);
        if (player.getPlayerData().getXp() >= inGameAbility.getXpCost()
                && inGameAbility.isAvailable() && !inGameAbility.isActive())
            return true;
        return false;
    }



}
