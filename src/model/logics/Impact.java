package model.logics;


import constants.TimeConstants;
import model.ModelData;
import model.interfaces.movementIntefaces.ImpactAble;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;

public class Impact {
    private final Vector collisionPoint;
    private double distance;
    private int time;
    private boolean sameForce;

    public Impact(Vector collisionPoint ,double distance){
        this.collisionPoint = collisionPoint;
        this.distance = distance;
        time = TimeConstants.REGULAR_IMPACT_TIME;
    }


    public Impact(Vector collisionPoint ,double distance ,int time){
        this.collisionPoint = collisionPoint;
        this.distance = distance;
        this.time = time;
    }

    public Impact(Vector collisionPoint ,double distance ,int time ,boolean sameForce){
        this.collisionPoint = collisionPoint;
        this.distance = distance;
        this.time = time;
        this.sameForce = sameForce;
    }

    public void MakeImpact(){
        double distance;
        for (int i = 0; i < ModelData.getModels().size() ; i++){
            if (ModelData.getModels().get(i) instanceof ImpactAble) {
                Vector direction;
                direction = Math.VectorAdd(Math.ScalarInVector(-1, collisionPoint), ModelData.getModels().get(i).getPosition());
                distance = Math.VectorSize(direction);
                //////////////////todo
                if (distance >= this.distance) {
                    continue;
                }
                //////////////////todo
                if (distance == 0)
                    continue;
                double dashDistance;
                if (sameForce) {
                    dashDistance = this.distance;
                }
                else {
                    dashDistance = this.distance - distance;
                }
                if (!(ModelData.getModels().get(i) instanceof EpsilonModel)) {
                    new Dash(
                            ModelData.getModels().get(i),
                            direction,
                            time,
                            dashDistance,
                            dashDistance / 100 * java.lang.Math.PI,
                            false
                    ).startDash();
                }
                else {
                    if (sameForce) {
                        new Dash(
                                ModelData.getModels().get(i),
                                direction,
                                time,
                                dashDistance,
                                0,
                                false
                        ).startDash();
                    }
                    else {
                        new Dash(
                                ModelData.getModels().get(i),
                                direction,
                                time,
                                100,
                                0,
                                false
                        ).startDash();
                    }
                }
            }
        }
    }

}
