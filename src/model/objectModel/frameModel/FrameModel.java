package model.objectModel.frameModel;

import controller.game.ObjectController;
import model.interfaces.collisionInterfaces.HasVertices;
import model.interfaces.collisionInterfaces.IsPolygon;
import model.logics.MovementManager;
import model.objectModel.ObjectModel;
import utils.Math;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class FrameModel extends ObjectModel implements IsPolygon , HasVertices {

    private Dimension size;
    private Vector upDownA = new Vector();
    private Vector leftRightA = new Vector();
    private Vector upDownV = new Vector();
    private Vector leftRightV = new Vector();
    private Vector upDownP = new Vector();
    private Vector leftRightP = new Vector();
    private Vector positionInit;
    private ArrayList<Vector> vertices;
    private Dimension dimensionInit;
    private boolean isResizing;
    private boolean isIsometric;
    private boolean canTopResize = true;
    private boolean canBottomResize = true;
    private boolean canRightResize = true;
    private boolean canLeftResize = true;

    public FrameModel(Vector positionInit ,Dimension dimensionInit ,String id){
        this.position = positionInit.clone();
        this.size = dimensionInit;
        this.id = id;
        this.positionInit = positionInit.clone();
        this.dimensionInit = dimensionInit;
        movementManager = new MovementManager();
        initVertices();
    }

    private void initVertices() {
        vertices = new ArrayList<>();
        vertices.add(new Vector(position.x ,position.y));
        vertices.add(new Vector(position.x + size.width ,position.y));
        vertices.add(new Vector(position.x + size.width ,position.y + size.height));
        vertices.add(new Vector(position.x ,position.y + size.height));
    }


    @Override
    public void die() {
        ObjectController.removeFrame(this);
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void resize() {
        new FrameResizer(this).resize();
    }

    public void revalidate(Vector upDownMoved ,Vector leftRightMoved) {
        setSize(new Dimension(
                (int)(leftRightP.y + leftRightP.x + dimensionInit.width) ,
                (int)(upDownP.y + upDownP.x + dimensionInit.height))
        );
        setPosition(new Vector(-leftRightP.x + positionInit.x, -upDownP.x + positionInit.y));
        transferVertices(upDownMoved ,leftRightMoved);
    }

    private void transferVertices(Vector upDownMoved, Vector leftRightMoved) {
        vertices.set(0 ,
                Math.VectorAdd(
                        vertices.get(0) ,
                        new Vector(-leftRightMoved.x ,-upDownMoved.x)
                )
        );
        vertices.set(1 ,
                Math.VectorAdd(
                        vertices.get(1) ,
                        new Vector(leftRightMoved.y ,-upDownMoved.x)
                )
        );
        vertices.set(2 ,
                Math.VectorAdd(
                        vertices.get(2) ,
                        new Vector(leftRightMoved.y ,upDownMoved.y)
                )
        );
        vertices.set(3 ,
                Math.VectorAdd(
                        vertices.get(3) ,
                        new Vector(-leftRightMoved.x ,upDownMoved.y)
                )
        );
    }

    public void transfer(Vector newPosition){
        new FrameTransferer(this).transfer(newPosition);
    }

    public Vector getUpDownV() {
        return upDownV;
    }

    public void setUpDownV(Vector upDownV) {
        this.upDownV = upDownV;
    }

    public Vector getLeftRightV() {
        return leftRightV;
    }

    public void setLeftRightV(Vector leftRightV) {
        this.leftRightV = leftRightV;
    }

    public void setUpDownV(double x , double y) {
        this.upDownV = new Vector(x ,y);
    }

    public void setLeftRightV(double x , double y) {
        this.leftRightV = new Vector(x ,y);
    }

    public Vector getUpDownP() {
        return upDownP;
    }

    public void setUpDownP(Vector upDownP) {
        this.upDownP = upDownP;
    }

    public Vector getLeftRightP() {
        return leftRightP;
    }

    public void setLeftRightP(Vector leftRightP) {
        this.leftRightP = leftRightP;
    }

    public void setUpDownP(double x ,double y) {
        this.upDownP = new Vector(x ,y);
    }

    public void setLeftRightP(double x ,double y) {
        this.leftRightP = new Vector(x ,y);
    }

    public Vector getUpDownA() {
        return upDownA;
    }

    public void setUpDownA(Vector upDownA) {
        this.upDownA = upDownA;
    }

    public Vector getLeftRightA() {
        return leftRightA;
    }

    public void setLeftRightA(Vector leftRightA) {
        this.leftRightA = leftRightA;
    }
    public void setLeftRightA(double x ,double y){
        leftRightA = new Vector(x ,y);
    }
    public void setUpDownA(double x ,double y){
        upDownA = new Vector(x ,y);
    }


    public boolean isResizing() {
        return isResizing;
    }

    public void setResizing(boolean resizing) {
        isResizing = resizing;
    }


    public boolean isIsometric() {
        return isIsometric;
    }

    public void setIsometric(boolean isometric) {
        isIsometric = isometric;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }


    public boolean canTopResize() {
        return canTopResize;
    }

    public void setCanTopResize(boolean canTopResize) {
        this.canTopResize = canTopResize;
    }

    public boolean canBottomResize() {
        return canBottomResize;
    }

    public void setCanBottomResize(boolean canBottomResize) {
        this.canBottomResize = canBottomResize;
    }

    public boolean canRightResize() {
        return canRightResize;
    }

    public void setCanRightResize(boolean canRightResize) {
        this.canRightResize = canRightResize;
    }

    public boolean canLeftResize() {
        return canLeftResize;
    }

    public void setCanLeftResize(boolean canLeftResize) {
        this.canLeftResize = canLeftResize;
    }

    public Vector getPositionInit() {
        return positionInit;
    }

    public void setPositionInit(Vector positionInit) {
        this.positionInit = positionInit;
    }

    public Dimension getDimensionInit() {
        return dimensionInit;
    }

    public void setDimensionInit(Dimension dimensionInit) {
        this.dimensionInit = dimensionInit;
    }

    @Override
    public void UpdateVertices(double xMoved, double yMoved, double theta) {
        for (int i = 0 ;i < vertices.size() ;i++){
            vertices.set(i ,new Vector(vertices.get(i).getX() + xMoved ,vertices.get(i).getY() + yMoved));
        }
    }

    @Override
    public ArrayList<Vector> getVertices() {
        return vertices;
    }
}
