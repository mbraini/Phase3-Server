package model.objectModel.fighters.finalBoss.abilities.projectile;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.TimeConstants;
import controller.game.enums.ModelType;
import controller.game.manager.GameState;
import controller.game.manager.Spawner;
import model.interfaces.collisionInterfaces.HasVertices;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.fighters.finalBoss.bossHelper.HandModel;
import model.objectModel.fighters.finalBoss.bossHelper.HeadModel;
import utils.Math;
import utils.Vector;

public class ProjectileThread extends Thread{

    private Projectile projectile;
    private Vector origin;
    private EpsilonModel epsilon;
    private final double thetaD = (java.lang.Math.PI / 2) / (1000 / RefreshRateConstants.PROJECTILE_THREAD_REFRESH_RATE);
    private double time;

    public ProjectileThread(Projectile projectile , EpsilonModel epsilon){
        this.epsilon = epsilon;
        this.projectile = projectile;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double deltaModel = 0;
        while (!epsilon.getGame().getGameState().isOver() && !isInterrupted()) {
            if (epsilon.getGame().getGameState().isPause() || epsilon.getGame().getGameState().isDizzy()){
                lastTime = System.nanoTime();
                continue;
            }
            if (epsilon.getGame().getGameState().isInAnimation()) {
                projectile.endAbility();
                return;
            }
            long now = System.nanoTime();
            deltaModel += (now - lastTime) / ns;
            lastTime = now;
            if (deltaModel >= RefreshRateConstants.PROJECTILE_THREAD_REFRESH_RATE) {
                time += RefreshRateConstants.PROJECTILE_THREAD_REFRESH_RATE;
                update();
                deltaModel = 0;
            }
        }
    }

    private void update() {
        turnAround();
        fireIf();
        if (time >= TimeConstants.PROJECTILE_DURATION)
            projectile.endAbility();
    }

    private void fireIf() {
        if (time % 1000 != 0){
            return;
        }
        if (!projectile.getBoss().getLeftHand().isDead())
            fire(projectile.getBoss().getLeftHand());
        if (!projectile.getBoss().getRightHand().isDead())
            fire(projectile.getBoss().getRightHand());
    }

    private void fire(HandModel hand) {
        Vector position = hand.getPosition();
        Vector direction = Math.VectorAdd(
                Math.ScalarInVector(-1 ,position),
                epsilon.getPosition()
        );

        Vector bulletPosition = Math.VectorAdd(
                position,
                Math.VectorWithSize(
                        direction ,
                        SizeConstants.BOSS_BULLET_RADIOS + SizeConstants.HAND_DIMENSION.width / 2d
                )
        );

        Spawner.addProjectile(epsilon.getGame() ,bulletPosition ,direction , ModelType.bossBullet);
    }

    private void turnAround() {
        if (origin == null)
            return;
        turnAroundObject(projectile.getBoss().getHead());
    }

    private void turnAroundObject(HeadModel headModel) {
        Vector newPosition;
        if (headModel.isInPositiveDirection()) {
            newPosition = Math.RotateByTheta(headModel.getPosition(), origin, thetaD);
        }
        else {
            newPosition = Math.RotateByTheta(headModel.getPosition(), origin, -thetaD);
        }
        Vector previousPosition = headModel.getPosition().clone();
        headModel.setPosition(newPosition);
        Vector moved = Math.VectorAdd(
                newPosition,
                Math.ScalarInVector(-1 ,previousPosition)
        );
        if (headModel instanceof HasVertices)
            ((HasVertices) headModel).UpdateVertices(moved.x ,moved.y ,0);
    }

    public Vector getOrigin() {
        return origin;
    }

    public void setOrigin(Vector origin) {
        this.origin = origin;
    }
}
