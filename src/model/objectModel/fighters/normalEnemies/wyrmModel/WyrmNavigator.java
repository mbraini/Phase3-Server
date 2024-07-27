package model.objectModel.fighters.normalEnemies.wyrmModel;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import model.ModelData;
import utils.Math;
import utils.Vector;

public class WyrmNavigator {
    private WyrmModel wyrmModel;
    public WyrmNavigator(WyrmModel wyrmModel){
        this.wyrmModel = wyrmModel;
    }

    public void navigate() {
        Vector epsilonPosition = ModelData.getModels().getFirst().getPosition();
        Vector direction = Math.VectorAdd(
                Math.ScalarInVector(-1 ,wyrmModel.getPosition()),
                epsilonPosition
        );
        wyrmModel.setVelocity(
                Math.VectorWithSize(
                        direction,
                        VelocityConstants.WYRM_NAVIGATION_VELOCITY
                )
        );
        double Moving = Math.VectorSize(wyrmModel.getVelocity()) * RefreshRateConstants.UPS;
        if (Math.VectorSize(direction) <= SizeConstants.WYRM_NAVIGATION_RADIOS + Moving){
            wyrmModel.setVelocity(Math.ScalarInVector(-1 ,wyrmModel.getVelocity()));
        }
        wyrmModel.setThetaRelativeToOrigin(direction);
    }

    public boolean hasArrived() {
        Vector epsilonPosition = ModelData.getModels().getFirst().getPosition();
        Vector distance = Math.VectorAdd(
                epsilonPosition,
                Math.ScalarInVector(-1 ,wyrmModel.getPosition())
        );
        if (java.lang.Math.abs(Math.VectorSize(distance) - SizeConstants.WYRM_NAVIGATION_RADIOS)
                <= Math.VectorSize(wyrmModel.getVelocity()) * RefreshRateConstants.UPS){
            return true;
        }
        return false;
    }
}
