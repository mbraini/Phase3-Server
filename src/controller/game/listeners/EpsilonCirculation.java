package controller.game.listeners;


import constants.SizeConstants;
import controller.game.Player;
import controller.game.manager.GameState;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class EpsilonCirculation extends MouseMotionAdapter {
    private EpsilonModel epsilon;
    private Player player;
    public EpsilonCirculation(Player player){
        this.player = player;
        this.epsilon = player.getPlayerData().getEpsilon();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (player.getGame().getGameState().isInAnimation())
            return;
        Vector mousePosition = new Vector(e.getX() ,e.getY());
        mousePosition = Math.VectorAdd(
                mousePosition,
                new Vector(
                        -SizeConstants.SCREEN_SIZE.width,
                        -SizeConstants.SCREEN_SIZE.height
                )
        );
        Vector epsilonPosition = epsilon.getPosition();
        if (mousePosition.Equals(epsilonPosition))
            return;
        Vector direction = Math.VectorAdd(Math.ScalarInVector(-1 ,epsilonPosition) ,mousePosition);
        double cosTheta = Math.DotProduct(direction ,new Vector(1 ,0)) / (Math.VectorSize(direction));
        double theta = java.lang.Math.acos(cosTheta);
        if (direction.getY() <= 0){
            theta = -theta;
        }
        epsilon.Rotate(-theta);
    }
}
