package model.animations;

import model.objectModel.frameModel.FrameModel;

public class GameStartAnimation extends TimerAnimation {
    private FrameAnimation frameAnimation;
    private FrameModel frame;
    public GameStartAnimation(FrameModel frame){
        this.frame = frame;
    }

    @Override
    public void StartAnimation(){
        frameAnimation = new FrameAnimation(frame ,-250 ,-250 ,-250 ,-250 ,1000);
        frameAnimation.StartAnimation();
    }
}