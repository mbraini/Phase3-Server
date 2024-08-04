package model.objectModel.projectiles;

import constants.DamageConstants;
import constants.SizeConstants;
import controller.game.Game;
import utils.Vector;

public class SlaughterBulletModel extends EpsilonBulletModel {
    public SlaughterBulletModel(Game game ,Vector position, Vector direction, String id) {
        super(game ,position, direction, id);
        damage = DamageConstants.SLAUGHTER_DAMAGE;
    }

    @Override
    public double getRadios() {
        return SizeConstants.SLAUGHTER_BULLET_RADIOS;
    }


}
