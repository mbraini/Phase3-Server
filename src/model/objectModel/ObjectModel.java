package model.objectModel;


import controller.game.onlineGame.Game;
import controller.game.enums.ModelType;
import model.logics.MovementManager;
import utils.Vector;

public abstract class ObjectModel {
    protected Game game;
    protected Vector position;
    protected Vector velocity;
    protected Vector acceleration;
    protected double theta;
    protected double omega;
    protected double alpha;
    protected MovementManager movementManager;
    protected String id;
    protected double HP;
    protected boolean isHovering;
    protected boolean isSolid;
    protected boolean isMotionless;
    protected ModelType type;

    public ObjectModel(Game game) {
        this.game = game;
    }

    public Vector getPosition() {
        return position;
    }

    public abstract void die();

    public void setPosition(double x ,double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setPosition(Vector vector) {
        this.position.setX(vector.getX());
        this.position.setY(vector.getY());
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(double x ,double y) {
        this.velocity.setX(x);
        this.velocity.setY(y);
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double x ,double y) {
        this.acceleration.setX(x);
        this.acceleration.setY(y);
    }
    public void setAcceleration(Vector vector) {
        this.acceleration.setX(vector.getX());
        this.acceleration.setY(vector.getY());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getOmega() {
        return omega;
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public boolean isHovering() {
        return isHovering;
    }

    public void setHovering(boolean hovering) {
        isHovering = hovering;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public ModelType getType() {
        return type;
    }

    public void setType(ModelType type) {
        this.type = type;
    }

    public boolean isMotionless() {
        return isMotionless;
    }

    public void setMotionless(boolean motionless) {
        isMotionless = motionless;
    }

    public MovementManager getMovementManager() {
        return movementManager;
    }

    public void setMovementManager(MovementManager movementManager) {
        this.movementManager = movementManager;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
