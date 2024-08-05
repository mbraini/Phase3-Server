package model.viewRequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.CostConstants;
import controller.game.configs.Configs;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.manager.GameState;
import controller.game.player.Player;
import controller.online.OnlineData;
import controller.online.client.GameClient;
import controller.online.client.gameClientUpdate.SkillTreeJsonHelper;
import model.skillTreeAbilities.SkillTreeAbility;
import model.skillTreeAbilities.SkillTreeAbilityHandler;
import utils.Helper;

public class SkillTreeAbilityRequests {

    public static void abilityRequest(SkillTreeAbilityType type , Player player) {
        if (canUse(type ,player)) {
            player.getPlayerData().setXp(player.getPlayerData().getXp() - SkillTreeAbilityHandler.getAbility(type ,player).getInGameXpCost());
            SkillTreeAbilityHandler.activateSkillTreeAbility(type ,player);
        }
    }

    private static boolean canUse(SkillTreeAbilityType type ,Player player) {
        SkillTreeAbility ability = SkillTreeAbilityHandler.getAbility(type ,player);
        if (ability.isBought() && ability.CanCast() && player.getPlayerData().getXp() >= ability.getInGameXpCost()){
            return true;
        }
        return false;
    }
    private static int canBuy(SkillTreeAbilityType type ,Player player) {
        GameClient gameClient = OnlineData.getGameClient(player.getUsername());
        switch (type) {
            case ares :
                if (gameClient.isAceso())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.ARES_UNLOCK_COST)
                    return CostConstants.ARES_UNLOCK_COST;
                break;
            case astrape:
                if (gameClient.isAstrape())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.ASTRAPE_UNLOCK_COST && gameClient.isAres())
                    return CostConstants.ASTRAPE_UNLOCK_COST;
                break;
            case cerberus:
                if (gameClient.isCerberus())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.CERBERUS_UNLOCK_COST) {
                    if (gameClient.isAres() && gameClient.isAstrape())
                        return CostConstants.CERBERUS_UNLOCK_COST;
                }
                break;
            case aceso:
                if (gameClient.isAceso())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.ACESO_UNLOCK_COST)
                    return CostConstants.ACESO_UNLOCK_COST;
                break;
            case melampus:
                if (gameClient.isMelampus())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.MELAMPUS_UNLOCK_COST && gameClient.isAceso())
                    return CostConstants.MELAMPUS_UNLOCK_COST;
                break;
            case chiron:
                if (gameClient.isChiron())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.CHIRON_UNLOCK_COST) {
                    if (gameClient.isAceso() && gameClient.isMelampus())
                        return CostConstants.CHIRON_UNLOCK_COST;
                }
                break;
            case athena:
                if (gameClient.isAthena())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.ATHENA_UNLOCK_COST) {
                    if (gameClient.isAceso() && gameClient.isMelampus())
                        return CostConstants.ATHENA_UNLOCK_COST;
                }
                break;
            case proteus:
                if (gameClient.isProteus())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.PROTEUS_UNLOCK_COST)
                    return CostConstants.PROTEUS_UNLOCK_COST;
                break;
            case empusa:
                if (gameClient.isEmpusa())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.EMPUSA_UNLOCK_COST && gameClient.isProteus())
                    return CostConstants.EMPUSA_UNLOCK_COST;
                break;
            case dolus:
                if (gameClient.isDolus())
                    return -1;
                if (player.getPlayerData().getXp() >= CostConstants.DOLUS_UNLOCK_COST) {
                    if (gameClient.isProteus() && gameClient.isEmpusa())
                        return CostConstants.DOLUS_UNLOCK_COST;
                }
                break;
        }
        return -1;
    }
}
