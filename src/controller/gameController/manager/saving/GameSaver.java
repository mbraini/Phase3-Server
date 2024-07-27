package controller.gameController.manager.saving;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.gameController.manager.GameState;
import controller.gameController.manager.WaveSpawner;
import controller.gameController.manager.loading.SkippedByJson;
import model.inGameAbilities.InGameAbility;
import model.objectModel.ObjectModel;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.frameModel.FrameModel;
import model.skillTreeAbilities.SkillTreeAbility;
import utils.Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameSaver {

    private ArrayList<FrameModel> frames;
    private ArrayList<ObjectModel> models;
    private ArrayList<AbstractEnemy> abstractEnemies;
    private ArrayList<EffectModel> effects;
    private ArrayList<InGameAbility> abilities;
    private ArrayList<SkillTreeAbility> skillTreeAbilities;
    private String path;
    private static Gson gson;

    public GameSaver(ArrayList<ObjectModel> models, ArrayList<EffectModel> effects,
                     ArrayList<FrameModel> frames, ArrayList<AbstractEnemy> abstractEnemies,
                     ArrayList<InGameAbility> abilities ,ArrayList<SkillTreeAbility> skillTreeAbilities,
                     String path
    )
    {
        this.models = (ArrayList<ObjectModel>) models.clone();
        this.effects = (ArrayList<EffectModel>) effects.clone();
        this.frames = (ArrayList<FrameModel>) frames.clone();
        this.abstractEnemies = (ArrayList<AbstractEnemy>) abstractEnemies.clone();
        this.abilities = (ArrayList<InGameAbility>) abilities.clone();
        this.skillTreeAbilities = (ArrayList<SkillTreeAbility>) skillTreeAbilities.clone();
        this.path = path;
    }


    public synchronized void save() {
        saveGame();
        saveAbilities();
        saveSkillTree();
        saveGameState();
        saveXP();
    }

    private void saveXP() {
        Helper.saveXP(GameState.getXp());
    }

    private void saveGameState() {
        GameManagerHelperSaver gameState = new GameManagerHelperSaver();
        gameState.time = GameState.getTime();
        gameState.hp = GameState.getHp();
        gameState.wave = GameState.getWave();
        gameState.shrinkageVelocity = GameState.getShrinkageVelocity();
        gameState.enemyKilled = GameState.getEnemyKilled();
        gameState.totalBullets = GameState.getTotalBullets();
        gameState.successfulBullets = GameState.getSuccessfulBullets();
        gameState.enemyCount = GameState.getEnemyCount();
        gameState.lastWaveTime = GameState.getLastWaveTime();
        gameState.xpGained = GameState.getXpGained();
        gameState.firstWavePR = GameState.getFirstWavePR();
        gameState.secondWavePR = GameState.getSecondWavePR();
        gameState.thirdWavePR = GameState.getThirdWavePR();
        gameState.forthWavePR = GameState.getForthWavePR();
        gameState.fifthWavePR = GameState.getFifthWavePR();
        gameState.isPause = GameState.isPause();
        gameState.isOver = GameState.isOver();
        gameState.repeatedCount = WaveSpawner.repeatedCount;
        gameState.isDizzy = GameState.isDizzy();
        gameState.isInAnimation = GameState.isInAnimation();
        gameState.repeatedCount = WaveSpawner.repeatedCount;

        Gson gson = getGson();
        String gameStateString = gson.toJson(gameState);
        Helper.writeFile(path + "/gameState.json" ,gameStateString);
    }

    private void saveSkillTree() {
        Gson gson = getGson();
        String skillTreeString = gson.toJson(skillTreeAbilities);
        Helper.writeFile(path + "/skillTree.json" ,skillTreeString);
    }

    private void saveAbilities() {
        Gson gson = getGson();
        String abilityString = gson.toJson(abilities);
        Helper.writeFile(path + "/abilities.json" ,abilityString);
    }

    private void saveGame() {
        Gson gson = getGson();

        String modelString = gson.toJson(models);
        String frameString = gson.toJson(frames);
        String abstractEnemyString = gson.toJson(abstractEnemies);
        String effectString = gson.toJson(effects);

        Helper.writeFile(path + "/models.json" ,modelString);
        Helper.writeFile(path + "/frames.json" ,frameString);
        Helper.writeFile(path + "/abstractEnemies.json" ,abstractEnemyString);
        Helper.writeFile(path + "/effects.json" ,effectString);
    }

    private Gson getGson() {
        if (gson != null)
            return gson;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(SkippedByJson.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                if (aClass.getAnnotation(SkippedByJson.class) == null)
                    return false;
                return true;
            }
        });
        gson = builder.create();
        return gson;
    }

    public static boolean isPortalSaved() {
        StringBuilder stringBuilder = Helper.readFile("src/controller/manager/saving/portalSaved/models.json");
        if (stringBuilder.toString().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isGameSaved() {
        StringBuilder stringBuilder = Helper.readFile("src/controller/manager/saving/inGameSaved/models.json");
        if (stringBuilder.toString().isEmpty()) {
            return false;
        }
        return true;
    }


}
