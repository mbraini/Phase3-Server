package model.objectModel.fighters;


import constants.DamageConstants;
import constants.RefreshRateConstants;
import constants.SizeConstants;
import controller.gameController.configs.Configs;
import controller.gameController.enums.ModelType;
import controller.gameController.interfaces.SizeChanger;
import controller.gameController.manager.Spawner;
import constants.ControllerConstants;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsCircle;
import model.interfaces.movementIntefaces.ImpactAble;
import model.interfaces.movementIntefaces.MoveAble;
import model.logics.collision.Collision;
import model.objectModel.FighterModel;
import utils.Helper;
import utils.Math;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class EpsilonModel extends FighterModel implements MoveAble, IsCircle, HasVertices, ImpactAble, SizeChanger {
    private ArrayList<EpsilonVertexModel> vertices = new ArrayList<>();
    private Dimension size;
    private boolean isImpacted = false;
    private int epsilonBulletDamage;
    private int epsilonDamageOnCollision;
    private int chanceOfSurvival;
    private int lifeSteal;
    public EpsilonModel(Vector position , String id){
        this.position = position;
        this.velocity = new Vector();
        this.acceleration = new Vector(0 ,0);
        this.size = new Dimension(
                SizeConstants.EPSILON_DIMENSION.width,
                SizeConstants.EPSILON_DIMENSION.height
        );
        this.id =  id;
        this.HP = 100;
        this.epsilonBulletDamage = DamageConstants.INITIAL_EPSILON_DAMAGE;
        this.meleeAttack = DamageConstants.INITIAL_EPSILON_DAMAGE;
        this.isSolid = true;
        type = ModelType.epsilon;
        vertices = new ArrayList<>();
    }

    @Override
    public void move() {
        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);
        ((HasVertices) this).UpdateVertices(xMoved ,yMoved ,omega);
        checkMaxSpeed();
    }

    void checkMaxSpeed(){
        double currentSpeed = java.lang.Math.sqrt(java.lang.Math.pow(velocity.x ,2)
                + java.lang.Math.pow(velocity.y ,2));
        assert currentSpeed != 0;
        if (currentSpeed > Configs.GameConfigs.EPSILON_MAX_SPEED){
            setVelocity(
                    getVelocity().x * Configs.GameConfigs.EPSILON_MAX_SPEED / currentSpeed ,
                    getVelocity().y * Configs.GameConfigs.EPSILON_MAX_SPEED / currentSpeed
            );
        }
    }

    @Override
    public double getRadios() {
        return size.height / 2d;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

    public void addVertex(){
        this.theta = 0;
        int vertexCount = vertices.size() + 1;
        double degree = java.lang.Math.PI * 2 / vertexCount;
        for (int i = 0; i < vertexCount - 1 ;i++){
            vertices.get(i).rotateTo(degree * i);
        }
        degree = degree * (vertexCount - 1);
        Vector direction = new Vector(java.lang.Math.cos(degree) , java.lang.Math.sin(degree));
        direction = Math.VectorWithSize(
                direction,
                SizeConstants.EPSILON_DIMENSION.width / 2d + SizeConstants.EPSILON_VERTICES_RADIOS
        );
        EpsilonVertexModel epsilonVertexModel = new EpsilonVertexModel(
                Math.VectorAdd(direction ,position),
                position.clone(),
                degree,
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
        Spawner.spawnVertex(epsilonVertexModel);
        vertices.add(epsilonVertexModel);
    }

    @Override
    public void UpdateVertices(double xMoved ,double yMoved ,double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.get(i).rotateBy(theta);
            Vector origin = new Vector(
                    getPosition().x + getRadios() + SizeConstants.EPSILON_VERTICES_RADIOS,
                    getPosition().y
            );
            vertices.get(i).setPosition(Math.RotateByTheta(
                    origin ,
                    getPosition() ,
                    vertices.get(i).getTheta())
            );
        }
    }

    public ArrayList<EpsilonVertexModel> getVertices(){
        return vertices;
    }
    public void Rotate(double theta){
        UpdateVertices(0 ,0 ,theta - this.theta);
        this.theta = theta;
    }

    @Override
    public boolean isImpacted() {
        return isImpacted;
    }

    @Override
    public void setImpacted(boolean impact) {
        isImpacted = impact;
    }

    public void setVertices(ArrayList<EpsilonVertexModel> vertices) {
        this.vertices = vertices;
    }

    @Override
    public void die() {
//        Controller.endGame(false);
    }

    public void meleeAttack(EnemyModel enemyModel){
        enemyModel.setHP(enemyModel.getHP() - epsilonDamageOnCollision);
        if (!enemyModel.isVulnerableToEpsilonMelee())
            return;
        for (EpsilonVertexModel vertex : vertices){
            if (Collision.IsColliding(vertex ,enemyModel)){
                enemyModel.setHP(enemyModel.getHP() - meleeAttack);
                setHP(getHP() + lifeSteal);
                checkHP();
                return;
            }
        }
    }

    public void checkHP() {
        if (HP > 100)
            HP = 100;
    }

    public int getEpsilonBulletDamage() {
        return epsilonBulletDamage;
    }

    public void setEpsilonBulletDamage(int epsilonBulletDamage) {
        this.epsilonBulletDamage = epsilonBulletDamage;
    }

    public int getEpsilonDamageOnCollision() {
        return epsilonDamageOnCollision;
    }

    public void setEpsilonDamageOnCollision(int epsilonDamageOnCollision) {
        this.epsilonDamageOnCollision = epsilonDamageOnCollision;
    }

    public int getChanceOfSurvival() {
        return chanceOfSurvival;
    }

    public void setChanceOfSurvival(int chanceOfSurvival) {
        this.chanceOfSurvival = chanceOfSurvival;
    }

    public int getLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(int lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }

    @Override
    public Dimension getSize() {
        return size;
    }
}
