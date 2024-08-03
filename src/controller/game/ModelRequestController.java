package controller.game;

import controller.game.manager.GameState;
import model.ModelData;
import model.objectModel.ObjectModel;
import model.objectModel.PortalModel;
import model.objectModel.fighters.EpsilonModel;

import java.util.ArrayList;

public class ModelRequestController {



//    public static void randomizeKeys() {
//        KeyHelper.randomize();
//    }
//
//    public static void reorderKeys() {
//        KeyHelper.reorder();
//    }

    public static void portalWindow() {
        Controller.pause();
//        int totalPR = GameState.getAllPR();
//        int PR = (int) (totalPR * GameState.getXpGained() * 10 / GameState.getHp());
    }

    public synchronized static void killEveryThing() {
//        ArrayList<ObjectModel> models;
//        synchronized (ModelData.getModels()) {
//            models = (ArrayList<ObjectModel>) ModelData.getModels().clone();
//        }
//        for (ObjectModel model : models) {
//            if (!(model instanceof EpsilonModel))
//                model.die();
//        }
    }

    public static void playSound(String path) {
//        Sound sound = null;
//        try {
//            sound = new Sound(path);
//        } catch (UnsupportedAudioFileException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }
//        sound.play();
    }

    public void reorderKeys() {

    }

    public void randomizeKeys() {

    }
}
