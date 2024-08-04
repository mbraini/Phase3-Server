package model.objectModel.fighters.finalBoss.bossHelper;

import constants.ImageConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.online.annotations.SkippedByJson;
import model.animations.BossDeathAnimation;
import model.interfaces.collisionInterfaces.CollisionDetector;
import model.interfaces.collisionInterfaces.IsCircle;
import model.logics.MovementManager;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.frameModel.FrameModelBuilder;
import utils.Math;
import utils.Vector;

import java.awt.*;

public class HeadModel extends BossHelperModel implements IsCircle , CollisionDetector {
    private boolean isInPositiveDirection;
    @SkippedByJson
    private Boss boss;

    public HeadModel(Game game ,Vector position , Boss boss, String id){
        super(game);
        this.position = position;
        this.id = id;
        this.image = ImageConstants.smiley;
        this.velocity = new Vector();
        this.acceleration = new Vector();
        movementManager = new MovementManager();
        this.boss = boss;
        size = new Dimension(
                SizeConstants.HEAD_DIMENSION.width,
                SizeConstants.HEAD_DIMENSION.height
        );
        type = ModelType.head;
        HP = 300;
        setHovering(true);
        initFrame();
    }

    public boolean isInPositiveDirection() {
        return isInPositiveDirection;
    }

    public void setInPositiveDirection(boolean inPositiveDirection) {
        isInPositiveDirection = inPositiveDirection;
    }


    @Override
    protected void initFrame() {
        FrameModelBuilder builder = new FrameModelBuilder(
                game,
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.HEAD_DIMENSION.width / 2d,
                                -SizeConstants.HEAD_DIMENSION.height / 2d
                        )
                ),
                new Dimension(SizeConstants.HEAD_DIMENSION),
                id
        );
        builder.setIsometric(true);
        frame = builder.create();
    }


    @Override
    public void die() {
        new BossDeathAnimation(boss).StartAnimation();
    }

    @Override
    public double getRadios() {
        return SizeConstants.HEAD_DIMENSION.width / 2d;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

    @Override
    public void detect() {
        isInPositiveDirection = !isInPositiveDirection;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}
