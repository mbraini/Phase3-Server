package model;

import controller.game.Game;
import model.objectModel.ObjectModel;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.frameModel.FrameModel;

import java.util.ArrayList;

public class ModelRequests {

    public ArrayList<String> removeObjectModelReq;
    private ArrayList<String> removeFrameModelReq;
    private ArrayList<String> removeEffectModelReq;
    private ArrayList<String> removeAbstractEnemyReq;
    private ArrayList<AbstractEnemy> addedAbstractEnemy;
    private ArrayList<EffectModel> addedEffectModel;
    private ArrayList<ObjectModel> addedObjectModel;
    private ArrayList<FrameModel> addedFrameModel;
    private boolean endRequest;
    private Game game;


    public ModelRequests(Game game) {
        this.game = game;
        resetAll();
    }



    public synchronized void resetAll() {
        removeObjectModelReq = new ArrayList<>();
        removeFrameModelReq = new ArrayList<>();
        removeEffectModelReq = new ArrayList<>();
        removeAbstractEnemyReq = new ArrayList<>();
        addedAbstractEnemy = new ArrayList<>();
        addedEffectModel = new ArrayList<>();
        addedObjectModel = new ArrayList<>();
        addedFrameModel = new ArrayList<>();
    }

    public synchronized void checkRequests(){
        if (endRequest) {
            endRequest = false;
            resetAll();
            game.getModelData().resetAll();
        }
        checkObjects();
        checkFrames();
        checkEffects();
        checkAbstractEnemies();
    }

    private synchronized void checkAbstractEnemies() {
        for (int i = 0 ;i < addedAbstractEnemy.size() ;i++){
            game.getModelData().addAbstractEnemy(addedAbstractEnemy.get(i));
            addedAbstractEnemy.remove(i);
            i--;
        }
        for (int i = 0 ;i < removeAbstractEnemyReq.size() ;i++){
            game.getModelData().removeAbstractEnemy(removeAbstractEnemyReq.get(i));
            removeAbstractEnemyReq.remove(i);
            i--;
        }
    }

    private synchronized void checkEffects() {
        for (int i = 0 ;i < addedEffectModel.size() ;i++){
            game.getModelData().addEffect(addedEffectModel.get(i));
            addedEffectModel.remove(i);
            i--;
        }
        for (int i = 0 ;i < removeEffectModelReq.size() ;i++){
            game.getModelData().removeEffect(removeEffectModelReq.get(i));
            removeEffectModelReq.remove(i);
            i--;
        }
    }

    private synchronized void checkObjects() {
        for (int i = 0; i <addedObjectModel.size() ;i++){
            game.getModelData().addModel(addedObjectModel.get(i));
            addedObjectModel.remove(i);
            i--;
        }
        for (int i = 0; i < removeObjectModelReq.size() ;i++){
            game.getModelData().removeModel(removeObjectModelReq.get(i));
            removeObjectModelReq.remove(i);
            i--;
        }
    }

    private synchronized void checkFrames() {
        for (int i = 0; i <addedFrameModel.size() ;i++){
            game.getModelData().addFrame(addedFrameModel.get(i));
            addedFrameModel.remove(i);
            i--;
        }
        for (int i = 0 ;i < removeFrameModelReq.size() ;i++){
            game.getModelData().removeFrame(removeFrameModelReq.get(i));
            removeFrameModelReq.remove(i);
            i--;
        }
    }


    public synchronized void addObjectModel(ObjectModel objectModel){
        addedObjectModel.add(objectModel);
    }

    public synchronized void addFrameModel(FrameModel frameModel){
        addedFrameModel.add(frameModel);
    }

    public synchronized void removeObjectModel(String id){
        removeObjectModelReq.add(id);
    }

    public synchronized void removeFrameModel(String id){
        removeFrameModelReq.add(id);
    }

    public synchronized void addEffectModel(EffectModel effectModel){
        addedEffectModel.add(effectModel);
    }

    public synchronized void removeEffectModel(String id){
        removeEffectModelReq.add(id);
    }

    public synchronized void addAbstractEnemy(AbstractEnemy abstractEnemy){
        addedAbstractEnemy.add(abstractEnemy);
    }

    public synchronized void removeAbstractEnemy(String id){
        removeAbstractEnemyReq.add(id);
    }


    public synchronized void endRequest() {
        endRequest = true;
        checkRequests();
    }
}
