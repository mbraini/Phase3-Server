package model.objectModel.fighters.miniBossEnemies.blackOrbModel;

import constants.ControllerConstants;
import constants.DamageConstants;
import constants.RefreshRateConstants;
import controller.game.manager.GameState;
import model.ModelData;
import model.logics.collision.Collision;
import model.objectModel.ObjectModel;
import model.objectModel.effects.BlackOrbAoeEffectModel;
import model.objectModel.fighters.EnemyModel;
import model.objectModel.fighters.EpsilonModel;
import utils.Helper;

import java.util.ArrayList;

public class BlackOrbThread extends Thread{
    private BlackOrbModel blackOrbModel;
    private ArrayList<OrbModel> models;
    private ArrayList<OrbModel> orbModels;
    ArrayList<BlackOrbAoeEffectModel> effects;
    private double time;
    private double frameTime;
    private double orbTime;
    private boolean spawningDone;

    public BlackOrbThread(BlackOrbModel blackOrbModel){
        this.blackOrbModel = blackOrbModel;
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double deltaModel = 0;
        while (!GameState.isOver()) {
            if (GameState.isPause()){
                lastTime = System.nanoTime();
                continue;
            }
            if (isInterrupted())
                return;
            long now = System.nanoTime();
            deltaModel += (now - lastTime) / ns;
            lastTime = now;
            if (deltaModel >= RefreshRateConstants.BLACK_ORB_THEAD_REFRESH_RATE) {
                if (GameState.isDizzy()){
                    setUpDizzy();
                    continue;
                }
                else {
                    unSetUpDizzy();
                }
                updateBlackOrb();
                deltaModel = 0;
                time += RefreshRateConstants.BLACK_ORB_THEAD_REFRESH_RATE;
            }
        }

    }

    private synchronized void setUpDizzy() {
        for (BlackOrbAoeEffectModel effectModel : blackOrbModel.getEffectModels()){
            effectModel.setR(0);
            effectModel.setG(0);
            effectModel.setB(0);
        }
    }

    private synchronized void unSetUpDizzy(){
        for (BlackOrbAoeEffectModel effectModel : blackOrbModel.getEffectModels()){
            effectModel.setR(255);
            effectModel.setG(0);
            effectModel.setB(255);
        }
    }

    private void updateBlackOrb() {
        if (!spawningDone) {
            spawn();
        }
        updateVariables();
        checkAoeDamage();
    }

    private void spawn() {
        if (blackOrbModel.getOrbCount() == 5 && blackOrbModel.getFrameCount() == 5){
            spawningDone = true;
        }
        if (blackOrbModel.getFrameCount() < 5){
            frameTime += RefreshRateConstants.BLACK_ORB_THEAD_REFRESH_RATE;
            spawnFrameIf();
            return;
        }
        if (blackOrbModel.getOrbCount() < 5){
            orbTime += RefreshRateConstants.BLACK_ORB_THEAD_REFRESH_RATE;
            spawnOrbIf();
        }
    }

    private void spawnOrbIf() {
        if (orbTime >= 1000){
            orbTime = 0;
            new OrbSpawner(blackOrbModel ,blackOrbModel.getOrbCount()).spawn();
        }
    }

    private void spawnFrameIf() {
        if (frameTime >= 1000){
            frameTime = 0;
            new FrameSpawner(blackOrbModel ,blackOrbModel.getFrameCount()).spawn();
        }
    }

    private void updateVariables() {
        synchronized (ModelData.getModels()){
            models = (ArrayList<OrbModel>) ModelData.getModels().clone();
        }
        synchronized (blackOrbModel.getEffectModels()) {
            effects =
                    (ArrayList<BlackOrbAoeEffectModel>) blackOrbModel.getEffectModels().clone();
            orbModels = (ArrayList<OrbModel>) blackOrbModel.getOrbModels().clone();
        }
    }

    private void checkAoeDamage() {
        if (time % 1000 != 0)
            return;
        ArrayList<ObjectModel> collidedObjects = new ArrayList<>();

        for (ObjectModel model : models){
            if (isCollided(model)){
                collidedObjects.add(model);
            }
        }

        for (ObjectModel model : collidedObjects){
            if (model instanceof OrbModel)
                continue;
            model.setHP(model.getHP() - DamageConstants.BLACK_ORB_LASER_DAMAGE);
        }
    }

    private boolean isCollided(ObjectModel model) {
        updateVariables();
        for (BlackOrbAoeEffectModel effectModel : effects){
            if (Collision.IsColliding(effectModel ,model))
                if (model instanceof EpsilonModel || model instanceof EnemyModel)
                    return true;
        }
        return false;
    }

    public void connectLasers(int index) {
        updateVariables();
        for (int i = 0; i < orbModels.size() ;i++){
            if (i == index)
                continue;
            blackOrbModel.addEffect(
                    blackOrbModel,
                    orbModels.get(i),
                    orbModels.get(index),
                    Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
            );
        }
    }

    public synchronized void disconnectLasers(OrbModel orbModel) {
        ArrayList<BlackOrbAoeEffectModel> effectModels =
                (ArrayList<BlackOrbAoeEffectModel>) blackOrbModel.getEffectModels().clone();
        for (BlackOrbAoeEffectModel effect : effectModels){
            if (effect.getOrbDestination().getId().equals(orbModel.getId())
                    || effect.getOrbOrigin().getId().equals(orbModel.getId()))
                effect.die();
        }
    }

    public void setBlackOrbModel(BlackOrbModel blackOrbModel) {
        this.blackOrbModel = blackOrbModel;
    }
}
