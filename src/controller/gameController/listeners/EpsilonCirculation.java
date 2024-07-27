package controller.gameController.listeners;


import constants.SizeConstants;
import controller.gameController.manager.GameState;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class EpsilonCirculation extends MouseMotionAdapter {
    private EpsilonModel epsilon;
    public EpsilonCirculation(){
        this.epsilon = ModelData.getEpsilon();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (GameState.isInAnimation())
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
