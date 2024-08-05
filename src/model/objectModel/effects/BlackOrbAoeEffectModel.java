package model.objectModel.effects;

import constants.SizeConstants;
import controller.game.Game;
import controller.game.ObjectController;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.objectModel.fighters.miniBossEnemies.blackOrbModel.BlackOrbModel;
import model.objectModel.fighters.miniBossEnemies.blackOrbModel.OrbModel;
import utils.Math;
import utils.Vector;
import utils.area.Polygon;

import java.util.ArrayList;

public class BlackOrbAoeEffectModel extends AoeEffectModel implements IsPolygon , HasVertices {
    @SkippedByJson
    private BlackOrbModel blackOrbModel;
    private OrbModel orbDestination;
    private OrbModel orbOrigin;
    private ArrayList<Vector> vertices;


    public BlackOrbAoeEffectModel (Game game, ArrayList<Player> targetedPlayers , BlackOrbModel blackOrbModel , OrbModel orbOrigin , OrbModel orbDestination , String id){
        super(game ,targetedPlayers);
        this.id = id;
        R =255;
        G = 0;
        B = 255;
        this.orbOrigin = orbOrigin;
        this.orbDestination = orbDestination;
        this.blackOrbModel = blackOrbModel;
        initVertices();
        setUpArea();
    }

    private void initVertices() {
        vertices = new ArrayList<>();
        Vector direction = Math.VectorAdd(
                orbDestination.getPosition(),
                Math.ScalarInVector(-1 ,orbOrigin.getPosition())
        );

        Vector n1 = Math.NormalWithSize(direction , SizeConstants.ORB_DIMENSION.width / 2d);
        Vector n2 = Math.ScalarInVector(-1 ,n1);

        vertices.add(Math.VectorAdd(
                orbOrigin.getPosition(),
                n1
        ));
        vertices.add(Math.VectorAdd(
                orbDestination.getPosition(),
                n1
        ));
        vertices.add(Math.VectorAdd(
                orbDestination.getPosition(),
                n2
        ));
        vertices.add(Math.VectorAdd(
                orbOrigin.getPosition(),
                n2
        ));
    }

    private void setUpArea() {
        ArrayList<Integer> xs = new ArrayList<>();
        ArrayList<Integer> ys = new ArrayList<>();

        for (int i = 0 ;i < 4 ;i++){
            xs.add((int) vertices.get(i).x);
            ys.add((int) vertices.get(i).y);
        }

        area = new Polygon(xs ,ys ,4);
    }

    @Override
    public void die() {
        synchronized (blackOrbModel.getEffectModels()) {
            blackOrbModel.getEffectModels().remove(this);
            ObjectController.removeEffect(this);
        }
    }

    @Override
    public void UpdateVertices(double xMoves, double yMoved, double theta) {

    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    public OrbModel getOrbDestination() {
        return orbDestination;
    }

    public OrbModel getOrbOrigin() {
        return orbOrigin;
    }

    public void setBlackOrbModel(BlackOrbModel blackOrbModel) {
        this.blackOrbModel = blackOrbModel;
    }
}
