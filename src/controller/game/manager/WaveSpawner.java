package controller.game.manager;

import constants.SizeConstants;
import constants.SoundPathConstants;
import controller.game.Game;
import controller.game.ModelRequestController;
import controller.game.enums.ModelType;
import model.ModelData;
import model.objectModel.frameModel.FrameModel;
import utils.Helper;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaveSpawner {

    private Timer spawner;
    private FrameModel epsilonFrame;
    private int timePassed;
    private int spawnDelay;
    public static int repeatedCount;
    private int lastWaveKilled;
    private Game game;

    public WaveSpawner(Game game) {
        this.game = game;
        spawnDelay = 3000;
        spawner = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getGameState().isPause() || game.getGameState().isDizzy())
                    return;
                if (game.getGameState().isOver()) {
                    spawner.stop();
                    return;
                }
                timePassed += 1000;
                if (timePassed >= spawnDelay) {
                    timePassed = 0;
                    int enemyKilled = game.getGameState().getEnemyKilled();
                    int wave = game.getGameState().getWave();
//                    epsilonFrame = game.getModelData().getEpsilonFrame();
                    spawnEnemies(enemyKilled ,wave);
                }
            }
        });
        spawner.start();
    }

    private void spawnEnemies(int enemyKilled ,int wave) {

        switch (wave) {
            case 1 :
//                firstWave(enemyKilled);
                break;
            case 2 :
//                secondWave(enemyKilled);
                break;
            case 3 :
//                thirdWave(enemyKilled);
                break;
            case 4 :
//                forthWave(enemyKilled);
                break;
            case 5 :
//                fifthWave(enemyKilled);
                break;
            case 6 :
                sixthWave();
                spawner.stop();
                break;
        }
    }
