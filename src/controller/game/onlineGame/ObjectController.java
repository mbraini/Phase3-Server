package controller.game;

import constants.SoundPathConstants;
import model.ModelRequests;
import model.objectModel.ObjectModel;
import model.objectModel.effects.EffectModel;
import model.objectModel.fighters.EnemyModel;
import model.objectModel.frameModel.FrameModel;

public class ObjectController {

    public synchronized static void removeObject(ObjectModel model) {
        model.getGame().getModelRequests().removeObjectModel(model.getId());
    }

    public synchronized static void removeEffect(EffectModel effectModel) {
        effectModel.getGame().getModelRequests().removeEffectModel(effectModel.getId());
    }

    public static void removeFrame(FrameModel frameModel) {
        frameModel.getGame().getModelRequests().removeFrameModel(frameModel.getId());
    }
}
