package model.logics.collision;


import constants.DistanceConstants;
import constants.SoundPathConstants;
import controller.game.ModelRequestController;
import controller.game.PlayerData;
import model.inGameAbilities.Dismay.EpsilonProtectorModel;
import model.interfaces.collisionInterfaces.CollisionDetector;
import model.interfaces.collisionInterfaces.HasVertices;
import model.logics.Impact;
import model.objectModel.ObjectModel;
import model.objectModel.PortalModel;
import model.objectModel.fighters.EnemyModel;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.fighters.finalBoss.bossHelper.BossHelperModel;
import model.objectModel.projectiles.BulletModel;
import model.objectModel.projectiles.EnemyBulletModel;
import model.objectModel.projectiles.EpsilonBulletModel;
import model.skillTreeAbilities.Cerberus.CerberusModel;
import utils.Math;
import utils.Vector;

import java.util.Random;

public class CollisionHandler {
    ObjectModel model1;
    ObjectModel model2;
    Vector collisionPoint;
    public CollisionHandler(ObjectModel model1 ,ObjectModel model2){
        this.model1 = model1;
        this.model2 = model2;
        this.collisionPoint = Collision.FindCollisionPoint(model1 ,model2);
    }

    public void handle() {
        if (collisionPoint == null)
            return;
        ///////////epsilon and anotherModel
        if (model1 instanceof EpsilonModel || model2 instanceof EpsilonModel){
            if (model1 instanceof EpsilonModel){
                epsilonHandler((EpsilonModel) model1 ,model2);
            }
            else {
                epsilonHandler((EpsilonModel) model2 ,model1);
            }
            return;
        }

        /////////enemy and enemy
        if (model1 instanceof EnemyModel && model2 instanceof EnemyModel){
            enemyHandler((EnemyModel) model1 ,(EnemyModel)model2);
            return;
        }

        ///////////enemy and epsilonBullet
        if (model1 instanceof EnemyModel && model2 instanceof EpsilonBulletModel) {
            BulletToEnemyHandler((EnemyModel) model1, (EpsilonBulletModel) model2);
            return;
        }
        if (model2 instanceof EnemyModel && model1 instanceof EpsilonBulletModel){
            BulletToEnemyHandler((EnemyModel) model2 ,(EpsilonBulletModel) model1);
            return;
        }
        //////////epsilon protector handler
        if (model1 instanceof EpsilonProtectorModel && model2 instanceof EnemyModel){
            EpsilonProtectorHandler((EpsilonProtectorModel) model1 ,(EnemyModel) model2);
            return;
        }
        if (model2 instanceof EpsilonProtectorModel && model1 instanceof EnemyModel){
            EpsilonProtectorHandler((EpsilonProtectorModel) model2 ,(EnemyModel) model1);
            return;
        }
        ////////cerberus enemy handler
        if (model1 instanceof CerberusModel && model2 instanceof EnemyModel){
            CerberusEnemyHandler((CerberusModel)model1 ,(EnemyModel)model2);
            return;
        }
        if (model2 instanceof CerberusModel && model1 instanceof EnemyModel){
            CerberusEnemyHandler((CerberusModel)model2 ,(EnemyModel)model1);
            return;
        }

    }

    private void CerberusEnemyHandler(CerberusModel cerberusModel, EnemyModel enemyModel) {
        cerberusModel.damageIf(enemyModel);
    }

    private void EpsilonProtectorHandler(EpsilonProtectorModel protectorModel, EnemyModel enemyModel) {
        if (enemyModel.isHovering() || enemyModel.isMotionless())
            return;
        pullOutObject(enemyModel ,protectorModel);
    }

