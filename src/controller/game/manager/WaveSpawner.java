package controller.game.manager;

import constants.ControllerConstants;
import constants.SizeConstants;
import constants.TimeConstants;
import controller.game.Game;
import controller.game.GameType;
import controller.game.player.Player;
import model.objectModel.ObjectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.fighters.EnemyModel;
import model.objectModel.fighters.basicEnemies.BasicEnemyModel;
import model.objectModel.fighters.basicEnemies.SquarantineModel;
import model.objectModel.fighters.basicEnemies.TrigorathModel;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.miniBossEnemies.MiniBossModel;
import model.objectModel.fighters.miniBossEnemies.barricadosModel.BarricadosFirstModel;
import model.objectModel.fighters.miniBossEnemies.barricadosModel.BarricadosSecondModel;
import model.objectModel.fighters.miniBossEnemies.blackOrbModel.BlackOrbModel;
import model.objectModel.fighters.normalEnemies.NormalEnemyModel;
import model.objectModel.fighters.normalEnemies.archmireModel.ArchmireModel;
import model.objectModel.fighters.normalEnemies.necropickModel.NecropickModel;
import model.objectModel.fighters.normalEnemies.omenoctModel.OmenoctModel;
import model.objectModel.fighters.normalEnemies.wyrmModel.WyrmModel;
import model.objectModel.frameModel.FrameModel;
import utils.Helper;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WaveSpawner {

    private Timer spawner;
    private FrameModel epsilonFrame;
    private final ArrayList<Class<?>> enemies = new ArrayList<>(Arrays.asList(
            SquarantineModel.class,
            TrigorathModel.class,
            OmenoctModel.class,
            NecropickModel.class,
            ArchmireModel.class,
            WyrmModel.class,
            BlackOrbModel.class,
            BarricadosFirstModel.class,
            BarricadosSecondModel.class,
            Boss.class
    ));
    private final ArrayList<Class<?>> spaceEnemies = new ArrayList<>(Arrays.asList(
            BarricadosFirstModel.class,
            BarricadosSecondModel.class
    ));
    private static final int BASICS_ENDING_INDEX = 1;
    private static final int NORMALS_ENDING_INDEX = 5;
    private static final int MINI_ENDING_INDEX = 8;
    private final Random random;
    private int maximumMiniBossCount;
    private int maximumNormalEnemyCount;
    private int maximumBasicEnemyCount;
    private boolean isBasicEnemyUnlocked = true;
    private boolean isNormalEnemyUnlocked;
    private boolean isMiniBossUnlocked;
    private int basicEnemyCount;
    private int normalEnemyCount;
    private int miniBossEnemyCount;
    private int basicEnemySpawnCount = 1;
    private int normalEnemySpawnCount = 1;
    private int miniBossEnemySpawnCount;
    private int enemyKilled;
    private int enemySpawned;
    private boolean canSpawn = true;
    private int repeatedCount;
    private int miniBossSpawnedAtCount = -8;
    private int chasingTurn;
    private Game game;

    public WaveSpawner(Game game) {
        this.game = game;
        random = new Random();
        synchronized (game.getModelData().getFrames()) {
            epsilonFrame = game.getModelData().getFrames().getFirst();
        }
        int delay;
        if (game.getGameType().equals(GameType.monomachia)) {
            delay = TimeConstants.INITIAL_MONOMACHIA_SPAWNER_DELAY;
            maximumMiniBossCount = 1;
            maximumNormalEnemyCount = 4;
            maximumBasicEnemyCount = 6;
        }
        else {
            delay = TimeConstants.INITIAL_COLOSSEUM_SPAWNER_DELAY;
            maximumMiniBossCount = 6;
            maximumNormalEnemyCount = 16;
            maximumBasicEnemyCount = 30;
        }

        spawner = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getGameState().isPause() || game.getGameState().isDizzy() || game.getGameState().isOver())
                    return;
                getEnemiesCounts();
                enemyKilled = game.getGameState().getEnemyKilled();
                updateVariables();
                spawnEnemies();
                repeatedCount++;
            }

        });

    }

    private void updateVariables() {
        if (game.getGameType().equals(GameType.monomachia)) {
            updateMonomachiaVariables();
        }
        else {
            updateColosseumVariables();
        }
        unlockEnemies();
    }

    private void unlockEnemies() {
        if (game.getGameState().getWave() >= 2) {
            isNormalEnemyUnlocked = true;
        }
        if (game.getGameState().getWave() >= 3) {
            isMiniBossUnlocked = true;
        }
    }

    private void updateColosseumVariables() {

        if (game.getGameState().getWave() == 1) {
            spawner.setDelay(3000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 1;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 20;
            maximumNormalEnemyCount = 4;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 2) {
            spawner.setDelay(3000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 2;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 22;
            maximumNormalEnemyCount = 5;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 3) {
            spawner.setDelay(2000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 1;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 24;
            maximumNormalEnemyCount = 6;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 4) {
            spawner.setDelay(2000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 1;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 26;
            maximumNormalEnemyCount = 7;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 5) {
            spawner.setDelay(1000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 2;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 30;
            maximumNormalEnemyCount = 8;
            maximumMiniBossCount = 1;
        }
        checkColosseumNextWave();
        allowColosseumSpawnCheck();

    }

    private void allowColosseumSpawnCheck() {
        if (enemySpawned >= 30 && game.getGameState().getWave() == 1) {
            canSpawn = false;
        }
        if (enemySpawned >= 50 && game.getGameState().getWave() == 2) {
            canSpawn = false;
        }
        if (enemySpawned >= 80 && game.getGameState().getWave() == 3) {
            canSpawn = false;
        }
        if (enemySpawned >= 110 && game.getGameState().getWave() == 4) {
            canSpawn = false;
        }
        if (enemySpawned >= 140 && game.getGameState().getWave() == 5) {
            canSpawn = false;
        }
    }

    private void checkColosseumNextWave() {
        if (enemyKilled >= 30 && game.getGameState().getWave() == 1) {
            game.getGameState().setWave(2);
            canSpawn = true;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 50 && game.getGameState().getWave() == 2) {
            game.getGameState().setWave(3);
            canSpawn = true;
            repeatedCount =0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 80 && game.getGameState().getWave() == 3) {
            game.getGameState().setWave(4);
            canSpawn = true;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 110 && game.getGameState().getWave() == 4) {
            game.getGameState().setWave(5);
            canSpawn = true;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 140 && game.getGameState().getWave() == 5) {
            game.getGameState().setWave(6);
            canSpawn = false;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
            Spawner.spawnBoss(
                    game,
                    getChasingPlayer(),
                    getTargetedPlayers()
            );
        }
    }

    private void updateMonomachiaVariables() {
        if (game.getGameState().getWave() == 1) {
            spawner.setDelay(4000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 2;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 10;
            maximumNormalEnemyCount = 4;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 2) {
            spawner.setDelay(4000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 2;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 14;
            maximumNormalEnemyCount = 5;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 3) {
            spawner.setDelay(3000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 2;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 18;
            maximumNormalEnemyCount = 6;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 4) {
            spawner.setDelay(3000);

            basicEnemySpawnCount = 2;
            normalEnemySpawnCount = 2;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 20;
            maximumNormalEnemyCount = 7;
            maximumMiniBossCount = 1;
        }
        if (game.getGameState().getWave() == 5) {
            spawner.setDelay(2000);

            basicEnemySpawnCount = 4;
            normalEnemySpawnCount = 2;
            miniBossEnemySpawnCount = 1;

            maximumBasicEnemyCount = 24;
            maximumNormalEnemyCount = 8;
            maximumMiniBossCount = 1;
        }
        checkMonomachiaNextWave();
        allowMonomachiaSpawnCheck();
    }

    private void allowMonomachiaSpawnCheck() {
        if (enemySpawned >= 20 && game.getGameState().getWave() == 1) {
            canSpawn = false;
        }
        if (enemySpawned >= 45 && game.getGameState().getWave() == 2) {
            canSpawn = false;
        }
        if (enemySpawned >= 70 && game.getGameState().getWave() == 3) {
            canSpawn = false;
        }
        if (enemySpawned >= 100 && game.getGameState().getWave() == 4) {
            canSpawn = false;
        }
        if (enemySpawned >= 130 && game.getGameState().getWave() == 5) {
            canSpawn = false;
        }

    }

    private void checkMonomachiaNextWave() {
        if (enemyKilled >= 20 && game.getGameState().getWave() == 1) {
            game.getGameState().setWave(2);
            canSpawn = true;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 45 && game.getGameState().getWave() == 2) {
            game.getGameState().setWave(3);
            canSpawn = true;
            repeatedCount =0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 70 && game.getGameState().getWave() == 3) {
            game.getGameState().setWave(4);
            canSpawn = true;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 100 && game.getGameState().getWave() == 4) {
            game.getGameState().setWave(5);
            canSpawn = true;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
        }
        if (enemyKilled >= 130 && game.getGameState().getWave() == 5) {
            game.getGameState().setWave(6);
            canSpawn = true;
            repeatedCount = 0;
            miniBossSpawnedAtCount = -8;
        }
    }


    private void spawnEnemies() {
        if (basicEnemyCount < maximumBasicEnemyCount && isBasicEnemyUnlocked) {
            spawnBasicEnemy();
        }
        if (normalEnemyCount < maximumNormalEnemyCount && isNormalEnemyUnlocked) {
            spawnNormalEnemy();
        }
        if (miniBossEnemyCount < maximumMiniBossCount && isMiniBossUnlocked && repeatedCount - miniBossSpawnedAtCount > 8) {
            spawnMiniBoss();
        }
    }

    private void getEnemiesCounts() {
        basicEnemyCount = 0;
        normalEnemyCount = 0;
        miniBossEnemyCount = 0;
        ArrayList<ObjectModel> models;
        synchronized (game.getModelData().getModels()) {
            models = (ArrayList<ObjectModel>) game.getModelData().getModels().clone();
        }
        for (ObjectModel model : models) {
            if (model instanceof BasicEnemyModel)
                basicEnemyCount++;
            if (model instanceof NormalEnemyModel)
                normalEnemyCount++;
            if (model instanceof MiniBossModel)
                miniBossEnemyCount++;
        }

    }


    private void spawnBasicEnemy() {
        for (int i = 0 ;i < basicEnemySpawnCount ;i++) {
            int index = random.nextInt(0 ,BASICS_ENDING_INDEX + 1);
            spawnEnemy(index);
        }
    }

    private void spawnNormalEnemy() {
        for (int i = 0 ;i < normalEnemySpawnCount ;i++) {
            int index = random.nextInt(BASICS_ENDING_INDEX + 1 ,NORMALS_ENDING_INDEX + 1);
            spawnEnemy(index);
        }
    }

    private void spawnMiniBoss() {
        for (int i = 0 ;i < miniBossEnemySpawnCount ;i++) {
            int index = random.nextInt(NORMALS_ENDING_INDEX + 1 ,MINI_ENDING_INDEX + 1);
            spawnEnemy(index);
            enemySpawned--;
            miniBossSpawnedAtCount = repeatedCount;
        }
    }

    private ArrayList<Player> getTargetedPlayers() {
        ArrayList<Player> targetedPlayers = new ArrayList<>();
        synchronized (game.getPlayers()) {
            if (game.getGameType().equals(GameType.monomachia)) {
                targetedPlayers.add(game.getPlayers().get(chasingTurn));
            }
            else {
                targetedPlayers = game.getPlayers();
            }
            return targetedPlayers;
        }
    }

    private Player getChasingPlayer() {
        Player player;
        chasingTurn++;
        if (chasingTurn >= game.getPlayers().size()) {
            chasingTurn = 0;
        }
        synchronized (game.getPlayers()) {
            player = game.getPlayers().get(chasingTurn);
        }
        if (player.isDead() && game.getGameState().isOver())
            player = getChasingPlayer();
        return player;
    }

    private void spawnEnemy(int index) {
        if (!canSpawn)
            return;
        enemySpawned++;
        String id = Helper.RandomStringGenerator(ControllerConstants.ID_SIZE);
        Vector position;
        if (spaceEnemies.contains(enemies.get(index))) {
            position = Helper.createRandomPositionSeparately(game ,epsilonFrame , SizeConstants.BARRICADOS_DIMENSION);
        }
        else {
            position = Helper.createRandomPosition(
                    epsilonFrame,
                    false
            );
        }
        Player chasingPlayer = getChasingPlayer();
        ArrayList<Player> targetedPlayers = getTargetedPlayers();
        try {
            Object enemyModel = enemies.get(index).getConstructors()[0].newInstance(
                    game ,chasingPlayer ,targetedPlayers ,position, id
            );
            if (enemyModel instanceof AbstractEnemy) {
                game.getModelRequests().addAbstractEnemy((AbstractEnemy) enemyModel);
            }
            else {
                game.getModelRequests().addObjectModel((EnemyModel)enemyModel);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public Timer getSpawner() {
        return spawner;
    }

    public void setSpawner(Timer spawner) {
        this.spawner = spawner;
    }
}
