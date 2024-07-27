package model;

import model.inGameAbilities.*;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.ObjectModel;
import model.skillTreeAbilities.SkillTreeAbility;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelData {

    private static ArrayList<FrameModel> frames;
    private static ArrayList<ObjectModel> models;
    private static HashMap<ObjectModel ,FrameModel> localFrames;
    private static ArrayList<EffectModel> effectModels;
    private static ArrayList<AbstractEnemy> abstractEnemies;
    private static ArrayList<InGameAbility> inGameAbilities;
    private static ArrayList<SkillTreeAbility> skillTreeAbilities = new ArrayList<>();
    public static ArrayList<AbstractEnemy> getAbstractEnemies() {
        return abstractEnemies;
    }
    private static EpsilonModel epsilon;
    private static FrameModel epsilonFrame;

    public static void resetAll() {
        frames = new ArrayList<>();
        models = new ArrayList<>();
        localFrames = new HashMap<>();
        effectModels = new ArrayList<>();
        abstractEnemies = new ArrayList<>();
        inGameAbilities = new ArrayList<>();
    }


    public static void setAbstractEnemies(ArrayList<AbstractEnemy> abstractEnemies) {
        ModelData.abstractEnemies = abstractEnemies;
    }

    public static ArrayList<FrameModel> getFrames() {
        return frames;
    }

    public static ArrayList<ObjectModel> getModels() {
        return models;
    }

    public static void setFrames(ArrayList<FrameModel> frames) {
        ModelData.frames = frames;
    }

    public static void setModels(ArrayList<ObjectModel> models) {
        ModelData.models = models;
    }

    public static void addModel(ObjectModel objectModel){
        models.add(objectModel);
    }

    public static void addFrame(FrameModel frameModel){
        frames.add(frameModel);
    }

    public static HashMap<ObjectModel, FrameModel> getLocalFrames() {
        return localFrames;
    }

    public static void setLocalFrames(HashMap<ObjectModel, FrameModel> localFrames) {
        ModelData.localFrames = localFrames;
    }

    public static ArrayList<EffectModel> getEffectModels() {
        return effectModels;
    }

    public static void setEffectModels(ArrayList<EffectModel> effectModels) {
        ModelData.effectModels = effectModels;
    }

    public static void removeModel(String id) {
        for (ObjectModel model : models){
            if (model.getId().equals(id)){
                models.remove(model);
                return;
            }
        }
    }

    public static void removeFrame(String id) {
        for (FrameModel frameModel : frames){
            if (frameModel.getId().equals(id)){
                frames.remove(frameModel);
                return;
            }
        }
    }

    public static void removeEffect(String id){
        for (EffectModel effectModel : effectModels){
            if (effectModel.getId().equals(id)){
                effectModels.remove(effectModel);
                return;
            }
        }
    }

    public static void addEffect(EffectModel effectModel) {
        effectModels.add(effectModel);
    }

    public static void removeAbstractEnemy(String id){
        for (AbstractEnemy abstractEnemy : abstractEnemies){
            if (abstractEnemy.getId().equals(id)){
                abstractEnemies.remove(abstractEnemy);
                return;
            }
        }
    }

    public static void addAbstractEnemy(AbstractEnemy abstractEnemy){
        abstractEnemies.add(abstractEnemy);
    }

    public static ArrayList<InGameAbility> getInGameAbilities() {
        return inGameAbilities;
    }

    public static void setInGameAbilities(ArrayList<InGameAbility> inGameAbilities) {
        ModelData.inGameAbilities = inGameAbilities;
    }

    public static ArrayList<SkillTreeAbility> getSkillTreeAbilities() {
        return skillTreeAbilities;
    }

    public static void setSkillTreeAbilities(ArrayList<SkillTreeAbility> skillTreeAbilities) {
        ModelData.skillTreeAbilities = skillTreeAbilities;
    }

    public static EpsilonModel getEpsilon() {
        return epsilon;
    }

    public static void setEpsilon(EpsilonModel epsilon) {
        ModelData.epsilon = epsilon;
    }

    public static FrameModel getEpsilonFrame() {
        return epsilonFrame;
    }

    public static void setEpsilonFrame(FrameModel epsilonFrame) {
        ModelData.epsilonFrame = epsilonFrame;
    }
}
