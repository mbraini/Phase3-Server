package model.animations;


import model.objectModel.frameModel.FrameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameVanishAnimation extends TimerAnimation {
    FrameModel gameFrame;
    Timer timer;
    public FrameVanishAnimation(FrameModel gameFrame){
        this.gameFrame = gameFrame;
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setSize(new Dimension(
                        gameFrame.getSize().width - 3 ,
                        gameFrame.getSize().height - 3)
                );
                if (gameFrame.getSize().width <= 140 && gameFrame.getSize().height <= 40){
//                    Controller.EndTheGame();
                    timer.stop();
                }
            }
        });
    }
    @Override
    public void StartAnimation() {
        timer.start();
    }
}
