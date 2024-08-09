package model.objectModel;

import constants.SizeConstants;
import constants.TimeConstants;
import controller.game.onlineGame.Game;
import controller.game.onlineGame.ObjectController;
import controller.game.enums.ModelType;
import model.interfaces.Ability;
import model.interfaces.Fader;
import model.interfaces.collisionInterfaces.IsCircle;
import model.objectModel.frameModel.FrameModel;
import utils.Vector;

public class PortalModel extends ObjectModel implements IsCircle, Fader ,Ability {
    private double fadeTime;
    private FrameModel epsilonFrame;

    public PortalModel(Game game ,FrameModel epsilonFrame, String id) {
        super(game);
        this.id = id;
        this.epsilonFrame = epsilonFrame;
        position = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width,
                epsilonFrame.getPosition().y
        );
        this.velocity = new Vector(0 ,0);
        this.acceleration = new Vector(0 ,0);
        HP = 1;
        this.type = ModelType.portal;
    }

    @Override
    public void die() {
        ObjectController.removeObject(this);
    }

    @Override
    public double getRadios() {
        return SizeConstants.PORTAL_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

    @Override
    public void addTime(double time) {
        fadeTime += time;
    }

    @Override
    public void fadeIf() {
        if (fadeTime >= TimeConstants.PORTAL_FADE_TIME) {
            die();
        }
    }

    @Override
    public void ability() {
        position = new Vector(
                epsilonFrame.getPosition().x + epsilonFrame.getSize().width,
                epsilonFrame.getPosition().y
        );
    }

    @Override
    public boolean hasAbility() {
        return true;
    }
}
