package controller.gameController.manager;

import constants.TimeConstants;
import controller.gameController.manager.saving.GameSaver;
import model.ModelData;
import model.inGameAbilities.InGameAbility;
import model.interfaces.Fader;
import model.objectModel.effects.EffectModel;
import model.objectModel.ObjectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.fighters.finalBoss.bossHelper.HeadModel;
import model.objectModel.frameModel.FrameModel;
import model.skillTreeAbilities.SkillTreeAbility;

import java.util.ArrayList;

public class GameManagerThread extends Thread{
    private ArrayList<ObjectModel> models;
    private ArrayList<EffectModel> effects;
    private ArrayList<FrameModel> frames;
    private ArrayList<AbstractEnemy> abstractEnemies;
    private ArrayList<InGameAbility> abilities;
    private ArrayList<SkillTreeAbility> skillTreeAbilities;
    private double time;
    private final static Object jsonLock = new Object();
    private int epsilonDeath;
    private int bossDeath;

    public GameManagerThread() {

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double deltaModel = 0;
        while (!GameState.isOver() && epsilonDeath <= 0) {
            if (GameState.isPause()) {
                lastTime = System.nanoTime();
                continue;
            }
            long now = System.nanoTime();
            deltaModel += (now - lastTime) / ns;
            lastTime = now;
            if (deltaModel >= TimeConstants.MANAGER_THREAD_REFRESH_TIME) {
                manage();
                deltaModel = 0;
                time += TimeConstants.MANAGER_THREAD_REFRESH_TIME;
            }
        }
    }

    private void manage() {

        synchronized (ModelData.getModels()){
            models = (ArrayList<ObjectModel>) ModelData.getModels().clone();
            effects = (ArrayList<EffectModel>) ModelData.getEffectModels().clone();
            frames = (ArrayList<FrameModel>) ModelData.getFrames().clone();
            abstractEnemies = (ArrayList<AbstractEnemy>) ModelData.getAbstractEnemies().clone();
            abilities = (ArrayList<InGameAbility>) ModelData.getInGameAbilities().clone();
            skillTreeAbilities =(ArrayList<SkillTreeAbility>) ModelData.getSkillTreeAbilities().clone();
        }
        interfaces();
        checkAoeDamage();
        if (time % 1000 == 0) {
            synchronized (jsonLock) {
                new GameSaver(
                        models,
                        effects,
                        frames,
                        abstractEnemies ,
                        abilities ,
                        skillTreeAbilities,
                        "src/controller/manager/saving/inGameSaved"
                ).save();
            }
        }
        GameState.update(models , TimeConstants.MANAGER_THREAD_REFRESH_TIME);
        killObjects();
    }

    private void checkAoeDamage() {

    }

    private void interfaces() {
        for (ObjectModel model : models){
            if (model instanceof Fader){
                ((Fader) model).addTime(TimeConstants.MANAGER_THREAD_REFRESH_TIME);
                ((Fader) model).fadeIf();
            }
        }
        for (EffectModel effect : effects){
            if (effect instanceof Fader){
                ((Fader) effect).addTime(TimeConstants.MANAGER_THREAD_REFRESH_TIME);
                ((Fader) effect).fadeIf();
            }
        }
    }

    private void killObjects() {
        for (ObjectModel model : models){
            if (model.getHP() <= 0) {
                if (model instanceof  EpsilonModel) {
                    epsilonDeath++;
                    if (epsilonDeath > 1) {
                        return;
                    }
                }
                if (model instanceof HeadModel) {
                    bossDeath++;
                    if (bossDeath > 1)
                        return;
                }
                model.die();
            }
        }
    }

    public static Object getJsonLock() {
        return jsonLock;
    }

}
