package model.objectModel.fighters.finalBoss.bossAI;

import controller.game.Game;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.objectModel.ObjectModel;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class ImaginaryObject extends ObjectModel implements IsPolygon , HasVertices {

    private ArrayList<Vector> vertices;

    public ImaginaryObject(Game game ,ArrayList<Vector> vertices ,String id) {
        super(game);
        this.vertices = vertices;
        this.id = id;
        initPosition();
    }

    private void initPosition() {
        position = new Vector();
        for (Vector vector : vertices) {
            position = Math.VectorAdd(position ,vector);
        }
        position = Math.ScalarInVector(1d / vertices.size() ,position);
    }

    public ImaginaryObject(Game game ,ArrayList<Vector> vertices) {
        super(game);
        this.vertices = vertices;
        initPosition();
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    @Override
    public void die() {

    }

    @Override
    public void UpdateVertices(double xMoved, double yMoved, double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(
                    vertices.get(i).getX() + xMoved ,
                    vertices.get(i).getY() + yMoved)
            );
            vertices.set(i , Math.RotateByTheta(vertices.get(i) ,position ,theta));
        }
    }
}
