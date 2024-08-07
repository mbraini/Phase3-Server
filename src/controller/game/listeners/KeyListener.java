package controller.game.listeners;

import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;

import java.util.ArrayList;

public class KeyListener {

    private Player player;
    public static char SHOP_KEY = 'p';
    public static char ARES_KEY = 'q';
    public static char ASTRAPE_KEY = 'a';
    public static char CERBERUS_KEY = 'z';
    public static char ACESO_KEY = 'w';
    public static char MELAMPUS_KEY = 's';
    public static char CHIRON_KEY = 'x';
    public static char PROTEUS_KEY = 'e';
    public static char EMPUSA_KEY = 'd';
    public static char DOLUS_KEY = 'c';
    public static char ATHENA_KEY = 'r';
    public ArrayList<Character> typedKeys = new ArrayList<>();

    public KeyListener(Player player ,ArrayList<Character> typedKeys){
        this.player = player;
        this.typedKeys = typedKeys;
    }

    public void checkKey() {
        if (player.getGame().getGameState().isInAnimation()) {
            return;
        }
        if (player.isDead())
            return;
        for (Character character : typedKeys) {
            if (character == SHOP_KEY) {
                if (player.getGame().getGameState().isPause()) {
                    if (player.getGame().getPauseWatcher().getIsPausing().getUsername().equals(player.getUsername()))
                        player.getGame().getGameController().unpause();
                }
                else {
                    if (player.getPlayerData().getPauseTimeLeft() > 0) {
                        player.getGame().getGameController().pause(player);
                    }
                }
            }
            if (character == ARES_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.ares, player);
            }
            if (character == ASTRAPE_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.astrape, player);
            }
            if (character == CERBERUS_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.cerberus, player);
            }
            if (character == ACESO_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.aceso, player);
            }
            if (character == MELAMPUS_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.melampus, player);
            }
            if (character == CHIRON_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.chiron, player);
            }
            if (character == PROTEUS_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.proteus, player);
            }
            if (character == EMPUSA_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.empusa, player);
            }
            if (character == DOLUS_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.dolus, player);
            }
            if (character == ATHENA_KEY) {
                player.getViewRequestController().skillTreeAbilityRequest(SkillTreeAbilityType.athena, player);
            }
        }
    }
}
