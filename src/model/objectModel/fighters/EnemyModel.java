package model.objectModel.fighters;


import controller.game.Game;
import controller.game.GameData;
import controller.game.ObjectController;
import controller.game.manager.GameState;
import model.interfaces.collisionInterfaces.IsCircle;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.logics.collision.Collision;
import model.objectModel.FighterModel;
import utils.Vector;

public abstract class EnemyModel extends FighterModel {

    protected boolean vulnerableToEpsilonMelee;
    protected boolean vulnerableToEpsilonBullet;

    public EnemyModel(Game game) {
        super(game);
    }

    public void meleeAttack(EpsilonModel epsilon){
        if (!hasMeleeAttack)
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
        ObjectController.removeObject(this);
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
