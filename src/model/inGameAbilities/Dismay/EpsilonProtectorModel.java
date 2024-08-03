package model.inGameAbilities.Dismay;

import constants.SizeConstants;
import controller.game.ObjectController;
import controller.game.enums.ModelType;
import model.interfaces.Ability;
import model.interfaces.collisionInterfaces.IsCircle;
import model.objectModel.ObjectModel;
import model.objectModel.fighters.EpsilonModel;
import utils.Vector;

public class EpsilonProtectorModel extends ObjectModel implements IsCircle , Ability {

    private EpsilonModel epsilon;

    public EpsilonProtectorModel(EpsilonModel epsilon ,String id){
        this.epsilon = epsilon;
        this.position = epsilon.getPosition().clone();
        this.id = id;
        this.HP = 20000;
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        type = ModelType.epsilonProtector;
    }

    @Override
    public void die() {
        ObjectController.removeObject(this);
    }


    @Override
    public double getRadios() {
        return SizeConstants.DISMAY_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

    @Override
    public void ability() {
        position = epsilon.getPosition().clone();
    }

    @Override
    public boolean hasAbility() {
        return true;
    }

    public void setEpsilon(EpsilonModel epsilon) {
        this.epsilon = epsilon;
    }
}
