package model.objectModel.fighters.normalEnemies.omenoctModel;

import constants.SizeConstants;
import constants.TimeConstants;
import controller.gameController.enums.ModelType;
import controller.gameController.manager.GameState;
import controller.gameController.manager.Spawner;
import constants.ControllerConstants;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;
import utils.Helper;
import utils.Math;
import utils.Vector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OmenoctShooter implements ActionListener {

    private Vector position;
    private OmenoctModel omenoctModel;
    private int timePassed;

    public OmenoctShooter(Vector position ,OmenoctModel omenoctModel) {
        this.position = position;
        this.omenoctModel = omenoctModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameState.isDizzy() || GameState.isPause())
            return;
        if (GameState.isOver()) {
            System.out.println("OMENOCT OUT");
            omenoctModel.getShooter().stop();
            omenoctModel.getShooter().removeActionListener(this);
            return;
        }
        timePassed += 1000;
        if (timePassed >= TimeConstants.OMENOCT_FIRING_TIME) {
            timePassed = 0;
            EpsilonModel epsilon = ModelData.getEpsilon();
            Vector direction = Math.VectorAdd(
                    Math.ScalarInVector(-1, position),
                    epsilon.getPosition()
            );
            String id = Helper.RandomStringGenerator(ControllerConstants.ID_SIZE);
            Vector bulletPosition = Math.VectorAdd(
                    position,
                    Math.VectorWithSize(
                            direction,
                            SizeConstants.OMENOCT_BULLET_RADIOS + SizeConstants.OMENOCT_RADIOS
                    )
            );
            Spawner.addProjectile(bulletPosition, direction, ModelType.omenoctBullet);
        }
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
