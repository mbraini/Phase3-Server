package model.objectModel.fighters.finalBoss.abilities;

import model.objectModel.fighters.EpsilonModel;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.abilities.powerPunch.PowerPunch;
import model.objectModel.fighters.finalBoss.abilities.projectile.Projectile;
import model.objectModel.fighters.finalBoss.abilities.quake.Quake;
import model.objectModel.fighters.finalBoss.abilities.rapidFire.RapidFire;
import model.objectModel.fighters.finalBoss.abilities.slap.Slap;
import model.objectModel.fighters.finalBoss.abilities.squeeze.Squeeze;
import model.objectModel.fighters.finalBoss.abilities.vomit.Vomit;
import model.objectModel.frameModel.FrameModel;

public class AbilityCaster {

    private AbilityType abilityType;
    private Boss boss;
    private FrameModel epsilonFrame;
    private EpsilonModel epsilonModel;

    public AbilityCaster(Boss boss ,FrameModel epsilonFrame ,EpsilonModel epsilonModel){
        this.boss = boss;
        this.epsilonFrame = epsilonFrame;
        this.epsilonModel = epsilonModel;
    }

    public void cast(){
        switch (abilityType){
            case squeeze :
                new Squeeze(boss ,epsilonFrame).activate();
                break;
            case projectile:
                new Projectile(boss).activate();
                break;
            case vomit:
                new Vomit(boss ,epsilonFrame).activate();
                break;
            case powerPunch:
                new PowerPunch(boss ,epsilonFrame).activate();
                break;
            case rapidFire:
                new RapidFire(boss).activate();
                break;
            case slap:
                new Slap(boss ,epsilonModel).activate();
                break;
            case quake:
                new Quake(boss ,epsilonFrame).activate();
                break;
        }
    }

    public boolean canCast(){
        switch (abilityType){
            case squeeze :
                if (!boss.getLeftHand().isInUse() && !boss.getRightHand().isInUse() && !boss.getLeftHand().isDead() && !boss.getRightHand().isDead())
                    return true;
                break;
            case projectile:
                if (!boss.getHead().isInUse() && (!boss.getLeftHand().isDead() || !boss.getRightHand().isDead()))
                    return true;
                break;
            case vomit:
                if (!boss.getHead().isInUse())
                    return true;
                break;
            case powerPunch:
                if (!boss.getPunch().isInUse())
                    return true;
                break;
            case rapidFire:
                if (!boss.getHead().isInUse())
                    return true;
                break;
            case slap:
                if (
                        (!boss.getLeftHand().isInUse() && !boss.getLeftHand().isDead())
                                || (!boss.getRightHand().isInUse() && !boss.getRightHand().isDead())
                                || !boss.getPunch().isInUse()
                )
                    return true;
                break;
            case quake:
                if (!boss.getPunch().isInUse())
                    return true;
                break;
            case null:
                return false;
        }
        return false;
    }


    public AbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }
}
