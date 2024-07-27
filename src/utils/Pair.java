package utils;

import java.util.ArrayList;

public class Pair {
    int a;
    int b;
    public Pair(int a , int b){
        this.a = a;
        this.b = b;
    }

    public boolean Eguals(Pair pair){
        if ((pair.a == a && pair.b == b) || (pair.b == a && pair.a == b))
            return true;
        return false;
    }

    public static boolean Contains(ArrayList<Pair> pairs ,Pair pair){
        for (int k = 0 ;k < pairs.size() ;k++){
            if (pairs.get(k).Eguals(pair))
                return true;
        }
        return false;
    }

}
