package model.skillTreeAbilities.Cerberus;

import constants.SizeConstants;
import constants.TimeConstants;
import controller.game.Game;
import controller.game.enums.ModelType;
import controller.game.manager.GameState;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import model.interfaces.collisionInterfaces.IsCircle;
import model.objectModel.FighterModel;
import model.objectModel.fighters.EnemyModel;
import utils.Vector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CerberusModel extends FighterModel implements IsCircle {
    @SkippedByJson
    private Timer timer;
    private int timePassed;
    private int coolDown;
    public CerberusModel(Game game , ArrayList<Player> targetedPlayers , Vector position , String id){
        super(game ,targetedPlayers);
        this.id = id;
        this.position = position;
        velocity = new Vector();
        acceleration = new Vector();
        isHovering = true;
        hasMeleeAttack = true;
        meleeAttack = 10;
        coolDown = TimeConstants.CERBERUS_COOLDOWN;
        type = ModelType.cerberus;
        setHP(100000);
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getGameState().isPause())
                    return;
                timePassed += 1000;
                if (timePassed >= TimeConstants.CERBERUS_COOLDOWN){
                    hasMeleeAttack = true;
                    timePassed = 0;
                    timer.stop();
                }
            }
        });
    }

    @Override
    public void die() {

    }


    @Override
    public double getRadios() {
        return SizeConstants.CERBERUS_RADIOS;
    }

    @Override
    public Vector getCenter() {
        return position;
    }

    public void damageIf(EnemyModel enemyModel) {
        if (hasMeleeAttack){
            enemyModel.setHP(enemyModel.getHP() - meleeAttack);
            hasMeleeAttack = false;
            timer.start();
        }
    }

    public void start() {
        initTimer();
        timer.start();
    }

}
