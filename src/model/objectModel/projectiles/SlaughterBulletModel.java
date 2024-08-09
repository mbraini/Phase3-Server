package model.objectModel.projectiles;

import constants.DamageConstants;
import constants.SizeConstants;
import controller.game.onlineGame.Game;
import controller.game.enums.ModelType;
import controller.game.player.Player;
import utils.Vector;

import java.util.ArrayList;

public class SlaughterBulletModel extends EpsilonBulletModel {
    public SlaughterBulletModel(Game game,Player belongingPlayer , ArrayList<Player> targetedPlayers , Vector position, Vector direction, String id) {
        super(game,belongingPlayer , targetedPlayers ,position, direction, id);
        damage = DamageConstants.SLAUGHTER_DAMAGE;
        type = ModelType.slaughterBullet;
    }

    @Override
    public double getRadios() {
        return SizeConstants.SLAUGHTER_BULLET_RADIOS;
    }


}