//
//    private void firstWave(int enemyKilled) {
//        repeatedCount++;
//        spawnDelay = 5000;
//        if (repeatedCount == 1) {
//            ModelRequestController.playSound(SoundPathConstants.waveSpawnSound);
//            Spawner.spawnPortal(
//                    new Vector(
//                            epsilonFrame.getPosition().x + epsilonFrame.getSize().width / 2d,
//                            epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
//                    ),
//                    game.getModelData().getEpsilonFrame()
//            );
//        }
//        if (enemyKilled >= 0 && enemyKilled <= 5) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.trigorath);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.squarantine);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 2);
//        }
//        if (enemyKilled > 5  && GameState.getEnemyCount() == 0) {
//            GameState.setWave(2);
//            lastWaveKilled = GameState.getEnemyKilled();
//            repeatedCount = 0;
//            spawnDelay = 4000;
//            GameState.setLastWaveTime(GameState.getTime());
//        }
//    }
//
//    private void secondWave(int enemyKilled) {
//        repeatedCount++;
//        if (repeatedCount == 1) {
//            ModelRequestController.playSound(SoundPathConstants.waveSpawnSound);
//            Spawner.spawnPortal(
//                    new Vector(
//                            epsilonFrame.getPosition().x + epsilonFrame.getSize().width / 2d,
//                            epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
//                    ),
//                    ModelData.getEpsilonFrame()
//            );
//        }
//        if (enemyKilled >= lastWaveKilled && enemyKilled <= lastWaveKilled + 2) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.trigorath);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.squarantine);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 2);
//        }
//
//        if (enemyKilled > lastWaveKilled + 2 && enemyKilled <= lastWaveKilled + 4) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.omenoct);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.necropick);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 2);
//        }
//
//        if (enemyKilled > lastWaveKilled + 6 && GameState.getWave() == 2 && GameState.getEnemyCount() == 0) {
//            GameState.setWave(3);
//            repeatedCount = 0;
//            lastWaveKilled = GameState.getEnemyKilled();
//            GameState.setLastWaveTime(GameState.getTime());
//        }
//    }
//
//    private void thirdWave(int enemyKilled) {
//        repeatedCount++;
//        if (enemyKilled >= lastWaveKilled && enemyKilled <= lastWaveKilled + 1) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.wyrm);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 1);
//        }
//        if (repeatedCount == 1) {
//            ModelRequestController.playSound(SoundPathConstants.waveSpawnSound);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.archmire);
//            Spawner.spawnPortal(
//                    new Vector(
//                            epsilonFrame.getPosition().x + epsilonFrame.getSize().width / 2d,
//                            epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
//                    ),
//                    ModelData.getEpsilonFrame()
//            );
//            GameState.setEnemyCount(GameState.getEnemyCount() + 1);
//        }
//
//        if (enemyKilled > lastWaveKilled + 1 && enemyKilled <= lastWaveKilled + 4 ) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.trigorath);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 1);
//        }
//
//        if (enemyKilled > lastWaveKilled + 4 && GameState.getWave() == 3 && GameState.getEnemyCount() == 0) {
//            GameState.setWave(4);
//            repeatedCount = 0;
//            lastWaveKilled = GameState.getEnemyKilled();
//            GameState.setLastWaveTime(GameState.getTime());
//        }
//    }
//
//    private void forthWave(int enemyKilled) {
//        repeatedCount++;
//        if (repeatedCount == 1) {
//            ModelRequestController.playSound(SoundPathConstants.waveSpawnSound);
//            Spawner.spawnObject(
//                    new Vector(
//                            SizeConstants.SCREEN_SIZE.width / 2d,
//                            SizeConstants.SCREEN_SIZE.height / 2d
//                    ),
//                    ModelType.blackOrb
//            );
//            Spawner.spawnPortal(
//                    new Vector(
//                            epsilonFrame.getPosition().x + epsilonFrame.getSize().width / 2d,
//                            epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
//                    ),
//                    ModelData.getEpsilonFrame()
//            );
//            GameState.setEnemyCount(GameState.getEnemyCount() + 5);
//        }
//
//        if (enemyKilled >= lastWaveKilled && enemyKilled < lastWaveKilled + 2) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.trigorath);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 1);
//        }
//        if (enemyKilled > lastWaveKilled + 2 && enemyKilled < lastWaveKilled + 4) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.omenoct);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.necropick);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.wyrm);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 3);
//        }
//        if (repeatedCount == 2) {
//            Spawner.spawnObject(
//                    Helper.createRandomPositionSeparately(
//                            epsilonFrame ,
//                            SizeConstants.BARRICADOS_DIMENSION
//                    ),
//                    ModelType.barricados
//            );
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.archmire);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 2);
//        }
//        if (enemyKilled > lastWaveKilled + 5 && GameState.getWave() == 4 && GameState.getEnemyCount() <= 1) {
//            GameState.setWave(5);
//            repeatedCount = 0;
//            lastWaveKilled = GameState.getEnemyKilled();
//            GameState.setLastWaveTime(GameState.getTime());
//        }
//    }
//
//    private void fifthWave(int enemyKilled) {
//        repeatedCount++;
//        if (repeatedCount == 1) {
//            ModelRequestController.playSound(SoundPathConstants.waveSpawnSound);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.archmire);
//            Spawner.spawnPortal(
//                    new Vector(
//                            epsilonFrame.getPosition().x + epsilonFrame.getSize().width / 2d,
//                            epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
//                    ),
//                    ModelData.getEpsilonFrame()
//            );
//            GameState.setEnemyCount(GameState.getEnemyCount() + 1);
//        }
//
//        if (repeatedCount == 2) {
//            Spawner.spawnObject(
//                    Helper.createRandomPositionSeparately(
//                        epsilonFrame ,
//                        SizeConstants.BARRICADOS_DIMENSION
//                    ),
//                    ModelType.barricados
//            );
//            GameState.setEnemyCount(GameState.getEnemyCount() + 1);
//        }
//
//        if (repeatedCount == 3) {
//            Spawner.spawnObject(
//                    new Vector(
//                            SizeConstants.SCREEN_SIZE.width / 2d,
//                            SizeConstants.SCREEN_SIZE.height / 2d
//                    )
//                    ,ModelType.blackOrb
//            );
//            GameState.setEnemyCount(GameState.getEnemyCount() + 5);
//        }
//
//        if (enemyKilled >= lastWaveKilled && enemyKilled < lastWaveKilled + 4) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.squarantine);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.trigorath);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 2);
//        }
//
//        if (enemyKilled >= lastWaveKilled + 4 && enemyKilled < lastWaveKilled + 6) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.omenoct);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.necropick);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.wyrm);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 3);
//        }
//
//        if (enemyKilled >= lastWaveKilled + 7 && enemyKilled < lastWaveKilled +10) {
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.trigorath);
//            Spawner.spawnObject(Helper.createRandomPosition(epsilonFrame ,false) ,ModelType.squarantine);
//            GameState.setEnemyCount(GameState.getEnemyCount() + 2);
//        }
//
//
//        if (enemyKilled > lastWaveKilled + 5 && GameState.getWave() == 5 && GameState.getEnemyCount() <= 2) {
//            GameState.setWave(6);
//            repeatedCount = 0;
//            lastWaveKilled = GameState.getEnemyKilled();
//            GameState.setLastWaveTime(GameState.getTime());
//        }

//    }

    private void sixthWave() {
        repeatedCount++;
        if (repeatedCount == 1) {
            ModelRequestController.playSound(SoundPathConstants.waveSpawnSound);
            Spawner.spawnBoss(game);
        }
    }

    public Timer getSpawner() {
        return spawner;
    }

    public void setSpawner(Timer spawner) {
        this.spawner = spawner;
    }
}
