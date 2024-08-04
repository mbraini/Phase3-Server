package controller.game.manager;

import constants.SizeConstants;
import controller.game.Game;
import controller.game.ModelRequestController;
import controller.game.enums.ModelType;
import constants.ControllerConstants;
import model.ModelRequests;
import model.animations.BossSpawnAnimation;
import model.inGameAbilities.Dismay.EpsilonProtectorModel;
import model.objectModel.CollectiveModel;
import model.objectModel.PortalModel;
import model.objectModel.effects.ArchmireAoeEffectModel;
import model.objectModel.effects.BlackOrbAoeEffectModel;
import model.objectModel.fighters.EpsilonModel;
import model.objectModel.fighters.EpsilonVertexModel;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.abilities.vomit.BossAoeEffectModel;
import model.objectModel.fighters.finalBoss.bossHelper.HandModel;
import model.objectModel.fighters.finalBoss.bossHelper.HeadModel;
import model.objectModel.fighters.finalBoss.bossHelper.PunchModel;
import model.objectModel.fighters.miniBossEnemies.barricadosModel.BarricadosFirstModel;
import model.objectModel.fighters.miniBossEnemies.barricadosModel.BarricadosSecondModel;
import model.objectModel.frameModel.FrameModel;
import model.objectModel.fighters.basicEnemies.SquarantineModel;
import model.objectModel.fighters.basicEnemies.TrigorathModel;
import model.objectModel.fighters.miniBossEnemies.blackOrbModel.BlackOrbModel;
import model.objectModel.fighters.miniBossEnemies.blackOrbModel.OrbModel;
import model.objectModel.fighters.normalEnemies.archmireModel.ArchmireModel;
import model.objectModel.fighters.normalEnemies.necropickModel.NecropickModel;
import model.objectModel.fighters.normalEnemies.omenoctModel.OmenoctModel;
import model.objectModel.fighters.normalEnemies.wyrmModel.WyrmModel;
import model.objectModel.projectiles.*;
import model.skillTreeAbilities.Cerberus.CerberusModel;
import utils.Helper;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public abstract class Spawner {

    public synchronized static void addFrame(FrameModel frameModel){
        frameModel.getGame().getModelRequests().addFrameModel(frameModel);
    }

    public synchronized static void spawnObjectWithId(Game game ,Vector position, ModelType modelType, String id){
        switch (modelType) {
            case epsilon:
                game.getModelRequests().addObjectModel(new EpsilonModel(game ,position, id));
                break;
            case trigorath:
                game.getModelRequests().addObjectModel(new TrigorathModel(game ,position, id));
                break;
            case squarantine:
                game.getModelRequests().addObjectModel(new SquarantineModel(game ,position, id));
                break;
            case omenoct:
                game.getModelRequests().addObjectModel(new OmenoctModel(game ,position, id));
                break;
            case necropick:
                game.getModelRequests().addObjectModel(new NecropickModel(game ,position ,id));
                break;
            case archmire:
                game.getModelRequests().addObjectModel(new ArchmireModel(game ,position ,id));
                break;
            case wyrm:
                WyrmModel wyrmModel = new WyrmModel(game ,position ,id);
                game.getModelRequests().addObjectModel(wyrmModel);
                addFrame(wyrmModel.getFrameModel());
                break;
            case blackOrb :
                BlackOrbModel blackOrbModel = new BlackOrbModel(
                        game,
                        position,
                        id
                );
                game.getModelRequests().addAbstractEnemy(blackOrbModel);
                blackOrbModel.spawn();
                break;
            case barricados:
                int randomInteger = (new Random()).nextInt(0 ,2);
                if (randomInteger == 0){
                    BarricadosFirstModel barricadosModel = new BarricadosFirstModel(game ,position ,id);
                    game.getModelRequests().addObjectModel(barricadosModel);
                }
                else {
                    BarricadosSecondModel barricadosModel = new BarricadosSecondModel(game ,position ,id);
                    game.getModelRequests().addObjectModel(barricadosModel);
                    addFrame(barricadosModel.getFrameModel());
                }
                break;
            case cerberus:
                game.getModelRequests().addObjectModel(new CerberusModel(game ,position ,id));
                 break;
        }
    }

    public synchronized static void spawnObject(Game game ,Vector position , ModelType modelType){
        spawnObjectWithId(game ,position , modelType,Helper.RandomStringGenerator(ControllerConstants.ID_SIZE));
    }



    public synchronized static void addProjectile(Game game ,Vector position , Vector direction , ModelType modelType){
        String id = Helper.RandomStringGenerator(ControllerConstants.ID_SIZE);
        addProjectileWithId(game ,position ,direction , modelType,id);
    }

    public synchronized static void addProjectileWithId(Game game ,Vector position , Vector direction , ModelType modelType, String id){
        switch (modelType) {
            case epsilonBullet:
                game.getModelRequests().addObjectModel(new EpsilonBulletModel(
                        game,
                        position,
                        direction,
                        id
                        )
                );
                break;
            case omenoctBullet:
                game.getModelRequests().addObjectModel(new OmenoctBulletModel(
                        game,
                        position,
                        direction,
                        id
                        )
                );
                break;
            case necropickBullet:
                game.getModelRequests().addObjectModel(new NecropickBulletModel(game, position ,direction ,id));
                break;
            case wyrmBullet:
                game.getModelRequests().addObjectModel(new WyrmBulletModel(game ,position ,direction ,id));
                break;
            case bossBullet:
                game.getModelRequests().addObjectModel(new BossBulletModel(game ,position ,direction ,id));
                break;
            case slaughterBullet:
                game.getModelRequests().addObjectModel(new SlaughterBulletModel(game ,position, direction ,id));
                break;
        }
    }

    public synchronized static void addArchmireEffect(Game game ,ArchmireAoeEffectModel archmireEffectModel){
        String id = archmireEffectModel.getId();
        game.getModelRequests().addEffectModel(archmireEffectModel);
    }

    public static void addBlackOrbEffectModel(Game game ,BlackOrbAoeEffectModel effectModel) {
        game.getModelRequests().addEffectModel(effectModel);
    }

    public static void addCollectives(Game game ,Vector position ,int count ,int value){
        Random random = new Random();
        for (int i = 0; i < count; i++){
            int x = random.nextInt(
                    (int) position.x - SizeConstants.COLLECTIVE_BOX_DIMENSION.width ,
                    (int) position.x + SizeConstants.COLLECTIVE_BOX_DIMENSION.width
            );
            int y = random.nextInt(
                    (int) position.y - SizeConstants.COLLECTIVE_BOX_DIMENSION.height ,
                    (int) position.y + SizeConstants.COLLECTIVE_BOX_DIMENSION.height
            );
            addCollective(game ,new Vector(x ,y) ,value);
        }
    }

    private static void addCollective(Game game ,Vector position, int value) {
        String id = Helper.RandomStringGenerator(ControllerConstants.ID_SIZE);
        game.getModelRequests().addObjectModel(new CollectiveModel(game ,position,id ,value));
    }

    public static void spawnBoss(Game game){
        ModelRequestController.killEveryThing();
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boss boss = new Boss(game ,Helper.RandomStringGenerator(ControllerConstants.ID_SIZE));
                BossSpawnAnimation bossSpawnAnimation = new BossSpawnAnimation(boss);
                bossSpawnAnimation.StartAnimation();
                game.getModelRequests().addAbstractEnemy(boss);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }


    public synchronized static void spawnHead(HeadModel head) {
        head.getGame().getModelRequests().addObjectModel(head);

        head.getGame().getModelRequests().addFrameModel(head.getFrame());
    }

    public synchronized static void spawnHand(HandModel hand){
        hand.getGame().getModelRequests().addObjectModel(hand);
        hand.getGame().getModelRequests().addFrameModel(hand.getFrame());
    }

    public static void addBossEffect(Game game ,BossAoeEffectModel effectModel) {
        game.getModelRequests().addEffectModel(effectModel);
    }

    public static void addPunch(PunchModel punch) {
        punch.getGame().getModelRequests().addObjectModel(punch);

        punch.getGame().getModelRequests().addFrameModel(punch.getFrame());
    }

    public static void spawnOrb(Game game ,Vector position, BlackOrbModel blackOrbModel, int number, String id) {
        blackOrbModel.getGame().getModelRequests().addObjectModel(new OrbModel(
                game,
                position,
                blackOrbModel,
                number,
                id
        ));
    }

    public static synchronized void spawnProtector(EpsilonProtectorModel protectorModel) {
        protectorModel.getGame().getModelRequests().addObjectModel(protectorModel);
    }

    public static void spawnVertex(EpsilonVertexModel epsilonVertexModel) {
        epsilonVertexModel.getGame().getModelRequests().addObjectModel(epsilonVertexModel);
    }
}
