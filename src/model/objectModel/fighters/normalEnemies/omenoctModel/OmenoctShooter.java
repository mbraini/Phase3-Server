package model.objectModel.fighters.normalEnemies.omenoctModel;

import constants.ControllerConstants;
import constants.SizeConstants;
import constants.TimeConstants;
import controller.game.enums.ModelType;
import controller.game.manager.GameState;
import controller.game.manager.Spawner;
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
        if (omenoctModel.getGame().getGameState().isDizzy() || omenoctModel.getGame().getGameState().isPause())
            return;
        if (omenoctModel.getGame().getGameState().isOver()) {
            System.out.println("OMENOCT OUT");
            omenoctModel.getShooter().stop();
            omenoctModel.getShooter().removeActionListener(this);
            return;
        }
        timePassed += 1000;
        if (timePassed >= TimeConstants.OMENOCT_FIRING_TIME) {
            timePassed = 0;
//            EpsilonModel epsilon = ModelData.getEpsilon(); ////todo
            Vector direction = Math.VectorAdd(
                    Math.ScalarInVector(-1, position),
                    new Vector()
//                    epsilon.getPosition()  //// todo
            );
            String id = Helper.RandomStringGenerator(ControllerConstants.ID_SIZE);
            Vector bulletPosition = Math.VectorAdd(
                    position,
                    Math.VectorWithSize(
                            direction,
                            SizeConstants.OMENOCT_BULLET_RADIOS + SizeConstants.OMENOCT_RADIOS
                    )
            );
            Spawner.addProjectile(omenoctModel.getGame() ,bulletPosition, direction, ModelType.omenoctBullet);
        }
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
