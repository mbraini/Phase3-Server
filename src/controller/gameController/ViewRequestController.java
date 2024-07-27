package controller.gameController;

import constants.SizeConstants;
import constants.TimeConstants;
import controller.gameController.enums.InGameAbilityType;
import controller.gameController.enums.SkillTreeAbilityType;
import controller.gameController.interfaces.SizeChanger;
import controller.gameController.manager.GameState;
import model.ModelData;
import model.inGameAbilities.InGameAbility;
import model.inGameAbilities.Slaughter;
import model.inGameAbilities.Slumber;
import controller.gameController.interfaces.ImageChanger;
import model.objectModel.ObjectModel;
import model.objectModel.effects.EffectModel;
import model.objectModel.frameModel.FrameModel;
import model.skillTreeAbilities.SkillTreeAbility;
import model.viewRequests.InGameAbilityRequests;
import model.viewRequests.ShootRequest;
import model.viewRequests.SkillTreeAbilityRequests;
import utils.Vector;
import view.ViewData;
import view.objectViews.FrameView;
import view.objectViews.ObjectView;
import view.objectViews.effectView.EffectView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewRequestController {


    public static boolean shootRequest(Vector clickedPoint){
        if (ShootRequest.canShoot()){
            new ShootRequest(ModelData.getEpsilon()).shoot(clickedPoint);
            GameState.setTotalBullets(GameState.getTotalBullets() + 1);
            return true;
        }
        return false;
    }

    public static void inGameAbilityRequest(InGameAbilityType type){
        InGameAbilityRequests.abilityRequest(type);
    }

    public static void skillTreeAbilityRequest(SkillTreeAbilityType type) {
        SkillTreeAbilityRequests.abilityRequest(type);
    }

    public static void updateView(){
        ArrayList<ObjectView> objectViews;
        ArrayList<FrameView> frameViews;
        ArrayList<ObjectModel> objectModels;
        ArrayList<FrameModel> frameModels;
        ArrayList<EffectView> effectViews;
        ArrayList<EffectModel> effectModels;
        HashMap<ObjectModel, FrameModel> locals;
        ArrayList<SkillTreeAbility> skillTreeAbilities;
        ArrayList<InGameAbility> inGameAbilities;
        synchronized (ModelData.getModels()) {
            objectViews = (ArrayList<ObjectView>) ViewData.getViews().clone();
            frameViews = (ArrayList<FrameView>) ViewData.getFrames().clone();
            objectModels = (ArrayList<ObjectModel>) ModelData.getModels().clone();
            frameModels = (ArrayList<FrameModel>) ModelData.getFrames().clone();
            effectViews = (ArrayList<EffectView>) ViewData.getEffectViews().clone();
            effectModels = (ArrayList<EffectModel>) ModelData.getEffectModels().clone();
            locals = (HashMap<ObjectModel, FrameModel>) ModelData.getLocalFrames().clone();
            skillTreeAbilities = (ArrayList<SkillTreeAbility>) ModelData.getSkillTreeAbilities().clone();
            inGameAbilities = (ArrayList<InGameAbility>) ModelData.getInGameAbilities().clone();
        }
        updateFrames(frameModels ,frameViews ,locals ,objectModels);
        updateViews(objectModels ,objectViews);
        updateEffects(effectModels ,effectViews);
        ViewData.setViews(objectViews);
        ViewData.setFrames(frameViews);
        setAbilities(skillTreeAbilities ,inGameAbilities);
        setVariables();
    }

    private static void setAbilities(ArrayList<SkillTreeAbility> skillTreeAbilities, ArrayList<InGameAbility> inGameAbilities) {
        ViewData.setAbilityViews(new ArrayList<>());
        for (SkillTreeAbility skillTreeAbility : skillTreeAbilities) {
            ViewData.addAbilityWithType(
                    skillTreeAbility.getInGameCoolDownTime(),
                    skillTreeAbility.getCoolDownTimePassed(),
                    skillTreeAbility.isBought() && skillTreeAbility.CanCast(),
                    skillTreeAbility.getType()
            );
        }
        for (InGameAbility inGameAbility : inGameAbilities) {
            if (inGameAbility.getType() == InGameAbilityType.slaughter) {
                Slaughter slaughter = (Slaughter) (inGameAbility);
                ViewData.addAbilityWithType(
                        TimeConstants.SLAUGHTER_COOLDOWN,
                        slaughter.getTimePassed(),
                        slaughter.isAvailable(),
                        slaughter.getType()
                );
            }
            if (inGameAbility.getType() == InGameAbilityType.slumber) {
                Slumber slaughter = (Slumber) (inGameAbility);
                ViewData.addAbilityWithType(
                        TimeConstants.SLUMBER_DURATION,
                        slaughter.getTimePassed(),
                        slaughter.isAvailable(),
                        slaughter.getType()
                );
            }
        }
    }

    private static void updateEffects(ArrayList<EffectModel> effectModels, ArrayList<EffectView> effectViews) {
        for (int i = 0; i < effectViews.size(); i++) {
            int index = -1;
            for (int j = 0; j < effectModels.size(); j++) {
                if (effectModels.get(j).getId() == null)
                    continue;
                if (effectModels.get(j).getId().equals(effectViews.get(i).getId()))
                    index = j;
            }
            if (index == -1)
                continue;
            EffectModel effectModel = effectModels.get(index);
            effectViews.get(i).setArea(effectModel.getArea());
            effectViews.get(i).setTheta(effectModel.getTheta());
            effectViews.get(i).setColor(new Color(
                    effectModel.getR(),
                    effectModel.getG(),
                    effectModel.getB()
            ));
        }
    }

    private static void updateViews(ArrayList<ObjectModel> objectModels, ArrayList<ObjectView> objectViews) {
        for (int i = 0; i < objectViews.size(); i++) {
            int index = -1;
            for (int j = 0; j < objectModels.size(); j++) {
                if (objectModels.get(j).getId().equals(objectViews.get(i).getId()))
                    index = j;
            }
            if (index == -1)
                continue;
            ObjectModel model = objectModels.get(index);
            objectViews.get(i).setPosition(model.getPosition());
            objectViews.get(i).setTheta(model.getTheta());
            objectViews.get(i).setHovering(model.isHovering());
            if (model instanceof ImageChanger)
                objectViews.get(i).setImage(((ImageChanger) model).getImage());
            if (model instanceof SizeChanger && objectViews.get(i) instanceof SizeChanger)
                ((SizeChanger) objectViews.get(i)).setSize(((SizeChanger) model).getSize());
        }
    }

    private static void updateFrames(ArrayList<FrameModel> frameModels, ArrayList<FrameView> frameViews, HashMap<ObjectModel, FrameModel> locals, ArrayList<ObjectModel> objectModels) {
        for (int i = 0; i < frameViews.size(); i++) {
            int index = -1;
            for (int j = 0; j < frameModels.size(); j++) {
                if (frameModels.get(j).getId().equals(frameViews.get(i).getId()))
                    index = j;
            }
            if (index == -1)
                continue;

            FrameModel frame = frameModels.get(index);
            frameViews.get(i).setPosition(frame.getPosition());
            frameViews.get(i).setDimension(
                    new Dimension(
                            frame.getSize().width + SizeConstants.barD.width,
                            frame.getSize().height + SizeConstants.barD.height
                    )
            );
            if (locals.get(objectModels.getFirst()) == frame){
                ViewData.setEpsilonFrame(frameViews.get(i));
            }
        }
    }


    private static void setVariables(){
        ViewData.setTime(GameState.getTime());
        ViewData.setHp(GameState.getHp());
        ViewData.setXp(GameState.getXp());
        ViewData.setWave(GameState.getWave());
    }

    public static void buySkillTreeRequest(SkillTreeAbilityType type) {
        SkillTreeAbilityRequests.buyRequest(type);
    }
}
