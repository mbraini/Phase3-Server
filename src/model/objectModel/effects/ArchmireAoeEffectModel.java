package model.objectModel.effects;

import constants.SizeConstants;
import controller.game.enums.EffectType;
import controller.online.annotations.SkippedByJson;
import model.interfaces.Fader;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.objectModel.fighters.normalEnemies.archmireModel.ArchmireModel;
import utils.Math;
import utils.Vector;
import utils.area.Polygon;

import java.util.ArrayList;

public class ArchmireAoeEffectModel extends AoeEffectModel implements Fader , IsPolygon , HasVertices {

    private double fadeTime;
    @SkippedByJson
    private ArchmireModel archmire;
    private ArrayList<Vector> vertices;


    public ArchmireAoeEffectModel(ArchmireModel archmire ,String id){
        this.id = id;
        this.G = 255;
        this.archmire = archmire;
        effectType = EffectType.archmireEffect;
        setUpArea(archmire);
        initVertices();
    }

    private void initVertices() {
        position = new Vector(
                (((Polygon)area).getX().getFirst() + ((Polygon)area).getX().getLast()) / 2d,
                (((Polygon)area).getY().getFirst() + ((Polygon)area).getY().getLast()) / 2d
        );
        vertices = new ArrayList<>();
        vertices.add(new Vector(
                position.x ,
                position.y - (java.lang.Math.sqrt(3) * SizeConstants.ARCHMIRE_DIMENSION.width / 3d))
        );
        vertices.add(new Vector(
                position.x - SizeConstants.ARCHMIRE_DIMENSION.width / 2d ,
                position.y + (java.lang.Math.sqrt(3) * SizeConstants.ARCHMIRE_DIMENSION.width / 6d))
        );
        vertices.add(new Vector(
                position.x + SizeConstants.ARCHMIRE_DIMENSION.width / 2d ,
                position.y + (java.lang.Math.sqrt(3) * SizeConstants.ARCHMIRE_DIMENSION.width / 6d))
        );
    }

    private void setUpArea(ArchmireModel archmire) {
        ArrayList<Vector> vertices = archmire.getVertices();
        ArrayList<Integer> xs = new ArrayList<>();
        ArrayList<Integer> ys = new ArrayList<>();
        for (int i = 0 ;i < vertices.size() ;i++){
            xs.add((int) vertices.get(i).x);
            ys.add((int) vertices.get(i).y);
        }
        area = new Polygon(xs ,ys ,vertices.size());
    }


    @Override
    public void die() {
        archmire.killEffect(this);
    }


    @Override
    public void addTime(double time) {
        if (time >= SizeConstants.ARCHMIRE_AOE_TIME_LIMIT)
            return;
        int G;
        G =(int) (255 - (this.fadeTime / SizeConstants.ARCHMIRE_AOE_TIME_LIMIT) * 255);
        this.G = G;
        fadeTime += time;
    }

    @Override
    public void fadeIf() {
        if (fadeTime >= SizeConstants.ARCHMIRE_AOE_TIME_LIMIT)
            die();
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

    public void setArchmire(ArchmireModel archmire) {
        this.archmire = archmire;
    }
}
