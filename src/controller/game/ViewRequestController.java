package controller.game;

import controller.game.enums.InGameAbilityType;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.listeners.EpsilonCirculation;
import controller.game.listeners.KeyListener;
import controller.game.listeners.epsilonMovement.EpsilonMovement;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.ModelData;
import model.inGameAbilities.InGameAbility;
import model.inGameAbilities.InGameAbilityHandler;
import model.skillTreeAbilities.SkillTreeAbility;
import model.skillTreeAbilities.SkillTreeAbilityHandler;
import model.viewRequests.InGameAbilityRequests;
import model.viewRequests.ShootRequest;
import model.viewRequests.SkillTreeAbilityRequests;
import utils.Vector;

import java.util.ArrayList;

public class ViewRequestController {

    private Player player;

    public ViewRequestController(Player player) {
        this.player = player;
        SkillTreeAbilityHandler.initAbilities(player);
        InGameAbilityHandler.initInGameAbilities(player);
    }

    public void shootRequest(Vector clickedPoint){
        new ShootRequest(player ,clickedPoint).shoot();
    }
    public void rotateRequest(Vector mousePosition) {
        new EpsilonCirculation(player ,mousePosition).rotate();
    }

    public void inGameAbilityRequest(InGameAbilityType type ,Player player){
        InGameAbilityRequests.abilityRequest(type ,player);
    }

    public void skillTreeAbilityRequest(SkillTreeAbilityType type ,Player player) {
        SkillTreeAbilityRequests.abilityRequest(type ,player);
    }

    public void movementRequest(ArrayList<Integer> pressedKeys, ArrayList<Integer> releasedKeys) {
        new EpsilonMovement(player ,pressedKeys ,releasedKeys).applyMovement();
    }

    public void keyTypedHandler(ArrayList<Character> typedKeys) {
        new KeyListener(player ,typedKeys).checkKey();
    }
}
