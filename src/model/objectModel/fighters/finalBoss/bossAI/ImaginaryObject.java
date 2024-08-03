package model.objectModel.fighters.finalBoss.bossAI;

import model.interfaces.collisionInterfaces.IsPolygon;
import model.objectModel.ObjectModel;
import utils.Vector;

import java.util.ArrayList;

public class ImaginaryObject extends ObjectModel implements IsPolygon {

    private ArrayList<Vector> vertices;

    public ImaginaryObject(ArrayList<Vector> vertices) {
        this.vertices = vertices;
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    @Override
    public void die() {

    }
}
