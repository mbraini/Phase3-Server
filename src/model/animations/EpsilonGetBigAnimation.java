package model.animations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EpsilonGetBigAnimation extends TimerAnimation {

    private Timer timer;
    private int delay = 10;

    public EpsilonGetBigAnimation() {
        setUpTimer();
    }

    private void setUpTimer() {
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (ModelData.getEpsilon().getSize().width >= ModelData.getEpsilonFrame().getSize().width
//                        || ModelData.getEpsilon().getSize().height >= ModelData.getEpsilonFrame().getSize().height)
//                {
//                    Controller.endGame(true);
//                    ModelRequestController.playSound(SoundPathConstants.endSound);
//                    timer.stop();
//                }
//                ModelData.getEpsilon().setSize(
//                        new Dimension(
//                                ModelData.getEpsilon().getSize().width + SizeConstants.EPSILON_DIMENSION.width / 10,
//                                ModelData.getEpsilon().getSize().height + SizeConstants.EPSILON_DIMENSION.height / 10
//                        )
//                );
            }
        });
    }

    @Override
    public void StartAnimation() {
//        GameState.setIsInAnimation(true);
        timer.start();
    }
}
