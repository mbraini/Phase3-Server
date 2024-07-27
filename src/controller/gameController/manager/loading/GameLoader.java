package controller.gameController.manager.loading;

import com.google.gson.*;
import controller.gameController.enums.AbstractEnemyType;
import controller.gameController.enums.InGameAbilityType;
import controller.gameController.enums.ModelType;
import controller.gameController.enums.SkillTreeAbilityType;
import controller.gameController.manager.GameState;
import controller.gameController.manager.WaveSpawner;
import controller.gameController.manager.saving.GameManagerHelperSaver;
import model.ModelData;
import model.inGameAbilities.InGameAbility;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.miniBossEnemies.blackOrbModel.BlackOrbModel;
import model.objectModel.frameModel.FrameModel;
import model.skillTreeAbilities.SkillTreeAbility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import utils.Helper;
import view.ViewRequest;
import view.objectViews.FrameView;

import java.util.ArrayList;

public class GameLoader {

    private static ArrayList<FrameModel> framesSpawnedByObjects;
    private static Gson gson;
    private String path;

    public GameLoader(String path){
        this.path = path;
        framesSpawnedByObjects = new ArrayList<>();
    }

    public synchronized void load() {
        loadGame();
        loadAbilities();
        loadSkillTree();
        loadGameState();
    }


    private void loadGameState() {
        gson = getGson();

        StringBuilder gameStateString = Helper.readFile(path + "/gameState.json");

        GameManagerHelperSaver gameState = gson.fromJson(gameStateString.toString() ,GameManagerHelperSaver.class);
        GameState.setTime(gameState.time);
        GameState.setHp(gameState.hp);
        GameState.setWave(gameState.wave);
        GameState.setShrinkageVelocity(gameState.shrinkageVelocity);
        GameState.setEnemyKilled(gameState.enemyKilled);
        GameState.setEnemyCount(gameState.enemyCount);
        GameState.setLastWaveTime(gameState.lastWaveTime);
        GameState.setXpGained(gameState.xpGained);
        GameState.setFirstWavePR(gameState.firstWavePR);
        GameState.setSecondWavePR(gameState.secondWavePR);
        GameState.setThirdWavePR(gameState.thirdWavePR);
        GameState.setForthWavePR(gameState.forthWavePR);
        GameState.setFifthWavePR(gameState.fifthWavePR);
        GameState.setTotalBullets(gameState.totalBullets);
        GameState.setSuccessfulBullets(gameState.successfulBullets);
        GameState.setOver(gameState.isOver);
        GameState.setDizzy(gameState.isDizzy);
        GameState.setPause(gameState.isPause);
        GameState.setIsInAnimation(gameState.isInAnimation);

        WaveSpawner.repeatedCount = gameState.repeatedCount;
    }

    public void loadSkillTree() {
        gson = getGson();

        StringBuilder skillTreeString = Helper.readFile(path + "/skillTree.json");
        ArrayList<SkillTreeAbility> abilities;
        try {
            JSONArray jAbilities = (JSONArray) new JSONTokener(skillTreeString.toString()).nextValue();
            for (int i = 0; i <jAbilities.length() ;i++){
                JSONObject jAbility = jAbilities.getJSONObject(i);
                String jType = jAbility.get("type").toString();
                SkillTreeAbilityType type = gson.fromJson(jType , SkillTreeAbilityType.class);
                GameLoaderHelper.addSkillTree(jAbility ,type);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAbilities() {
        gson = getGson();

        StringBuilder abilityString = Helper.readFile(path + "/abilities.json");
        ArrayList<InGameAbility> abilities;
        try {
            JSONArray jAbilities = (JSONArray) new JSONTokener(abilityString.toString()).nextValue();
            for (int i = 0; i <jAbilities.length() ;i++){
                JSONObject jAbility = jAbilities.getJSONObject(i);
                String jType = jAbility.get("type").toString();
                InGameAbilityType type = gson.fromJson(jType , InGameAbilityType.class);
                GameLoaderHelper.addAbility(jAbility ,type);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGame() {
        gson = getGson();

        StringBuilder modelString = Helper.readFile(path + "/models.json");
        StringBuilder frameString = Helper.readFile(path + "/frames.json");
        StringBuilder abstractEnemyString = Helper.readFile(path + "/abstractEnemies.json");
        StringBuilder effectString = Helper.readFile(path + "/effects.json");
        try {
            JSONArray jModels = (JSONArray) new JSONTokener(modelString.toString()).nextValue();
            for (int i = 0; i <jModels.length() ;i++){
                JSONObject jModel = jModels.getJSONObject(i);
                String jType = jModel.get("type").toString();
                ModelType type = gson.fromJson(jType , ModelType.class);
                GameLoaderHelper.addModel(jModel ,type);
            }
            JSONArray jFrames = (JSONArray) new JSONTokener(frameString.toString()).nextValue();
            for (int i = 0; i < jFrames.length() ;i++){/////////////only one frame!
                JSONObject jModel = jFrames.getJSONObject(i);
                FrameModel frameModel = gson.fromJson(jModel.toString() , FrameModel.class);
                if (i == 0){
                    ModelData.addFrame(frameModel);
                    ModelData.setEpsilonFrame(frameModel);
                    ViewRequest.addFrameView(new FrameView(
                            frameModel.getPosition(),
                            frameModel.getSize(),
                            frameModel.getId()
                    ));
                    continue;
                }
                if (!spawnedByObjects(frameModel.getId()))
                    GameLoaderHelper.addFrame(frameModel);
            }
            for (FrameModel frameModel : framesSpawnedByObjects)
                GameLoaderHelper.addFrame(frameModel);
            JSONArray jAbstractEnemies = (JSONArray) new JSONTokener(abstractEnemyString.toString()).nextValue();
            for (int i = 0; i < jAbstractEnemies.length() ; i++){/////////////only one frame!
                JSONObject jAbstract = jAbstractEnemies.getJSONObject(i);
                String jType = jAbstract.get("type").toString();
                AbstractEnemyType type = gson.fromJson(jType , AbstractEnemyType.class);
                if (type == AbstractEnemyType.blackOrb) {
                    GameLoaderHelper.addBlackOrb(
                            gson.fromJson(jAbstract.toString(), BlackOrbModel.class),
                            jAbstract
                    );
                }
                else {
                    GameLoaderHelper.addBoss(
                            gson.fromJson(jAbstract.toString(), Boss.class)
                    );
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean spawnedByObjects(String id) {
        for (FrameModel frameModel : framesSpawnedByObjects){
            if (frameModel.getId().equals(id))
                return true;
        }
        return false;
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
        return builder.create();
    }

    public static void addFrame(FrameModel frameModel){
        framesSpawnedByObjects.add(frameModel);
    }


}
