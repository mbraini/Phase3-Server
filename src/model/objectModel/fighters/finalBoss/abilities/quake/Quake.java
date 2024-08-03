package model.objectModel.fighters.finalBoss.abilities.quake;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import controller.game.ModelRequestController;
import model.logics.Dash;
import model.logics.Impact;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.abilities.Ability;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quake extends Ability {

    private Boss boss;
    private FrameModel epsilonFrame;
    private Timer reorderTimer;
    private Timer randomizeTimer;

    public Quake(Boss boss ,FrameModel frameModel) {
        this.boss = boss;
        this.epsilonFrame = frameModel;
        setUpTimers();
    }

    private void setUpTimers() {
        reorderTimer = new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ModelRequestController.reorderKeys(); ////////////////todo
                endAbility();
                reorderTimer.stop();
            }
        });
        randomizeTimer = new Timer(RefreshRateConstants.ABILITY_SETUP_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ModelRequestController.randomizeKeys(); /////////////todo
                new Impact(
                        boss.getGame(),
                        boss.getPunch().getPosition(),
                        SizeConstants.SCREEN_SIZE.height,
                        3000,
                        true
                ).MakeImpact();
                randomizeTimer.stop();
            }
        });
    }

    @Override
    protected void setUp() {
        ownHelper(boss.getPunch());
        boss.getPunch().setHovering(true);
    }

    @Override
    protected void unsetUp() {
        disownHelper(boss.getPunch());
        boss.getPunch().setHovering(false);
    }

    @Override
    public void activate(){
        super.activate();
        punchAnimation();
        randomizeTimer.start();
        reorderTimer.start();
    }

    @Override
    public void endAbility() {
        super.endAbility();
    }

    private void punchAnimation() {
        Vector destination = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width / 2d,
                epsilonFrame.getPosition().y + epsilonFrame.getSize().height + SizeConstants.PUNCH_DIMENSION.height / 2d
        );
        Vector direction = Math.VectorAdd(
                destination ,
                Math.ScalarInVector(-1 ,boss.getPunch().getPosition())
        );
        new Dash(
                boss.getPunch(),
                direction,
                RefreshRateConstants.ABILITY_SETUP_DELAY,
                Math.VectorSize(direction),
                0,
                true
        ).startDash();
    }


}
