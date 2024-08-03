package controller.game;

import com.google.gson.Gson;
import constants.ControllerConstants;
import constants.SizeConstants;
import controller.game.configs.Configs;
import controller.game.configs.helper.GameConfigsJsonHelper;
import controller.game.manager.GameManager;
import controller.game.manager.GameState;
import model.ModelData;
import model.ModelRequests;
import model.animations.GameStartAnimation;
import model.inGameAbilities.InGameAbilityHandler;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.frameModel.FrameModelBuilder;
import model.skillTreeAbilities.SkillTreeAbilityHandler;
import model.threads.FrameThread;
import model.threads.GameLoop;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import utils.Helper;
import utils.Vector;

import java.awt.*;

public abstract class Controller {
    private static GameLoop gameLoop;
    private static FrameThread frameThread;
    private static GameManager gameManager;
    private static GameMode gameMode;

    public static void resume() {
        GameState.setPause(false);
    }

    public static void pause() {
        GameState.setPause(true);
    }

    public static boolean addXP(int pr) {
        GameState.setXp(GameState.getXp() + pr);
        if (pr > 0) {
            GameState.setXpGained(GameState.getXpGained() + pr);
        }
        if (GameState.getXp() < 0) {
            GameState.setXp(GameState.getXp() - pr);
            return false;
        }
        return true;
    }

    public static void saveGameInPortal() {
        ModelData.getEpsilon().setHP(ModelData.getEpsilon().getHP() + 10);
        ModelData.getEpsilon().checkHP();
    }

    public static void rumModel() {
        setModelConfigs();
    }

    private static void setModelConfigs() {
        skillTreeConfigs();
        gameConfigs();
    }

    private static void gameConfigs() {
        Gson gson = new Gson();
        StringBuilder stringBuilder = Helper.readFile("src/controller/configs/gameConfigs.json");
        GameConfigsJsonHelper helper = gson.fromJson(stringBuilder.toString() , GameConfigsJsonHelper.class);
        Configs.GameConfigs.XP = helper.XP;
        GameState.setXp(helper.XP);
        Configs.GameConfigs.EPSILON_ACCELERATION = helper.EPSILON_ACCELERATION;
        Configs.GameConfigs.EPSILON_DECELERATION_TIME = helper.EPSILON_DECELERATION_TIME;
        Configs.GameConfigs.EPSILON_MAX_SPEED = helper.EPSILON_MAX_SPEED;
    }

    private static void skillTreeConfigs() {
        StringBuilder stringBuilder = Helper.readFile("src/controller/configs/skillTree.json");
        try {
            JSONObject jsonObject = (JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
            Configs.SkillTreeConfigs.aresBought = (boolean)jsonObject.get("ares");
            Configs.SkillTreeConfigs.astrapeBought = (boolean)jsonObject.get("astrape");
            Configs.SkillTreeConfigs.cerberusBought = (boolean)jsonObject.get("cerberus");
            Configs.SkillTreeConfigs.acesoBought = (boolean)jsonObject.get("aceso");
            Configs.SkillTreeConfigs.melampusBought = (boolean)jsonObject.get("melampus");
            Configs.SkillTreeConfigs.chironBought = (boolean)jsonObject.get("chiron");
            Configs.SkillTreeConfigs.athenaBought = (boolean)jsonObject.get("athena");
            Configs.SkillTreeConfigs.proteusBought = (boolean)jsonObject.get("proteus");
            Configs.SkillTreeConfigs.empusaBought = (boolean)jsonObject.get("empusa");
            Configs.SkillTreeConfigs.dolusBought = (boolean)jsonObject.get("dolus");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateObjectViews(){

    }


    public static void startGame(){
        gameMode = GameMode.inGame;
        GameState.reset();
        modelStarter();
        viewStarter();
        addEpsilonAndFrame();
        new GameStartAnimation(ModelData.getFrames().getFirst()).StartAnimation();
        InGameAbilityHandler.initInGameAbilities();
        SkillTreeAbilityHandler.initAbilities();
        Controller.threadsStarter();
    }

    public static void endGame(boolean won) {
        int xpGained = GameState.getXpGained();
        int enemyKilled = GameState.getEnemyKilled();
        int totalShots = GameState.getTotalBullets();
        int successfulShots = GameState.getSuccessfulBullets();
        int timePassed = (int) GameState.getTime() / 1000;
        endRequest();
        if (won) {
//            new EndGamePanel(new EndGameFrame(),xpGained ,enemyKilled ,totalShots ,successfulShots ,timePassed).start();
            return;
        }
        else {
//            new EndGamePanel(new EndGameFrame() ,xpGained ,enemyKilled ,totalShots ,successfulShots ,timePassed).start();
        }
    }

    private static void endRequest() {
        GameState.setOver(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GameState.reset();
        ModelRequests.endRequest();
//        ViewRequest.endRequest();
        Helper.resetAllJsons("src/controller/manager/saving/inGameSaved");
    }


    private static void addEpsilonAndFrame() {
        EpsilonModel epsilon = new EpsilonModel(
                new Vector(SizeConstants.SCREEN_SIZE.width / 2d ,
                        SizeConstants.SCREEN_SIZE.height / 2d
                )
                ,Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
        ModelData.addModel(epsilon);
        ModelData.setEpsilon(epsilon);
//        ViewData.addObject(new EpsilonView(epsilon.getPosition() ,epsilon.getId()));
        FrameModelBuilder builder = new FrameModelBuilder(
                new Vector(
                        SizeConstants.SCREEN_SIZE.width / 2d - SizeConstants.GAME_WIDTH / 2d ,
                        SizeConstants.SCREEN_SIZE.height / 2d - SizeConstants.GAME_HEIGHT / 2d
                ),
                new Dimension(SizeConstants.GAME_WIDTH , SizeConstants.GAME_HEIGHT),
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
        builder.setSolid(true);
        FrameModel frameModel = builder.create();
        ModelData.addFrame(frameModel);
        ModelData.setEpsilonFrame(frameModel);
//        ViewData.addFrame(new FrameView(
//                frameModel.getPosition(),
//                frameModel.getSize(),
//                frameModel.getId())
//        );
//        ViewData.addImaginaryPanel(new ImaginaryPanel(frameModel.getId()));
    }

    public static void threadsStarter() {
        frameThread = new FrameThread();
        gameLoop = new GameLoop();
        gameManager = new GameManager();
        frameThread.start();
        gameLoop.start();
        gameManager.getGameManager().start();
    }

    private static void modelStarter() {
        ModelData.resetAll();
        ModelRequests.resetAll();
    }

    private static void viewStarter() {
//        ViewData.resetAll();
//        ViewRequest.resetAll();
    }

}
