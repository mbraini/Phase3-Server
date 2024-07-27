package utils.area;

import java.util.ArrayList;

public class Polygon extends Area{
    int n;
    ArrayList<Integer> x;
    ArrayList<Integer> y;
    public Polygon(ArrayList<Integer> x ,ArrayList<Integer> y ,int n){
        this.x = x;
        this.y = y;
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public ArrayList<Integer> getX() {
        return x;
    }

    public void setX(ArrayList<Integer> x) {
        this.x = x;
    }

    public ArrayList<Integer> getY() {
        return y;
    }

    public void setY(ArrayList<Integer> y) {
        this.y = y;
    }
}
