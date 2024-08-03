package model.viewRequests;

import controller.game.enums.InGameAbilityType;
import controller.game.manager.GameState;
import model.inGameAbilities.InGameAbility;
import model.inGameAbilities.InGameAbilityHandler;

public class InGameAbilityRequests {


    public static void abilityRequest(InGameAbilityType type){

//        if (canUse(type)) {
//            InGameAbilityHandler.activateInGameAbility(type);
//            GameState.setXp(GameState.getXp() - InGameAbilityHandler.getInGameAbility(type).getXpCost());
//        }

    }

    private static boolean canUse(InGameAbilityType type) {
        InGameAbility inGameAbility = InGameAbilityHandler.getInGameAbility(type);
//        if (GameState.getXp() >= inGameAbility.getXpCost()  && inGameAbility.isAvailable() && !inGameAbility.isActive())
//            return true;
        return false;
    }



}
