package model.inGameAbilities;

import controller.game.enums.InGameAbilityType;
import model.ModelData;
import model.inGameAbilities.Dismay.Dismay;
import model.objectModel.fighters.EpsilonModel;

import java.util.ArrayList;

public class InGameAbilityHandler {

    public synchronized static void initInGameAbilities() {
//        ArrayList<InGameAbility> inGameAbilities = ModelData.getInGameAbilities();
//
//        inGameAbilities.add(new Banish(
//                (EpsilonModel) ModelData.getModels().getFirst()
//        ));
//        inGameAbilities.add(new Empower());
//        inGameAbilities.add(new Heal((EpsilonModel) ModelData.getModels().getFirst()));
//        inGameAbilities.add(new Dismay((EpsilonModel) ModelData.getModels().getFirst()));
//        inGameAbilities.add(new Slumber());
//        inGameAbilities.add(new Slaughter());
    }

    public synchronized static void activateInGameAbility(InGameAbilityType type) {
//        InGameAbility inGameAbility = getInGameAbility(type);
//        inGameAbility.performAbility();
    }

    public synchronized static InGameAbility getInGameAbility(InGameAbilityType type) {
//        ArrayList<InGameAbility> inGameAbilities = ModelData.getInGameAbilities();
//
//        for (InGameAbility inGameAbility : inGameAbilities){
//            if (inGameAbility.getType() == type)
//                return inGameAbility;
//        }
        return null;
    }

    public synchronized static void disableAll(){
//        ArrayList<InGameAbility> inGameAbilities = ModelData.getInGameAbilities();
//
//        for (InGameAbility inGameAbility : inGameAbilities){
//            if (inGameAbility.getType() != InGameAbilityType.slumber)
//                inGameAbility.setAvailable(false);
//        }
    }


    public static void permitAll() {
//        ArrayList<InGameAbility> inGameAbilities = ModelData.getInGameAbilities();
//
//        for (InGameAbility inGameAbility : inGameAbilities)
//            inGameAbility.setAvailable(true);
    }

    public static void addAbility(InGameAbility ability) {
//        ArrayList<InGameAbility> inGameAbilities = ModelData.getInGameAbilities();
//        inGameAbilities.add(ability);
    }
}
