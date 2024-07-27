package model;

import model.objectModel.ObjectModel;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.frameModel.FrameModel;

import java.util.ArrayList;

public class ModelRequests {

    public static ArrayList<String> removeObjectModelReq;
    private static ArrayList<String> removeFrameModelReq;
    private static ArrayList<String> removeEffectModelReq;
    private static ArrayList<String> removeAbstractEnemyReq;
    private static ArrayList<AbstractEnemy> addedAbstractEnemy;
    private static ArrayList<EffectModel> addedEffectModel;
    private static ArrayList<ObjectModel> addedObjectModel;
    private static ArrayList<FrameModel> addedFrameModel;
    private static boolean endRequest;

    public static void resetAll() {
        removeObjectModelReq = new ArrayList<>();
        removeFrameModelReq = new ArrayList<>();
        removeEffectModelReq = new ArrayList<>();
        removeAbstractEnemyReq = new ArrayList<>();
        addedAbstractEnemy = new ArrayList<>();
        addedEffectModel = new ArrayList<>();
        addedObjectModel = new ArrayList<>();
        addedFrameModel = new ArrayList<>();
    }

    public static void checkRequests(){
        if (endRequest) {
            endRequest = false;
            resetAll();
            ModelData.resetAll();
        }
        checkObjects();
        checkFrames();
        checkEffects();
        checkAbstractEnemies();
    }

    private static void checkAbstractEnemies() {
        for (int i = 0 ;i < addedAbstractEnemy.size() ;i++){
            ModelData.addAbstractEnemy(addedAbstractEnemy.get(i));
            addedAbstractEnemy.remove(i);
            i--;
        }
        for (int i = 0 ;i < removeAbstractEnemyReq.size() ;i++){
            ModelData.removeAbstractEnemy(removeAbstractEnemyReq.get(i));
            removeAbstractEnemyReq.remove(i);
            i--;
        }
    }

    private static void checkEffects() {
        for (int i = 0 ;i < addedEffectModel.size() ;i++){
            ModelData.addEffect(addedEffectModel.get(i));
            addedEffectModel.remove(i);
            i--;
        }
        for (int i = 0 ;i < removeEffectModelReq.size() ;i++){
            ModelData.removeEffect(removeEffectModelReq.get(i));
            removeEffectModelReq.remove(i);
            i--;
        }
    }

    private static void checkObjects() {
        for (int i = 0; i <addedObjectModel.size() ;i++){
            ModelData.addModel(addedObjectModel.get(i));
            addedObjectModel.remove(i);
            i--;
        }
        for (int i = 0; i < removeObjectModelReq.size() ;i++){
            ModelData.removeModel(removeObjectModelReq.get(i));
            removeObjectModelReq.remove(i);
            i--;
        }
    }

    private static void checkFrames() {
        for (int i = 0; i <addedFrameModel.size() ;i++){
            ModelData.addFrame(addedFrameModel.get(i));
            addedFrameModel.remove(i);
            i--;
        }
        for (int i = 0 ;i < removeFrameModelReq.size() ;i++){
            ModelData.removeFrame(removeFrameModelReq.get(i));
            removeFrameModelReq.remove(i);
            i--;
        }
    }


    public synchronized static void addObjectModel(ObjectModel objectModel){
        addedObjectModel.add(objectModel);
    }

    public synchronized static void addFrameModel(FrameModel frameModel){
        addedFrameModel.add(frameModel);
    }

    public synchronized static void removeObjectModel(String id){
        removeObjectModelReq.add(id);
    }

    public synchronized static void removeFrameModel(String id){
        removeFrameModelReq.add(id);
    }

    public synchronized static void addEffectModel(EffectModel effectModel){
        addedEffectModel.add(effectModel);
    }

    public synchronized static void removeEffectModel(String id){
        removeEffectModelReq.add(id);
    }

    public synchronized static void addAbstractEnemy(AbstractEnemy abstractEnemy){
        addedAbstractEnemy.add(abstractEnemy);
    }

    public synchronized static void removeAbstractEnemy(String id){
        removeAbstractEnemyReq.add(id);
    }


    public static void endRequest() {
        endRequest = true;
        checkRequests();
    }
}
