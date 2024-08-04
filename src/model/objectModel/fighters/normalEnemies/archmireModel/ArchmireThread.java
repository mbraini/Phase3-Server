package model.objectModel.fighters.normalEnemies.archmireModel;

import constants.ControllerConstants;
import constants.DamageConstants;
import constants.TimeConstants;
import controller.game.manager.GameState;
import controller.game.manager.Spawner;
import model.ModelData;
import model.logics.collision.Collision;
import model.objectModel.ObjectModel;
import model.objectModel.effects.ArchmireAoeEffectModel;
import model.objectModel.fighters.EnemyModel;
import model.objectModel.fighters.EpsilonModel;
import utils.Helper;

import java.util.ArrayList;


public class ArchmireThread extends Thread{

    private double time;
    private ArchmireModel archmire;
    private ArrayList<String> removedAoe = new ArrayList<>();
    private ArrayList<ObjectModel> models;

    public ArchmireThread(ArchmireModel archmire){
        this.archmire = archmire;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double deltaModel = 0;
        while (!archmire.getGame().getGameState().isOver()) {
            if (archmire.getGame().getGameState().isPause()) {
                lastTime = System.nanoTime();
                continue;
            }
            if (isInterrupted())
                return;
            long now = System.nanoTime();
            deltaModel += (now - lastTime) / ns;
            lastTime = now;
            if (deltaModel >= TimeConstants.ARCHMIRE_THREAD_REFRESH_RATE) {
                updateAOE();
                deltaModel = 0;
                time += TimeConstants.ARCHMIRE_THREAD_REFRESH_RATE;
            }
        }
    }

    private void updateAOE() {
        if (archmire.getGame().getGameState().isDizzy())
            return;
        synchronized (archmire.getGame().getModelData().getModels()){
            models = (ArrayList<ObjectModel>) archmire.getGame().getModelData().getModels().clone();
        }
        checkRemovedAOEs();
        addEffect();
        checkDamage();
    }

    private void checkDamage() {
        if (time % 1000 != 0)
            return;

        ArrayList<ObjectModel> collidedModels = new ArrayList<>();

        for (ObjectModel model : models){
            if (isCollided(model)){
                collidedModels.add(model);
            }
        }

        for (ObjectModel model : collidedModels){
            if (model instanceof ArchmireModel)
                continue;
            if (Collision.IsColliding(model ,archmire)){
                model.setHP(model.getHP() - DamageConstants.ARCHMIRE_DROWN_DAMAGE_PER_SECOND);
            }
            else {
                model.setHP(model.getHP() - DamageConstants.ARCHMIRE_AOE_DAMAGE_PER_SECOND);
            }
        }

    }

    private synchronized boolean isCollided(ObjectModel model) {
        for (ArchmireAoeEffectModel effectModel : archmire.getAoeEffects()){
            if (Collision.IsColliding(effectModel ,model)) {
                if (model instanceof EnemyModel || model instanceof EpsilonModel)
                    return true;
            }
        }
        return false;
    }

    private synchronized void checkRemovedAOEs() {
        for (String id : removedAoe){
            removeAoe(id);
        }
        removedAoe = new ArrayList<>();
    }

    private synchronized void removeAoe(String id) {
        for (ArchmireAoeEffectModel effectModel : archmire.getAoeEffects()){
            if (effectModel.getId().equals(id)){
                archmire.getAoeEffects().remove(effectModel);
                return;
            }
        }
    }

    private synchronized void addEffect() {
        ArchmireAoeEffectModel effectModel = new ArchmireAoeEffectModel(
                archmire.getGame(),
                archmire,
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
        Spawner.addArchmireEffect(archmire.getGame() ,effectModel);
        archmire.getAoeEffects().add(effectModel);
    }

    public ArrayList<String> getRemovedAoe() {
        return removedAoe;
    }
}
