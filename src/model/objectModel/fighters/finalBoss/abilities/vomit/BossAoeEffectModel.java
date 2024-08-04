package model.objectModel.fighters.finalBoss.abilities.vomit;

import constants.DistanceConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.ObjectController;
import controller.online.annotations.SkippedByJson;
import model.interfaces.Fader;
import model.interfaces.collisionInterfaces.IsCircle;
import model.logics.Impact;
import model.objectModel.effects.AoeEffectModel;
import utils.Vector;
import utils.area.Circle;

public class BossAoeEffectModel extends AoeEffectModel implements Fader , IsCircle {
    private double time;
    @SkippedByJson
    private Vomit vomit;
    @SkippedByJson
    private VomitThread thread;

    public BossAoeEffectModel(Game game ,Vector center ,VomitThread thread ,Vomit vomit, String id){
        super(game);
        this.id = id;
        this.vomit = vomit;
        this.thread = thread;
        area = new Circle(SizeConstants.VOMIT_RADIOS ,center);
    }

    @Override
    public void die() {
        ObjectController.removeEffect(this);
        synchronized (vomit.getEffects()){
            vomit.removeEffect(id);
        }
        thread.dealDamage(this);
        new Impact(vomit.getBoss().getGame() ,((Circle)area).getCenter() , DistanceConstants.REGULAR_IMPACT_RANGE).MakeImpact();
    }

    @Override
    public void addTime(double time) {
        this.time += time;
    }

    @Override
    public void fadeIf() {
        if (time >= 3000)
            die();
    }

    @Override
    public double getRadios() {
        return SizeConstants.VOMIT_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return ((Circle) area).getCenter();
    }
}
