package controller.game;

import controller.game.enums.InGameAbilityType;
import controller.game.enums.SkillTreeAbilityType;
import controller.game.listeners.EpsilonCirculation;
import controller.game.listeners.epsilonMovement.EpsilonMovement;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.ModelData;
import model.inGameAbilities.InGameAbility;
import model.skillTreeAbilities.SkillTreeAbility;
import model.viewRequests.InGameAbilityRequests;
import model.viewRequests.ShootRequest;
import model.viewRequests.SkillTreeAbilityRequests;
import utils.Vector;

import java.util.ArrayList;

public class ViewRequestController {

    private Player player;

    public ViewRequestController(Player player) {
        this.player = player;
    }

    public void shootRequest(Vector clickedPoint){
        new ShootRequest(player ,clickedPoint).shoot();
    }
    public void rotateRequest(Vector mousePosition) {
        new EpsilonCirculation(player ,mousePosition).rotate();
    }

    public void inGameAbilityRequest(InGameAbilityType type){
        InGameAbilityRequests.abilityRequest(type);
    }

    public void skillTreeAbilityRequest(SkillTreeAbilityType type) {
        SkillTreeAbilityRequests.abilityRequest(type);
    }


    public void buySkillTreeRequest(SkillTreeAbilityType type) {
        SkillTreeAbilityRequests.buyRequest(type);
    }

    public void movementRequest(ArrayList<Integer> pressedKeys, ArrayList<Integer> releasedKeys) {
        new EpsilonMovement(player ,pressedKeys ,releasedKeys).applyMovement();
    }
}
