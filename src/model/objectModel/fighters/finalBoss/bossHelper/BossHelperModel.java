package model.objectModel.fighters.finalBoss.bossHelper;

import constants.RefreshRateConstants;
import controller.game.ObjectController;
import controller.game.interfaces.ImageChanger;
import controller.game.interfaces.SizeChanger;
import controller.game.manager.GameState;
import controller.online.annotations.SkippedByJson;
import model.interfaces.FrameSticker;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.movementIntefaces.MoveAble;
import model.objectModel.fighters.EnemyModel;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

import java.awt.*;

public abstract class BossHelperModel extends EnemyModel implements ImageChanger , MoveAble , FrameSticker , SizeChanger {
    protected FrameModel frame;
    @SkippedByJson
    protected Image image;
    protected Dimension size;
    protected boolean isInUse = true;
    protected boolean isDead;
    protected boolean dashedInPositiveWay;
    private boolean inDash;

    protected abstract void initFrame();

    @Override
    public void meleeAttack(EpsilonModel epsilon) {
        epsilon.setHP(epsilon.getHP() - meleeAttack);
    }

    public FrameModel getFrame() {
        return frame;
    }

    public void setFrame(FrameModel frame) {
        this.frame = frame;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isInUse() {
        return isInUse;
    }

    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean dashedInPositiveWay() {
        return dashedInPositiveWay;
    }

    public void setDashedInPositiveWay(boolean dashedInPositiveWay) {
        this.dashedInPositiveWay = dashedInPositiveWay;
    }

    @Override
    public void move() {
        if (game.getGameState().isDizzy())
            return;

        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        movementManager.manage(RefreshRateConstants.UPS ,this);
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);


        omega += alpha * RefreshRateConstants.UPS;
        double thetaMoved = ((2 * omega - alpha * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        theta = theta + thetaMoved;
        if (this instanceof HasVertices)
            ((HasVertices)this).UpdateVertices(xMoved ,yMoved ,thetaMoved);
    }

    @Override
    public void setStuckFramePosition() {
        frame.setSize(size);
        frame.transfer(Math.VectorAdd(
                position,
                new Vector(
                        -size.width / 2d,
                        -size.height / 2d
                )
        ));
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void die() {
        ObjectController.removeObject(this);
        ObjectController.removeFrame(frame);
        setDead(true);
    }

    public boolean isInDash() {
        return inDash;
    }

    public void setInDash(boolean inDash) {
        this.inDash = inDash;
    }
}
