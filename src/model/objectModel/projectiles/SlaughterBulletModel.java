package model.objectModel.projectiles;

import constants.DamageConstants;
import constants.SizeConstants;
import utils.Vector;

public class SlaughterBulletModel extends EpsilonBulletModel {
    public SlaughterBulletModel(Vector position, Vector direction, String id) {
        super(position, direction, id);
        damage = DamageConstants.SLAUGHTER_DAMAGE;
    }

    @Override
    public double getRadios() {
        return SizeConstants.SLAUGHTER_BULLET_RADIOS;
    }


}
