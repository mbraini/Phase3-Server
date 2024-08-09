package model.objectModel.fighters.normalEnemies.archmireModel;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import constants.VelocityConstants;
import controller.game.onlineGame.Game;
import controller.game.onlineGame.ObjectController;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import model.interfaces.Ability;
import model.interfaces.movementIntefaces.MoveAble;
import model.objectModel.effects.ArchmireAoeEffectModel;
import model.objectModel.fighters.normalEnemies.NormalEnemyModel;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class ArchmireModel extends NormalEnemyModel implements MoveAble , Ability {
    @SkippedByJson
    private ArchmireThread thread;
    private ArrayList<Vector> vertices = new ArrayList<>();
    private ArrayList<ArchmireAoeEffectModel> aoeEffects = new ArrayList<>();

    public ArchmireModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers , Vector position , String id){
        super(game ,chasingPlayer ,targetedPlayers);
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.id = id;
        this.HP = 30;
        type = ModelType.archmire;
        setHovering(true);
        vulnerableToEpsilonBullet = true;
        omega = VelocityConstants.ENEMY_ROTATION_SPEED * game.getGameSpeed();
        initVertices();
        start();
    }

    public void initVertices() {
        vertices = new ArrayList<>();
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                0,
                                SizeConstants.ARCHMIRE_DIMENSION.height /2d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                SizeConstants.ARCHMIRE_DIMENSION.width * 18 / 40d,
                                SizeConstants.ARCHMIRE_DIMENSION.height * 9 / 32d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                SizeConstants.ARCHMIRE_DIMENSION.width * 18 / 40d,
                                -SizeConstants.ARCHMIRE_DIMENSION.height / 32d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                SizeConstants.ARCHMIRE_DIMENSION.width * 13 / 40d,
                                -SizeConstants.ARCHMIRE_DIMENSION.height * 10 / 32d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                0,
                                -SizeConstants.ARCHMIRE_DIMENSION.height / 2d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.ARCHMIRE_DIMENSION.width * 13 / 40d,
                                -SizeConstants.ARCHMIRE_DIMENSION.height * 10 / 32d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.ARCHMIRE_DIMENSION.width * 18 / 40d,
                                -SizeConstants.ARCHMIRE_DIMENSION.height / 32d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.ARCHMIRE_DIMENSION.width * 18 / 40d,
                                SizeConstants.ARCHMIRE_DIMENSION.height * 9 / 32d
                        )
                )
        );
    }


    @Override
    public void die() {
        super.die();
        Spawner.addCollectives(game ,targetedPlayers ,position ,5 ,6);
        thread.interrupt();
    }

    @Override
    public void move() {
        velocity = Math.VectorAdd(velocity ,Math.ScalarInVector(RefreshRateConstants.UPS ,acceleration));
        double xMoved = ((2 * velocity.x - acceleration.x * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        double yMoved = ((2 * velocity.y - acceleration.y * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        setPosition(position.x + xMoved ,position.y + yMoved);


        omega += alpha * RefreshRateConstants.UPS;
        double thetaMoved = ((2 * omega - alpha * RefreshRateConstants.UPS) / 2) * RefreshRateConstants.UPS;
        theta = theta + thetaMoved;
        UpdateVertices(xMoved ,yMoved ,thetaMoved);
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    @Override
    public void ability() {
        synchronized (game.getModelData().getModels()) {
            Vector epsilonPosition = game.getModelData().getModels().getFirst().getPosition();
            velocity = Math.VectorWithSize(Math.VectorAdd(
                    epsilonPosition,
                    Math.ScalarInVector(-1 ,position)
            ), VelocityConstants.ARCHMIRE_VELOCITY * game.getGameSpeed());
        }
    }

    @Override
    public boolean hasAbility() {
        return true;
    }

    @Override
    public void UpdateVertices(double xMoved, double yMoved, double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(vertices.get(i).getX() + xMoved ,vertices.get(i).getY() + yMoved));
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }

    public ArrayList<ArchmireAoeEffectModel> getAoeEffects() {
        return aoeEffects;
    }

    public synchronized void killEffect(ArchmireAoeEffectModel archmireAoeEffectModel) {
        thread.addRemovedAoe(archmireAoeEffectModel.getId());
        ObjectController.removeEffect(archmireAoeEffectModel);
    }

    public void start() {
        thread = new ArchmireThread(this);
        thread.start();
    }
}
