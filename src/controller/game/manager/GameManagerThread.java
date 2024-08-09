package controller.game.manager;

import constants.TimeConstants;
import controller.game.onlineGame.Game;
import controller.game.onlineGame.GameType;
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
import java.util.Random;

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
        checkDone();
        checkEnd();
    }

    private void checkDone() {
        if (game.getGameType().equals(GameType.monomachia)) {
            if (time / 1000 >= 1 * 60) {
                game.getGameState().setDone(true);
            }
        }
    }

    private void checkEnd() {
        synchronized (game.getPlayers()) {
            for (Player player : game.getPlayers()) {
                if (player.isDead()) {
                    if (player.getTeammate() != null) {
                        if (player.getTeammate().isDead()) {
                            game.end(player);
                            return;
                        }
                    }
                    else {
                        game.end(player);
                        return;
                    }
                }
            }
            if (game.getGameState().isDone()) {
                Player player1 = game.getPlayers().getFirst();
                Player player2 = game.getPlayers().get(1);
                if (game.getGameType().equals(GameType.monomachia)) {
                    if (player1.getPlayerData().getEpsilon().getHP() > player2.getPlayerData().getEpsilon().getHP()) {
                        game.end(player2);
                        return;
                    }
                    if (player2.getPlayerData().getEpsilon().getHP() > player1.getPlayerData().getEpsilon().getHP()) {
                        game.end(player1);
                        return;
                    }
                    if ((new Random()).nextInt(0 ,2) == 0) {
                        game.end(player1);
                        return;
                    }
                    else {
                        game.end(player2);
                        return;
                    }
                }
                else {
                    game.end(null);
                    return;
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
