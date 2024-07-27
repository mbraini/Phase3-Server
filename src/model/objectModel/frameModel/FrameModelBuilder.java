package model.objectModel.frameModel;

import model.objectModel.frameModel.FrameModel;
import utils.Vector;

import java.awt.*;

public class FrameModelBuilder {
    private Vector positionInit;
    private Dimension dimensionInit;
    private String id;
    private boolean isometric;
    private boolean solid;
    public FrameModelBuilder(Vector positionInit , Dimension dimensionInit ,String id){
        this.positionInit = positionInit;
        this.dimensionInit = dimensionInit;
        this.id = id;
    }


    public void setIsometric(boolean isometric){
        this.isometric = isometric;
    }

    public void setSolid(boolean solid){
        this.solid = solid;
    }

    public FrameModel create(){
        FrameModel frameModel = new FrameModel(positionInit ,dimensionInit ,id);
        frameModel.setSolid(solid);
        frameModel.setIsometric(isometric);
        return frameModel;
    }

    public void reset(){
        positionInit = new Vector();
        dimensionInit = new Dimension();
        id = "";
        isometric = false;
        solid = false;
    }

    public void setPositionInit(Vector positionInit) {
        this.positionInit = positionInit;
    }

    public void setDimensionInit(Dimension dimensionInit) {
        this.dimensionInit = dimensionInit;
    }

    public void setId(String id) {
        this.id = id;
    }
}
