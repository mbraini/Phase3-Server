package model.objectModel.fighters.finalBoss.bossAI;

import constants.DistanceConstants;
import constants.SizeConstants;
import constants.TimeConstants;
import model.logics.Dash;
import model.logics.collision.Collision;
import model.objectModel.ObjectModel;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.bossHelper.BossHelperModel;
import model.objectModel.fighters.finalBoss.bossHelper.HeadModel;
import model.objectModel.projectiles.EpsilonBulletModel;
import utils.Math;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BossAI {

    private Boss boss;
    private EpsilonModel epsilonModel;
    private ArrayList<ObjectModel> models;


    public BossAI(Boss boss , EpsilonModel epsilonModel){
        this.boss = boss;
        this.epsilonModel = epsilonModel;
    }

    public boolean isInProjectileRange() {
        Vector distanceV = Math.VectorAdd(
                boss.getHead().getPosition(),
                Math.ScalarInVector(-1 ,epsilonModel.getPosition())
        );

        if (Math.VectorSize(distanceV) >= SizeConstants.PROJECTILE_ACTIVATION_RADIOS)
            return true;
        return false;
    }

    public boolean isInSqueezePosition() {
        boolean isUnderHead = false;
        boolean isBetweenHands = false;
        if (epsilonModel.getPosition().y >= boss.getHead().getPosition().y){
            isUnderHead = true;
        }
        if (epsilonModel.getPosition().x <= boss.getRightHand().getPosition().x &&
                boss.getLeftHand().getPosition().x <= epsilonModel.getPosition().x)
        {
            double yMin = java.lang.Math.min(
                    boss.getRightHand().getFrame().getPosition().y ,
                    boss.getLeftHand().getFrame().getPosition().y
            );
            double yMax = java.lang.Math.max(
                    boss.getRightHand().getFrame().getPosition().y + boss.getRightHand().getFrame().getSize().height,
                    boss.getLeftHand().getFrame().getPosition().y + boss.getLeftHand().getFrame().getSize().height
            );
            double epsilonY = epsilonModel.getPosition().y;
            if (epsilonY <= yMax && epsilonY >= yMin)
                isBetweenHands = true;
        }

        return isUnderHead && isBetweenHands;
    }


    public void dash(BossHelperModel helperModel) {
        if (helperModel.isDead() || helperModel.isInUse() || helperModel.isInDash())
            return;
        if (isAttacked(helperModel)) {
            if (helperModel instanceof HeadModel) {
                headDash(helperModel);
            }
            else {
                handDash(helperModel);
            }
        }

    }

    private void handDash(BossHelperModel helperModel) {
        if (helperModel.dashedInPositiveWay()) {
            new Dash(
                    helperModel,
                    new Vector(0,1),
                    TimeConstants.BOSS_HELPER_DASH_TIME,
                    DistanceConstants.BOSS_HELPER_DASH_DISTANCE,
                    0,
                    false
            ).startDash();
        }
        else {
            new Dash(
                    helperModel,
                    new Vector(0,-1),
                    TimeConstants.BOSS_HELPER_DASH_TIME,
                    DistanceConstants.BOSS_HELPER_DASH_DISTANCE,
                    0,
                    false
            ).startDash();
        }
        helperModel.setInDash(true);
        Timer timer = new Timer(
                TimeConstants.BOSS_HELPER_DASH_TIME,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        helperModel.setInDash(false);

                    }
                }
        );
        timer.setRepeats(false);
        timer.start();
        helperModel.setDashedInPositiveWay(!helperModel.dashedInPositiveWay());
    }

    private void headDash(BossHelperModel helperModel) {
        if (helperModel.dashedInPositiveWay()) {
            new Dash(
                    helperModel,
                    new Vector(1,0),
                    TimeConstants.BOSS_HELPER_DASH_TIME,
                    DistanceConstants.BOSS_HELPER_DASH_DISTANCE,
                    0,
                    false
            ).startDash();
        }
        else {
            new Dash(
                    helperModel,
                    new Vector(-1,0),
                    TimeConstants.BOSS_HELPER_DASH_TIME,
                    DistanceConstants.BOSS_HELPER_DASH_DISTANCE,
                    0,
                    false
            ).startDash();
        }
        helperModel.setInDash(true);
        Timer timer = new Timer(
                TimeConstants.BOSS_HELPER_DASH_TIME,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        helperModel.setInDash(false);
                    }
                }
        );
        timer.setRepeats(false);
        timer.start();
        helperModel.setDashedInPositiveWay(!helperModel.dashedInPositiveWay());
    }

    private boolean isAttacked(BossHelperModel helperModel) {
        for (ObjectModel model : models) {
            if (model instanceof EpsilonBulletModel) {
                if (isAttacking((EpsilonBulletModel)model ,helperModel))
                    return true;
            }
        }
        return false;
    }

    private boolean isAttacking(EpsilonBulletModel bullet, BossHelperModel helperModel) {
        Vector direction = bullet.getVelocity().clone();
        double distance = Math.VectorSize(
                Math.VectorAdd(
                        bullet.getPosition(),
                        Math.ScalarInVector(-1 ,helperModel.getPosition())
                )
        );
        ArrayList<Vector> imaginaryVertices = new ArrayList<>();
        Vector n1 = Math.NormalWithSize(direction , SizeConstants.EPSILON_BULLET_RADIOS);
        Vector n2 = Math.ScalarInVector(-1 ,n1);
        imaginaryVertices.add(
                Math.VectorAdd(
                        n1,
                        bullet.getPosition()
                )
        );
        imaginaryVertices.add(
                Math.VectorAdd(
                        imaginaryVertices.getFirst(),
                        Math.VectorWithSize(direction ,distance)
                )
        );
        imaginaryVertices.add(
                Math.VectorAdd(
                        imaginaryVertices.get(1),
                        n2
                )
        );
        imaginaryVertices.add(
                Math.VectorAdd(
                        n2,
                        bullet.getPosition()
                )
        );
        ImaginaryObject imaginaryObject = new ImaginaryObject(imaginaryVertices);
        if (Collision.IsColliding(imaginaryObject ,helperModel)) {
            return true;
        }
        return false;
    }


    public void setModels(ArrayList<ObjectModel> models) {
        this.models = models;
    }
}