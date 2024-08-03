package model.animations;

import model.logics.FrameHitPerformer;
import model.objectModel.frameModel.FrameModel;

public class GameStartAnimation extends TimerAnimation {
    private FrameHitPerformer frameHitPerformer;
    private FrameModel frame;
    public GameStartAnimation(FrameModel frame){
        this.frame = frame;
    }

    @Override
    public void StartAnimation(){
        frameHitPerformer = new FrameHitPerformer(frame ,-250 ,-250 ,-250 ,-250 ,1000);
        frameHitPerformer.frameHit();
    }
}