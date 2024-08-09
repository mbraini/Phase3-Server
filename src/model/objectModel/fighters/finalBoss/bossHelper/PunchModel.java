package model.objectModel.fighters.finalBoss.bossHelper;

import constants.ImageConstants;
import constants.SizeConstants;
import controller.game.onlineGame.Game;
import controller.game.enums.ModelType;
import controller.game.player.Player;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.logics.MovementManager;
import model.objectModel.frameModel.FrameModelBuilder;
import utils.Math;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class PunchModel extends BossHelperModel implements IsPolygon, HasVertices {

    private ArrayList<Vector> vertices;

    public PunchModel(Game game , Player chasingPlayer , ArrayList<Player> targetedPlayers , Vector position , String id){
        super(game ,chasingPlayer ,targetedPlayers);
        this.position = position;
        this.id = id;
        this.image = ImageConstants.punch;
        this.velocity = new Vector();
        size = new Dimension(
                SizeConstants.PUNCH_DIMENSION.width,
                SizeConstants.PUNCH_DIMENSION.height
        );
        type = ModelType.punch;
        movementManager = new MovementManager();
        HP = 100000;
        this.acceleration = new Vector();
        initFrame();
        initVertices();
    }

    private void initVertices() {
        vertices = new ArrayList<>();
        vertices.add(getFrame().getPosition().clone());
        vertices.add(Math.VectorAdd(
                getFrame().getPosition(),
                new Vector(SizeConstants.PUNCH_DIMENSION.width ,0)
        ));
        vertices.add(Math.VectorAdd(
                getFrame().getPosition(),
                new Vector(SizeConstants.PUNCH_DIMENSION.width , SizeConstants.PUNCH_DIMENSION.height)
        ));
        vertices.add(Math.VectorAdd(
                getFrame().getPosition(),
                new Vector(0 , SizeConstants.PUNCH_DIMENSION.height)
        ));
    }


    @Override
    protected void initFrame() {
        FrameModelBuilder builder = new FrameModelBuilder(
                game,
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.PUNCH_DIMENSION.width / 2d,
                                -SizeConstants.PUNCH_DIMENSION.height / 2d
                        )
                ),
                new Dimension(SizeConstants.PUNCH_DIMENSION),
                id
        );
        builder.setIsometric(true);
        frame = builder.create();
    }


    @Override
    public void UpdateVertices(double xMoved, double yMoved, double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(vertices.get(i).getX() + xMoved ,vertices.get(i).getY() + yMoved));
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }
}
