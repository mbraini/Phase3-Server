package model.skillTreeAbilities;

import controller.game.enums.SkillTreeAbilityType;
import controller.game.player.Player;
import controller.online.dataBase.OnlineData;
import controller.online.client.GameClient;
import model.skillTreeAbilities.Cerberus.Cerberus;

import java.util.ArrayList;

public class SkillTreeAbilityHandler {

    public synchronized static void initAbilities(Player player){
        ArrayList<SkillTreeAbility> abilities = new ArrayList<>();
        GameClient gameClient = OnlineData.getGameClient(player.getUsername());
        abilities.add(new Ares(player ,gameClient.isAres()));
        abilities.add(new Astrape(player ,gameClient.isAstrape()));
        abilities.add(new Cerberus(player ,gameClient.isCerberus()));
        abilities.add(new Aceso(player ,gameClient.isAceso()));
        abilities.add(new Melapmus(player ,gameClient.isMelampus()));
        abilities.add(new Chiron(player ,gameClient.isChiron()));
        abilities.add(new Proteus(player ,gameClient.isProteus()));
        abilities.add(new Empusa(player ,gameClient.isEmpusa()));
        abilities.add(new Dolus(player ,gameClient.isDolus()));
        abilities.add(new Athena(player ,gameClient.isAthena()));
        player.getPlayerData().setSkillTreeAbilities(abilities);
    }

    public static SkillTreeAbility getAbility(SkillTreeAbilityType type , Player player) {
        ArrayList<SkillTreeAbility> abilities = player.getPlayerData().getSkillTreeAbilities();
        for (SkillTreeAbility ability : abilities){
            if (ability.getType().equals(type))
                return ability;
        }
        return null;
    }

    public static void activateSkillTreeAbility(SkillTreeAbilityType type ,Player player) {
        SkillTreeAbility skillTreeAbility = getAbility(type ,player);
        skillTreeAbility.cast();
    }

}
