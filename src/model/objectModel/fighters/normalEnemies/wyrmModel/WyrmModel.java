package model.objectModel.fighters.normalEnemies.wyrmModel;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import controller.gameController.ObjectController;
import controller.gameController.enums.ModelType;
import controller.gameController.manager.Spawner;
import controller.gameController.manager.loading.SkippedByJson;
import model.ModelData;
import model.interfaces.*;
import model.interfaces.collisionInterfaces.CollisionDetector;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.movementIntefaces.MoveAble;
import model.interfaces.movementIntefaces.Navigator;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.frameModel.FrameModelBuilder;
import model.objectModel.fighters.normalEnemies.NormalEnemyModel;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class WyrmModel extends NormalEnemyModel implements Navigator, FrameSticker , MoveAble, CollisionDetector {

    private FrameModel frameModel;
    private boolean isInRange;
    private ArrayList<Vector> vertices;
    @SkippedByJson
    private WyrmThread wyrmThread;
    private boolean positiveDirection;
    private Vector origin;

    public WyrmModel(Vector position ,String id){
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
        ObjectController.removeFrame(frameModel);
        if (wyrmThread != null)
            wyrmThread.interrupt();
        Spawner.addCollectives(position ,2 ,8);
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
            origin = ModelData.getModels().getFirst().getPosition().clone();
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
            wyrmThread.start();
        }
    }

    private void initWyrmThread() {
        wyrmThread = new WyrmThread(this, origin);
    }


}
