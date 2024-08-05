package model.objectModel.projectiles;

import constants.DamageConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.player.Player;
import utils.Vector;

import java.util.ArrayList;

public class SlaughterBulletModel extends EpsilonBulletModel {
    public SlaughterBulletModel(Game game, ArrayList<Player> targetedPlayers , Vector position, Vector direction, String id) {
        super(game, targetedPlayers ,position, direction, id);
        damage = DamageConstants.SLAUGHTER_DAMAGE;
    }

    @Override
    public double getRadios() {
        return SizeConstants.SLAUGHTER_BULLET_RADIOS;
    }


}
