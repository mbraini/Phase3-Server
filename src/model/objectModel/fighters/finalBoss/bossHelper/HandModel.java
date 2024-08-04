package model.objectModel.fighters.finalBoss.bossHelper;

import constants.ImageConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.logics.MovementManager;
import model.objectModel.frameModel.FrameModelBuilder;
import utils.Math;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class HandModel extends BossHelperModel implements IsPolygon , HasVertices {

    private ArrayList<Vector> vertices;


    public HandModel(Game game ,Vector position , String id){
        super(game);
        this.position = position;
        this.id = id;
        this.image = ImageConstants.hand;
        this.velocity = new Vector();
        this.acceleration = new Vector();
        movementManager = new MovementManager();
        size = new Dimension(
                SizeConstants.HAND_DIMENSION.width,
                SizeConstants.HAND_DIMENSION.height
        );
        type = ModelType.hand;
        HP = 100;
        setHovering(true);
        initFrame();
        initVertices();
    }

    private void initVertices() {
        vertices = new ArrayList<>();
        vertices.add(getFrame().getPosition().clone());
        vertices.add(Math.VectorAdd(
                getFrame().getPosition(),
                new Vector(SizeConstants.HAND_DIMENSION.width ,0)
        ));
        vertices.add(Math.VectorAdd(
                getFrame().getPosition(),
                new Vector(SizeConstants.HAND_DIMENSION.width , SizeConstants.HAND_DIMENSION.height)
        ));
        vertices.add(Math.VectorAdd(
                getFrame().getPosition(),
                new Vector(0 , SizeConstants.HAND_DIMENSION.height)
        ));
    }


    @Override
    protected void initFrame() {
        FrameModelBuilder builder = new FrameModelBuilder(
                game,
                Math.VectorAdd(
                        position,
                        new Vector(
                                -SizeConstants.HAND_DIMENSION.width / 2d,
                                -SizeConstants.HAND_DIMENSION.height / 2d
                        )
                ),
                new Dimension(SizeConstants.HAND_DIMENSION),
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
