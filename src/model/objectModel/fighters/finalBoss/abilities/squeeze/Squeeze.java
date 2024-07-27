package model.objectModel.fighters.finalBoss.abilities.squeeze;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import model.animations.DashAnimation;
import model.interfaces.movementIntefaces.Navigator;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.abilities.Ability;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

public class Squeeze extends Ability implements Navigator {

    private SqueezeThread thread;
    private FrameModel epsilonFrame;
    private Boss boss;
    private boolean arrived;
    private final SqueezeNavigator navigator;

    public Squeeze(Boss boss ,FrameModel epsilonFrame){
        this.epsilonFrame = epsilonFrame;
        this.boss = boss;
        thread = new SqueezeThread(this);
        navigator = new SqueezeNavigator(epsilonFrame ,boss.getLeftHand() ,boss.getRightHand());
    }


    @Override
    protected void setUp() {
        ownHelper(boss.getRightHand());
        ownHelper(boss.getLeftHand());
        boss.getRightHand().setHovering(false);
        boss.getLeftHand().setHovering(false);
        boss.getHead().setVulnerableToEpsilonBullet(true);
    }

    @Override
    protected void unsetUp() {
        disownHelper(boss.getRightHand());
        disownHelper(boss.getLeftHand());
        boss.getRightHand().setHovering(false);
        boss.getLeftHand().setHovering(false);
        boss.getLeftHand().getFrame().setSolid(false);
        boss.getRightHand().getFrame().setSolid(false);
        boss.getHead().setVulnerableToEpsilonMelee(false);
        boss.getHead().setVulnerableToEpsilonBullet(false);
    }

    @Override
    public void activate() {
        super.activate();
        thread.start();
    }

    @Override
    protected void endAbility() {
        doAnimation();
        try {
            Thread.sleep(RefreshRateConstants.ABILITY_UNSETUP_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.interrupt();
        unsetUp();
    }

    private void doAnimation() {
        Vector leftD = Math.VectorAdd(
                new Vector(
                        SizeConstants.HAND_DIMENSION.width / 2d,
                        SizeConstants.SCREEN_SIZE.height / 2d
                ),
                Math.ScalarInVector(-1, boss.getLeftHand().getPosition())
        );
        new DashAnimation(
                boss.getLeftHand(),
                leftD,
                1000,
                Math.VectorSize(leftD),
                0,
                false
        ).StartAnimation();
        Vector rightD = Math.VectorAdd(
                new Vector(
                        SizeConstants.SCREEN_SIZE.width - SizeConstants.HAND_DIMENSION.width / 2d,
                        SizeConstants.SCREEN_SIZE.height / 2d
                ),
                Math.ScalarInVector(-1, boss.getRightHand().getPosition())
        );
        new DashAnimation(
                boss.getRightHand(),
                rightD,
                1000,
                Math.VectorSize(rightD),
                0,
                false
        ).StartAnimation();
    }

    @Override
    public boolean hasArrived() {
        return arrived;
    }

    @Override
    public void navigate() {
        navigator.navigate();
    }


    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public void checkArrived() {
        arrived = navigator.hasArrived();
        if (arrived){
            setUpArrived();
        }
    }

    private void setUpArrived() {
        boss.getLeftHand().setVelocity(new Vector());
        boss.getRightHand().setVelocity(new Vector());
        boss.getLeftHand().getFrame().setSolid(true);
        boss.getRightHand().getFrame().setSolid(true);
    }

    public FrameModel getEpsilonFrame(){
        return epsilonFrame;
    }

    public Boss getBoss() {
        return boss;
    }
}
