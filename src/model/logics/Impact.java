package model.logics;


import constants.TimeConstants;
import controller.game.Game;
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
    private Game game;

    public Impact(Game game ,Vector collisionPoint ,double distance){
        this.game = game;
        this.collisionPoint = collisionPoint;
        this.distance = distance;
        time = TimeConstants.REGULAR_IMPACT_TIME;
    }


    public Impact(Game game ,Vector collisionPoint ,double distance ,int time){
        this.game = game;
        this.collisionPoint = collisionPoint;
        this.distance = distance;
        this.time = time;
    }

    public Impact(Game game, Vector collisionPoint , double distance , int time , boolean sameForce){
        this.game = game;
        this.collisionPoint = collisionPoint;
        this.distance = distance;
        this.time = time;
        this.sameForce = sameForce;
    }

    public void MakeImpact(){
        double distance;
        for (int i = 0; i < game.getModelData().getModels().size() ; i++){
            if (game.getModelData().getModels().get(i) instanceof ImpactAble) {
                Vector direction;
                direction = Math.VectorAdd(Math.ScalarInVector(-1, collisionPoint), game.getModelData().getModels().get(i).getPosition());
                distance = Math.VectorSize(direction);
                if (distance >= this.distance) {
                    continue;
                }
                if (distance == 0)
                    continue;
                double dashDistance;
                if (sameForce) {
                    dashDistance = this.distance;
                }
                else {
                    dashDistance = this.distance - distance;
                }
                if (!(game.getModelData().getModels().get(i) instanceof EpsilonModel)) {
                    new Dash(
                            game.getModelData().getModels().get(i),
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
                                game.getModelData().getModels().get(i),
                                direction,
                                time,
                                dashDistance,
                                0,
                                false
                        ).startDash();
                    }
                    else {
                        new Dash(
                                game.getModelData().getModels().get(i),
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
