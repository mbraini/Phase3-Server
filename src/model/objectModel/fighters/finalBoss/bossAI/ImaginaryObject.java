package model.objectModel.fighters.finalBoss.bossAI;

import controller.game.Game;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.objectModel.ObjectModel;
import utils.Vector;

import java.util.ArrayList;

public class ImaginaryObject extends ObjectModel implements IsPolygon {

    private ArrayList<Vector> vertices;

    public ImaginaryObject(Game game ,ArrayList<Vector> vertices) {
        super(game);
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
