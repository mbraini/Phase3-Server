package model.objectModel.fighters.finalBoss.abilities.rapidFire;

import constants.TimeConstants;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.abilities.Ability;

import javax.swing.*;

public class RapidFire extends Ability {

    private Timer shooter;

    private Boss boss;

    public RapidFire(Boss boss){
        this.boss = boss;
    }

    @Override
    protected void setUp() {
        ownHelper(boss.getHead());
        boss.getHead().setVulnerableToEpsilonBullet(true);
    }

    @Override
    protected void unsetUp() {
        disownHelper(boss.getHead());
        boss.getHead().setVulnerableToEpsilonMelee(false);
        boss.getHead().setVulnerableToEpsilonBullet(false);
    }

    @Override
    public void activate() {
        super.activate();
        shooter = new Timer(TimeConstants.BOSS_BULLET_DELAY_TIME,new HeadShooter(this));
        shooter.start();
    }

    @Override
    protected void endAbility() {
        super.endAbility();
    }

    public Timer getShooter() {
        return shooter;
    }

    public Boss getBoss() {
        return boss;
    }
}
