package model.objectModel.fighters.normalEnemies.necropickModel;

import constants.RefreshRateConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.game.manager.GameState;
import controller.game.manager.Spawner;
import controller.online.annotations.SkippedByJson;
import model.ModelData;
import model.interfaces.Ability;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.movementIntefaces.MoveAble;
import model.objectModel.fighters.normalEnemies.NormalEnemyModel;
import utils.Math;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NecropickModel extends NormalEnemyModel implements MoveAble ,Ability {
    @SkippedByJson
    private Timer hoveringTimer;
    @SkippedByJson
    private Timer abilityTimer;
    private double hoveringTime;
    private double abilityTime;
    private boolean hasAbility;
    private ArrayList<Vector> vertices;
    private boolean isArrived;

    public NecropickModel(Game game ,Vector position , String id){
        super(game);
        this.position = position;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        this.id = id;
        this.HP = 10;
        type = ModelType.necropick;
        vulnerableToEpsilonBullet = true;
        vulnerableToEpsilonMelee = true;
        initVertices();
        start();
    }

    public void start() {
        hoveringTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getGameState().isPause() || game.getGameState().isDizzy())
                    return;
                if (game.getGameState().isOver()) {
                    hoveringTimer.stop();
                    return;
                }
                if (!isHovering())
                    hoveringTime += 1000;
                if (hoveringTime >= 8000) {
                    hoveringTime = 0;
                    setHovering(true);
                    hasAbility = true;
                    isMotionless = true;
                    abilityTimer.start();
                    hoveringTimer.stop();
                }
            }
        });
        abilityTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getGameState().isPause() || game.getGameState().isDizzy())
                    return;
                if (game.getGameState().isOver()) {
                    abilityTimer.stop();
                    return;
                }
                if (isHovering())
                    abilityTime += 1000;
                if (abilityTime >= 4000) {
                    abilityTime = 0;
                    isMotionless = false;
                    new NecropickShooter(game ,position).shoot();
                    setHovering(false);
                    hasAbility = false;
                    velocity = new Vector();
                    hoveringTimer.start();
                    abilityTimer.stop();
                }
            }
        });
        if (abilityTime == 0)
            hoveringTimer.start();
        else
            abilityTimer.start();
    }


    @Override
    public void die() {
        super.die();
        abilityTimer.stop();
        hoveringTimer.stop();
        Spawner.addCollectives(game ,position ,4 ,2);
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
        if (this instanceof HasVertices)
            ((HasVertices) this).UpdateVertices(xMoved ,yMoved ,thetaMoved);
    }

    @Override
    public void ability() {
        NecropickNavigator navigator = new NecropickNavigator(
                position,
                game.getModelData().getModels().getFirst().getPosition()
        );
        navigator.navigate();
        setVelocity(navigator.getVelocity());
    }

    @Override
    public boolean hasAbility() {
        return hasAbility;
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    public void initVertices(){
        vertices = new ArrayList<>();
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                SizeConstants.NECROPICK_DIMENSION.width /2d,
                                SizeConstants.NECROPICK_DIMENSION.height / 2d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                SizeConstants.NECROPICK_DIMENSION.width /2d,
                                -SizeConstants.NECROPICK_DIMENSION.height / 5d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                0,
                                -SizeConstants.NECROPICK_DIMENSION.height / 2d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.NECROPICK_DIMENSION.width /2d,
                                -SizeConstants.NECROPICK_DIMENSION.height / 5d
                        )
                )
        );
        vertices.add(
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.NECROPICK_DIMENSION.width /2d,
                                SizeConstants.NECROPICK_DIMENSION.height / 2d
                        )
                )
        );
    }

    @Override
    public void UpdateVertices(double xMoved ,double yMoved ,double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(vertices.get(i).getX() + xMoved ,vertices.get(i).getY() + yMoved));
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }
}
