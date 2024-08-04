package model.objectModel.fighters.normalEnemies.wyrmModel;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import controller.game.Game;
import controller.game.ObjectController;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import model.ModelData;
import model.interfaces.FrameSticker;
import model.interfaces.collisionInterfaces.CollisionDetector;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.movementIntefaces.MoveAble;
import model.interfaces.movementIntefaces.Navigator;
import model.objectModel.fighters.normalEnemies.NormalEnemyModel;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.frameModel.FrameModelBuilder;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class WyrmModel extends NormalEnemyModel implements Navigator, FrameSticker , MoveAble, CollisionDetector {

    private FrameModel frameModel;
    private boolean isInRange;
    private ArrayList<Vector> vertices;
    @SkippedByJson
    private WyrmThread shooter;
    private boolean positiveDirection;
    private Vector origin;

    public WyrmModel(Game game, Vector position , String id){
        super(game);
        this.id = id;
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.HP = 12;
        type = ModelType.wyrm;
        vulnerableToEpsilonBullet = true;
        setFrame();
        setPosition(Math.VectorAdd(
                position,
                new Vector(frameModel.getSize().width / 2d ,frameModel.getSize().height / 2d))
        );
        initVertices();
    }

    private void setFrame() {
        FrameModelBuilder builder = new FrameModelBuilder(
                game,
                position.clone(),
                SizeConstants.WYRM_FRAME_DIMENSION,
                id
        );
        builder.setIsometric(true);
        builder.setSolid(false);
        frameModel = builder.create();
    }


    @Override
    public void die() {
        super.die();
        game.getModelRequests().removeFrameModel(frameModel.getId());
        if (shooter != null)
            shooter.setInterrupt(true);
        Spawner.addCollectives(game ,position ,2 ,8);
    }

    public FrameModel getFrameModel() {
        return frameModel;
    }

    public void setFrameModel(FrameModel frameModel) {
        this.frameModel = frameModel;
    }

    @Override
    public boolean hasArrived() {
        return isInRange;
    }

    public boolean isPositiveDirection() {
        return positiveDirection;
    }

    public void setPositiveDirection(boolean positiveDirection) {
        this.positiveDirection = positiveDirection;
    }

    @Override
    public void navigate() {
        WyrmNavigator navigator = new WyrmNavigator(this);
        navigator.navigate();
        isInRange = navigator.hasArrived();
        if (isInRange){
            setVelocity(0 ,0);
            origin = game.getModelData().getModels().getFirst().getPosition().clone();
            start();
        }
    }

    @Override
    public void setStuckFramePosition() {
        frameModel.transfer(Math.VectorAdd(
                position,
                new Vector(
                        -SizeConstants.WYRM_FRAME_DIMENSION.width / 2d,
                        -SizeConstants.WYRM_FRAME_DIMENSION.height / 2d
                )
        ));
    }

    @Override
    public void move() {
        if (isInRange) {
            circularMovement();
        }
        else {
            regularMovement();
        }
    }

    private void circularMovement() {
        Vector newPosition;
        if (origin == null)
            return;
        double thetaD = RefreshRateConstants.UPS * VelocityConstants.WYRM_THETA_UPDATE;
        if (isPositiveDirection()) {
            newPosition = Math.RotateByTheta(getPosition(), origin, thetaD);
            setTheta(getTheta() + thetaD);
        }
        else {
            newPosition = Math.RotateByTheta(getPosition() ,origin ,-thetaD);
            setTheta(getTheta() - thetaD);
        }
        Vector previousPosition = getPosition().clone();
        setPosition(newPosition);
        Vector moved = Math.VectorAdd(
                newPosition,
                Math.ScalarInVector(-1 ,previousPosition)
        );
        UpdateVertices(moved.x ,moved.y ,thetaD);
    }

    private void regularMovement() {
        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);


        omega += alpha * RefreshRateConstants.UPS;
        double thetaMoved = ((2 * omega - alpha * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        theta = theta + thetaMoved;
        if (this instanceof HasVertices)
            ((HasVertices) this).UpdateVertices(xMoved ,yMoved ,thetaMoved);
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    public void setThetaRelativeToOrigin(Vector distance) {
        Vector xVector = new Vector(1 ,0);
        double dotProduct = Math.DotProduct(distance ,xVector);
        double cosTheta = dotProduct / Math.VectorSize(distance);
        setTheta(java.lang.Math.acos(cosTheta));
    }

    public void initVertices(){
        vertices = new ArrayList<>();
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                SizeConstants.WYRM_DIMENSION.width /2d,
                                0
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                0,
                                -SizeConstants.WYRM_DIMENSION.height / 2d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.WYRM_DIMENSION.width / 2d,
                                0
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                0,
                                SizeConstants.WYRM_DIMENSION.height /2d
                        )
                )
        );
    }

    @Override
    public void UpdateVertices(double xMoved ,double yMoved ,double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(vertices.get(i).getX() + xMoved ,vertices.get(i).getY() + yMoved));
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }

    @Override
    public void detect() {
        positiveDirection = !positiveDirection;
    }


    public void start(){
        if (isInRange) {
            initWyrmThread();
            shooter.start();
        }
    }

    private void initWyrmThread() {
        shooter = new WyrmThread(this);
    }


}
