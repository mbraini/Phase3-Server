package utils.area;

import utils.Vector;

public class Circle extends Area{
    private double r;
    private Vector center;
    public Circle(double r ,Vector center){
        this.r = r;
        this.center = center;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }
}
