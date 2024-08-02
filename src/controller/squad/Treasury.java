package controller.squad;

import java.util.HashMap;

public class Treasury {

    private int palioxisCount;
    private int adonisCount;
    private int gefjonCount;
    private int xp;
    private HashMap<String ,Integer> donateMap;

    public Treasury() {
        donateMap = new HashMap<>();
    }

    public int getPalioxisCount() {
        return palioxisCount;
    }

    public void setPalioxisCount(int palioxisCount) {
        this.palioxisCount = palioxisCount;
    }

    public int getAdonisCount() {
        return adonisCount;
    }

    public void setAdonisCount(int adonisCount) {
        this.adonisCount = adonisCount;
    }

    public int getGefjonCount() {
        return gefjonCount;
    }

    public void setGefjonCount(int gefjonCount) {
        this.gefjonCount = gefjonCount;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