    private void BulletToEnemyHandler(EnemyModel enemy, EpsilonBulletModel epsilonBullet) {
        if (enemy.isVulnerableToEpsilonBullet())
            enemy.setHP(enemy.getHP() - epsilonBullet.getDamage());
        PlayerData playerData = epsilonBullet.getBelongingPlayer().getPlayerData();
        playerData.setSuccessfulBullets(playerData.getSuccessfulBullets() + 1);
        EpsilonModel epsilonModel = epsilonBullet.getBelongingPlayer().getPlayerData().getEpsilon();
        epsilonModel.setHP(epsilonModel.getHP() + epsilonModel.getLifeSteal());
        epsilonModel.checkHP();
        new Impact(enemy.getGame() ,epsilonBullet.getPosition() , DistanceConstants.REGULAR_IMPACT_RANGE).MakeImpact();
        epsilonBullet.die();
    }

    private void enemyHandler(EnemyModel enemy1, EnemyModel enemy2) {
        if (enemy1.isHovering() || enemy2.isHovering())
            return;
        if (enemy1 instanceof BossHelperModel && enemy2 instanceof BossHelperModel)
            return;
        if (enemy2.isMotionless())
            pullOutObject(enemy1 ,enemy2);
        else
            pullOutObject(enemy2 ,enemy1);
        new Impact(enemy1.getGame() ,collisionPoint , DistanceConstants.REGULAR_IMPACT_RANGE).MakeImpact();
        if (enemy1 instanceof CollisionDetector)
            ((CollisionDetector) enemy1).detect();
        if (enemy2 instanceof CollisionDetector)
            ((CollisionDetector) enemy2).detect();
    }

    private void epsilonHandler(EpsilonModel epsilon ,ObjectModel object) {
        if (object.isHovering()){
            return;
        }
        if (object instanceof EpsilonModel) {
            if (object.getId().equals(epsilon.getId()))
                return;
            pullOutObject(epsilon ,object);
            new Impact(epsilon.getGame() , collisionPoint, DistanceConstants.REGULAR_IMPACT_RANGE).MakeImpact();
        }
        if (object instanceof EnemyModel){
            epsilonEnemyMeleeHandler(epsilon ,(EnemyModel)object);
            pullOutObject(epsilon ,object);
            new Impact(epsilon.getGame() ,collisionPoint , DistanceConstants.REGULAR_IMPACT_RANGE).MakeImpact();
            ModelRequestController.playSound(SoundPathConstants.impactSound);
            if (object instanceof BossHelperModel){
                object.setAcceleration(0 ,0);
                object.setVelocity(0 ,0);
            }
        }
        if (object instanceof EnemyBulletModel){
            EnemyBulletModel enemyBulletModel = (EnemyBulletModel) object;
            if (enemyBulletModel.isTargeting(epsilon)) {
            epsilon.setHP(epsilon.getHP() - enemyBulletModel.getDamage());
            new Impact(enemyBulletModel.getGame() ,enemyBulletModel.getPosition() , DistanceConstants.REGULAR_IMPACT_RANGE).MakeImpact();
            enemyBulletModel.die();
            }
        }
        if (object instanceof CollisionDetector){
            ((CollisionDetector) object).detect();
        }
        if (object instanceof PortalModel) {
            ModelRequestController.portalWindow();
        }
    }

    private void epsilonEnemyMeleeHandler(EpsilonModel epsilon, EnemyModel enemy) {
        Random random = new Random();
        int attackChance = random.nextInt(1 ,101);
        if (attackChance >= epsilon.getChanceOfSurvival())
            (enemy).meleeAttack(epsilon);
        epsilon.meleeAttack(enemy);
    }


    private void pullOutObject(ObjectModel attacker, ObjectModel defender) {
        Vector attackerP = Math.VectorAdd(Math.ScalarInVector(-1, collisionPoint), attacker.getPosition());
        attackerP = Math.VectorWithSize(attackerP, 1);
        while (Collision.IsColliding(attacker, defender)) {
            attacker.setPosition(Math.VectorAdd(attackerP, attacker.getPosition()));
            if (attacker instanceof HasVertices){
                ((HasVertices) attacker).UpdateVertices(attackerP.x ,attackerP.y ,0);
            }
        }
    }

}
