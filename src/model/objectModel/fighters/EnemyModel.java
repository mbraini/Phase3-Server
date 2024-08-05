package model.objectModel.fighters;


import controller.game.Game;
import controller.game.GameData;
import controller.game.ObjectController;
import controller.game.manager.GameState;
import controller.game.player.Player;
import model.interfaces.collisionInterfaces.IsCircle;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.logics.collision.Collision;
import model.objectModel.FighterModel;
import utils.Vector;

import java.util.ArrayList;

public abstract class EnemyModel extends FighterModel {

    protected boolean vulnerableToEpsilonMelee;
    protected boolean vulnerableToEpsilonBullet;

    public EnemyModel(Game game ,Player chasingPlayer ,ArrayList<Player> targetedPlayers) {
        super(game ,chasingPlayer ,targetedPlayers);
    }

    public void meleeAttack(EpsilonModel epsilon){
        if (!hasMeleeAttack)
            return;
        if (!isTargeted(epsilon))
            return;
        if (this instanceof IsPolygon){
            for (Vector vertex : ((IsPolygon) this).getVertices()) {
                if (Collision.IsInCircle((IsCircle) epsilon, vertex)){
                    epsilon.setHP(epsilon.getHP() - meleeAttack);
                    return;
                }
            }
        }
    }

    @Override
    public void die() {
        game.getModelRequests().removeObjectModel(id);
        game.getGameState().setEnemyCount(game.getGameState().getEnemyCount() - 1);
        game.getGameState().setEnemyKilled(game.getGameState().getEnemyKilled() + 1);
    }

    public boolean isVulnerableToEpsilonMelee() {
        return vulnerableToEpsilonMelee;
    }

    public void setVulnerableToEpsilonMelee(boolean vulnerableToEpsilonMelee) {
        this.vulnerableToEpsilonMelee = vulnerableToEpsilonMelee;
    }

    public boolean isVulnerableToEpsilonBullet() {
        return vulnerableToEpsilonBullet;
    }

    public void setVulnerableToEpsilonBullet(boolean vulnerableToEpsilonBullet) {
        this.vulnerableToEpsilonBullet = vulnerableToEpsilonBullet;
    }
}
