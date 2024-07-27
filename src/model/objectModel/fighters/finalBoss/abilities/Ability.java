package model.objectModel.fighters.finalBoss.abilities;

import model.objectModel.fighters.finalBoss.bossHelper.BossHelperModel;

public abstract class Ability {

    protected void ownHelper(BossHelperModel helper) {
        helper.setInUse(true);
    }
    protected void disownHelper(BossHelperModel helper) {
        helper.setInUse(false);
    }
    protected abstract void setUp();
    protected abstract void unsetUp();
    public void activate() {
        setUp();
    }
    protected void endAbility() {
        unsetUp();
    }

}
