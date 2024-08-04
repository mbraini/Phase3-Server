package controller.game.listeners;


import constants.SizeConstants;
import constants.TimeConstants;
import controller.game.player.Player;
import model.objectModel.fighters.EpsilonModel;
import utils.Vector;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EpsilonAiming extends MouseAdapter {

    double timer;
    public static int AIM_CODE = 1;
    private Player player;
    public EpsilonAiming(Player player){
        this.player = player;
        timer = 0;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (player.getGame().getGameState().isInAnimation())
            return;
        if (e.getButton() != AIM_CODE) {
            return;
        }
        if (player.getGame().getGameState().isPause())
            return;
        if (player.getGame().getGameState().getTime() - timer < TimeConstants.EPSILON_SHOOTIN_TIME)
            return;
        timer = player.getGame().getGameState().getTime();
        EpsilonModel epsilon = player.getPlayerData().getEpsilon();
        Vector clickedPoint = new Vector(
                e.getX() - SizeConstants.SCREEN_SIZE.width ,
                e.getY() - SizeConstants.SCREEN_SIZE.height
        );
        if (clickedPoint.Equals(epsilon.getPosition()))
            return;

//        try {
//            Sound sound = new Sound(SoundPathConstants.BulletFiredSound);
//            sound.play();
//        } catch (UnsupportedAudioFileException ex) {
//            throw new RuntimeException(ex);
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        } catch (LineUnavailableException ex) {
//            throw new RuntimeException(ex);
//        }
    }
}
