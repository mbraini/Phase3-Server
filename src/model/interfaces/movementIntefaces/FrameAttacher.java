package model.interfaces.movementIntefaces;

import model.objectModel.frameModel.FrameLocations;

public interface FrameAttacher {
    FrameLocations getAttachedLocation();
    void damage();
}
