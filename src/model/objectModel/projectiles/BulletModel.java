package model.objectModel.projectiles;

import constants.RefreshRateConstants;
import controller.gameController.ObjectController;
import model.interfaces.movementIntefaces.MoveAble;
import utils.Math;

public abstract class BulletModel extends ProjectileModel implements MoveAble {
    protected double hp = 1;

    @Override
    public void move() {
        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);
    }

    @Override
    public void die() {
        ObjectController.removeObject(this);
    }

}
