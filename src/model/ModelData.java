package model;

import controller.game.Game;
import model.inGameAbilities.InGameAbility;
import model.objectModel.ObjectModel;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.frameModel.FrameModel;
import model.skillTreeAbilities.SkillTreeAbility;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelData {

    private Game game;

    public ModelData(Game game) {
        this.game = game;
        resetAll();
    }

    private ArrayList<FrameModel> frames;
    private ArrayList<ObjectModel> models;
    private HashMap<ObjectModel ,FrameModel> localFrames;
    private ArrayList<EffectModel> effectModels;
    private ArrayList<AbstractEnemy> abstractEnemies;
    public ArrayList<AbstractEnemy> getAbstractEnemies() {
        return abstractEnemies;
    }

    public void resetAll() {
        frames = new ArrayList<>();
        models = new ArrayList<>();
        localFrames = new HashMap<>();
        effectModels = new ArrayList<>();
        abstractEnemies = new ArrayList<>();
    }


    public void setAbstractEnemies(ArrayList<AbstractEnemy> abstractEnemies) {
        this.abstractEnemies = abstractEnemies;
    }

    public ArrayList<FrameModel> getFrames() {
        return frames;
    }

    public ArrayList<ObjectModel> getModels() {
        return models;
    }

    public void setFrames(ArrayList<FrameModel> frames) {
        this.frames = frames;
    }

    public void setModels(ArrayList<ObjectModel> models) {
        this.models = models;
    }

    public void addModel(ObjectModel objectModel){
        models.add(objectModel);
    }

    public void addFrame(FrameModel frameModel){
        frames.add(frameModel);
    }

    public HashMap<ObjectModel, FrameModel> getLocalFrames() {
        return localFrames;
    }

    public void setLocalFrames(HashMap<ObjectModel, FrameModel> localFrames) {
        this.localFrames = localFrames;
    }

    public ArrayList<EffectModel> getEffectModels() {
        return effectModels;
    }

    public void setEffectModels(ArrayList<EffectModel> effectModels) {
        this.effectModels = effectModels;
    }

    public void removeModel(String id) {
        for (ObjectModel model : models){
            if (model.getId().equals(id)){
                models.remove(model);
                return;
            }
        }
    }

    public void removeFrame(String id) {
        for (FrameModel frameModel : frames){
            if (frameModel.getId().equals(id)){
                frames.remove(frameModel);
                return;
            }
        }
    }

    public void removeEffect(String id){
        for (EffectModel effectModel : effectModels){
            if (effectModel.getId().equals(id)){
                effectModels.remove(effectModel);
                return;
            }
        }
    }

    public void addEffect(EffectModel effectModel) {
        effectModels.add(effectModel);
    }

    public void removeAbstractEnemy(String id){
        for (AbstractEnemy abstractEnemy : abstractEnemies){
            if (abstractEnemy.getId().equals(id)){
                abstractEnemies.remove(abstractEnemy);
                return;
            }
        }
    }

    public void addAbstractEnemy(AbstractEnemy abstractEnemy){
        abstractEnemies.add(abstractEnemy);
    }

}
