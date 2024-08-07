package model.objectModel.fighters.normalEnemies.necropickModel;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import utils.Math;
import utils.Vector;

public class NecropickNavigator {
    private Vector position;
    private Vector epsilonPosition;
    private Vector velocity;
    private NecropickModel necropickModel;

    public NecropickNavigator(NecropickModel necropickModel ,Vector epsilonPosition){
        this.necropickModel = necropickModel;
        this.position = necropickModel.getPosition();
        this.epsilonPosition = epsilonPosition;
    }

    public void navigate() {
        ///////////// prevents from jiggling :)
        double velocityD = VelocityConstants.NECROPCIK_NAVIGATION_VELOCITY * necropickModel.getGame().getGameSpeed();
        if (java.lang.Math.abs(Math.VectorSize(
                Math.VectorAdd(position ,Math.ScalarInVector(-1 ,epsilonPosition))
        ) - SizeConstants.NECROPICK_SPAWN_RADIOS) <= velocityD * RefreshRateConstants.UPS){
            velocity = new Vector();
            return;
        }
        ////////////
        Vector direction = Math.VectorAdd(
                Math.ScalarInVector(-1 ,position),
                epsilonPosition
        );
        if (Math.VectorSize(direction) <= SizeConstants.NECROPICK_SPAWN_RADIOS){
            direction = Math.ScalarInVector(
                    -1,
                    direction
            );
            velocity = Math.VectorAdd(
                    position,
                    Math.ScalarInVector(-1 ,direction)
            );
        }
        velocity = Math.VectorWithSize(direction , velocityD);
    }

    public Vector getVelocity(){
        return velocity;
    }

}
