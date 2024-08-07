package controller.game.listeners;


import constants.SizeConstants;
import controller.game.player.Player;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class EpsilonCirculation {
    private EpsilonModel epsilon;
    private Vector mousePosition;
    private Player player;
    public EpsilonCirculation(Player player ,Vector mousePosition){
        this.player = player;
        this.mousePosition = mousePosition;
        this.epsilon = player.getPlayerData().getEpsilon();
    }

    public void rotate() {
        if (player == null)
            return;
        if (player.getPlayerData().getEpsilon() == null)
            return;
        if (player.getGame().getGameState().isInAnimation() || player.getGame().getGameState().isPause())
            return;
        if (player.isDead())
            return;
        Vector epsilonPosition = player.getPlayerData().getEpsilon().getPosition();
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
