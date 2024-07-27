package utils;

public class Vector {
    public double x;
    public double y;
    public Vector(){
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x ,double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean Equals(Vector vector) {
        if (x == vector.x && y == vector.y)
            return true;
        return false;
    }

    public Vector clone(){
        return new Vector(x ,y);
    }

}
