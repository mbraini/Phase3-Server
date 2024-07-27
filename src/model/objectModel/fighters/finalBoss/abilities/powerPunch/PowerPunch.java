package model.objectModel.fighters.finalBoss.abilities.powerPunch;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import model.animations.DashAnimation;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.abilities.Ability;
import model.objectModel.fighters.finalBoss.bossHelper.PunchModel;
import model.objectModel.frameModel.FrameLocations;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

import javax.swing.*;

public class PowerPunch extends Ability {
    private Boss boss;
    private FrameModel epsilonFrame;
    private Timer timer;
    private FrameLocations frameLocation;

    public PowerPunch(Boss boss ,FrameModel epsilonFrame){
        this.boss = boss;
        this.epsilonFrame = epsilonFrame;
    }


    @Override
    protected void setUp() {
        ownHelper(boss.getPunch());
        boss.getPunch().setHovering(true);
        boss.getHead().setVulnerableToEpsilonMelee(true);
        boss.getHead().setVulnerableToEpsilonBullet(true);
        boss.getLeftHand().setVulnerableToEpsilonBullet(true);
        boss.getRightHand().setVulnerableToEpsilonBullet(true);
    }

    @Override
    protected void unsetUp() {
        disownHelper(boss.getPunch());
        boss.getPunch().setHovering(false);
        boss.getHead().setVulnerableToEpsilonMelee(false);
        boss.getHead().setVulnerableToEpsilonBullet(false);
        boss.getLeftHand().setVulnerableToEpsilonMelee(false);
        boss.getLeftHand().setVulnerableToEpsilonBullet(false);
        boss.getRightHand().setVulnerableToEpsilonMelee(false);
        boss.getRightHand().setVulnerableToEpsilonBullet(false);
    }

    @Override
    public void activate() {
        super.activate();
        chooseFrameLocation();
        PunchAnimation();
        timer = new Timer(RefreshRateConstants.ABILITY_SETUP_DELAY ,new PowerPunchAL(epsilonFrame ,frameLocation ,this));
        timer.start();
    }

    @Override
    protected void endAbility() {
        super.endAbility();
    }

    private void PunchAnimation() {
        Vector destination = setDestination();
        Vector direction = Math.VectorAdd(
                destination ,
                Math.ScalarInVector(-1 ,boss.getPunch().getPosition())
        );
        new DashAnimation(
                boss.getPunch(),
                direction,
                1000,
                Math.VectorSize(direction),
                0,
                true
        ).StartAnimation();
    }

    private Vector setDestination() {
        Vector destination;
        Vector leftMid = new Vector(
                epsilonFrame.getPosition().x,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height / 2d
        );
        Vector topMid = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width /2d,
                epsilonFrame.getPosition().y
        );
        Vector rightMid = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height /2d
        );
        Vector bottomMid = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width /2d,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height
        );
        switch (frameLocation){
            case top :
                destination = Math.VectorAdd(
                        topMid,
                        new Vector(
                                0,
                                -SizeConstants.PUNCH_DIMENSION.height /2d
                        )
                );
                break;
            case right:
                destination = Math.VectorAdd(
                        rightMid,
                        new Vector(
                                SizeConstants.PUNCH_DIMENSION.width /2d,
                                0
                        )
                );
                break;
            case bottom:
                destination = Math.VectorAdd(
                        bottomMid,
                        new Vector(
                                0,
                                SizeConstants.PUNCH_DIMENSION.height /2d
                        )
                );
                break;
            default:
                destination = Math.VectorAdd(
                        leftMid,
                        new Vector(
                                -SizeConstants.PUNCH_DIMENSION.width /2d,
                                0
                        )
                );
                break;
        }
        return destination;
    }

    private void chooseFrameLocation() {
        PunchModel punchModel = boss.getPunch();
        if (punchModel.getPosition().x <= epsilonFrame.getPosition().x){
            frameLocation = FrameLocations.left;
            return;
        }
        if (punchModel.getPosition().x >= epsilonFrame.getPosition().x + epsilonFrame.getSize().width){
            frameLocation = FrameLocations.right;
            return;
        }
        if (punchModel.getPosition().y <= epsilonFrame.getPosition().y){
            frameLocation = FrameLocations.top;
            return;
        }
        frameLocation = FrameLocations.bottom;
    }

    public Timer getTimer() {
        return timer;
    }
}
