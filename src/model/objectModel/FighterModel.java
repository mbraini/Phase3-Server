package model.objectModel;

public abstract class FighterModel extends ObjectModel{
    protected boolean hasMeleeAttack;
    protected int meleeAttack;


    public boolean isHasMeleeAttack() {
        return hasMeleeAttack;
    }

    public void setHasMeleeAttack(boolean hasMeleeAttack) {
        this.hasMeleeAttack = hasMeleeAttack;
    }

    public int getMeleeAttack() {
        return meleeAttack;
    }

    public void setMeleeAttack(int meleeAttack) {
        this.meleeAttack = meleeAttack;
    }
}
