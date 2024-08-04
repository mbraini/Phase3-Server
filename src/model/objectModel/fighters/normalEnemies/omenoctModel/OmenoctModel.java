package model.objectModel.fighters.normalEnemies.omenoctModel;

import constants.DamageConstants;
import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.online.annotations.SkippedByJson;
import model.interfaces.Ability;
import model.interfaces.movementIntefaces.FrameAttacher;
import model.interfaces.movementIntefaces.MoveAble;
import model.objectModel.fighters.normalEnemies.NormalEnemyModel;
import model.objectModel.frameModel.FrameLocations;
import utils.Math;
import utils.Vector;

import javax.swing.*;
import java.util.ArrayList;

public class OmenoctModel extends NormalEnemyModel implements Ability , MoveAble, FrameAttacher {

    /////////////////////// Fix the Ability interface with Navigator interface


    private ArrayList<Vector> vertices = new ArrayList<>();
    private FrameLocations frameLocation;
    private FrameLocations willAttachTo;
    private Vector destination;
    @SkippedByJson
    private Timer shooter;
    private final OmenoctNavigater navigater;

    public OmenoctModel(Game game ,Vector position , String id){
        super(game);
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.id = id;
        this.HP = 20;
        type = ModelType.omenoct;
        vulnerableToEpsilonBullet = true;
        vulnerableToEpsilonMelee = true;
        hasMeleeAttack = true;
        meleeAttack = DamageConstants.OMENOCT_MELEE_ATTACK;
        omega = VelocityConstants.ENEMY_ROTATION_SPEED;
        navigater = new OmenoctNavigater(position);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        initVertices();
    }

    public void initVertices() {
        vertices = new ArrayList<>();
        Vector initVector = new Vector(
                java.lang.Math.cos(java.lang.Math.PI / 8),
                java.lang.Math.sin(java.lang.Math.PI / 8)
        );
        initVector = Math.VectorWithSize(initVector , SizeConstants.OMENOCT_RADIOS);
        for (int i = 0 ;i < 8 ;i++){
            vertices.add(
                    Math.VectorAdd(
                            position,
                            initVector
                    )
            );
            initVector = Math.RotateByTheta(initVector ,new Vector() ,java.lang.Math.PI / 4);
        }
    }


    @Override
    public void ability() {
        navigater.reset(position);
        navigater.navigateFrame();
        destination = navigater.getDestination();
        willAttachTo = navigater.getWillAttachTo();
        setNavigationVelocity();
        checkAttached();
        setOmenoctShooterPosition();
    }

    private void setOmenoctShooterPosition() {
        if (shooter != null) {
            ((OmenoctShooter)shooter.getActionListeners()[0]).setPosition(position);
        }
    }

    private void setNavigationVelocity() {
        assert destination!= null;
        velocity = Math.VectorWithSize(
                Math.VectorAdd(
                        Math.ScalarInVector(-1 ,position),
                        destination
                )
                , VelocityConstants.OMENOCT_NAVIGATE_VELOCITY
        );
    }

    private void checkAttached() {
        if (willAttachTo == null)
            return;
        if (Math.VectorSize(Math.VectorAdd(
                Math.ScalarInVector(-1 ,position),
                destination
        )) <= VelocityConstants.OMENOCT_NAVIGATE_VELOCITY * RefreshRateConstants.UPS){
            position = destination.clone();
            velocity = new Vector();
        }
        if (destination.Equals(position)){
            frameLocation = willAttachTo;
            setUpShooter();
        }
    }

    private void setUpShooter() {
        if (shooter != null){
            shooter.start();
        }
        else {
            shooter = new Timer(1000, new OmenoctShooter(position ,this));
            shooter.start();
        }
    }


    @Override
    public boolean hasAbility() {
        return true;
    }


    @Override
    public FrameLocations getAttachedLocation() {
        return frameLocation;
    }

    @Override
    public void damage() {
        HP = HP - DamageConstants.OMENOCT_FRAME_DAMAGE;
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
        UpdateVertices(xMoved ,yMoved ,thetaMoved);
    }

    @Override
    public void die() {
        super.die();
        if (shooter != null) {
            shooter.stop();
        }
        Spawner.addCollectives(game ,position ,8 ,4);
    }

    @Override
    public void UpdateVertices(double xMoved, double yMoved, double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(vertices.get(i).getX() + xMoved ,vertices.get(i).getY() + yMoved));
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    public Timer getShooter() {
        return shooter;
    }
}
