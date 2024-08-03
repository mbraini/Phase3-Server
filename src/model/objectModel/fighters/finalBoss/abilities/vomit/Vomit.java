package model.objectModel.fighters.finalBoss.abilities.vomit;

import constants.ControllerConstants;
import controller.game.manager.Spawner;
import model.objectModel.fighters.finalBoss.Boss;
import model.objectModel.fighters.finalBoss.abilities.Ability;
import model.objectModel.frameModel.FrameModel;
import utils.Helper;

import java.util.ArrayList;

public class Vomit extends Ability {

    private Boss boss;
    private VomitThread thread;
    private ArrayList<BossAoeEffectModel> effects = new ArrayList<>();

    public Vomit(Boss boss , FrameModel epsilonFrame){
        this.boss = boss;
        thread = new VomitThread(this ,epsilonFrame);
    }


    @Override
    protected void setUp() {
        ownHelper(boss.getHead());
        boss.getHead().setVulnerableToEpsilonBullet(true);
    }

    @Override
    protected void unsetUp() {
        disownHelper(boss.getHead());
        boss.getHead().setVulnerableToEpsilonMelee(false);
        boss.getHead().setVulnerableToEpsilonBullet(false);
    }

    @Override
    public void activate() {
        super.activate();
        thread.start();
    }

    @Override
    protected void endAbility() {
        super.endAbility();
        thread.interrupt();
    }

    public void addEffect(FrameModel epsilonFrame){
        BossAoeEffectModel effectModel = new BossAoeEffectModel(
                Helper.createRandomPosition(epsilonFrame ,true),
                thread,
                this,
                Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
        );
        Spawner.addBossEffect(effectModel);
        synchronized (effects) {
            effects.add(effectModel);
        }
    }

    public ArrayList<BossAoeEffectModel> getEffects() {
        return effects;
    }

    public void removeEffect(String id) {
        for (BossAoeEffectModel effectModel : effects){
            if (effectModel.getId().equals(id)){
                effects.remove(effectModel);
                return;
            }
        }
    }
}
