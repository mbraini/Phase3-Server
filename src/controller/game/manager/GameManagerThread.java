package controller.game.manager;

import constants.TimeConstants;
import controller.game.Game;
import controller.game.GameType;
import controller.game.player.Player;
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
    private Game game;

    public GameManagerThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double deltaModel = 0;
        while (!game.getGameState().isOver() && epsilonDeath <= 0) {
            if (game.getGameState().isPause()) {
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

        synchronized (game.getModelData().getModels()){
            models = (ArrayList<ObjectModel>) game.getModelData().getModels().clone();
        }
        synchronized (game.getModelData().getEffectModels()) {
            effects = (ArrayList<EffectModel>) game.getModelData().getEffectModels().clone();
        }
        synchronized (game.getModelData().getFrames()) {
            frames = (ArrayList<FrameModel>) game.getModelData().getFrames().clone();
        }
        synchronized (game.getModelData().getAbstractEnemies()) {
            abstractEnemies = (ArrayList<AbstractEnemy>) game.getModelData().getAbstractEnemies().clone();
        }
        interfaces();
        game.getGameState().update(models , TimeConstants.MANAGER_THREAD_REFRESH_TIME);
        killObjects();
        checkEnd();
    }

    private void checkEnd() {
        synchronized (game.getPlayers()) {
            if (game.getGameType().equals(GameType.monomachia)) {
                for (Player player : game.getPlayers()) {
                    if (player.isDead()) {
                        if (player.getTeammate() != null) {
                            if (player.getTeammate().isDead()) {
                                game.end();
                                return;
                            }
                        }
                        else {
                            game.end();
                        }
                    }
                }
            }
            else {
                for (Player player : game.getPlayers()) {
                    if (player.isDead()) {
                        if (player.getTeammate() != null) {
                            if (player.getTeammate().isDead()) {
                                game.end();
                                return;
                            }
                        }
                        else {
                            game.end();
                        }
                    }
                }
            }
        }
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
                if (model instanceof HeadModel) {
                    bossDeath++;
                    if (bossDeath > 1)
                        continue;
                }
                if (model instanceof EpsilonModel) {
                    ((EpsilonModel) model).getBelongingPlayer().setDead(true);
                }
                model.die();
            }
        }
    }

}
