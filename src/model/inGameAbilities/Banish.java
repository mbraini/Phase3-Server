package model.inGameAbilities;

import constants.DistanceConstants;
import controller.game.enums.InGameAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.logics.Impact;
import model.objectModel.fighters.EpsilonModel;

public class Banish extends InGameAbility{

    private EpsilonModel epsilon;

    public Banish(Player player){
        super(player);
        type = InGameAbilityType.banish;
        xpCost = 100;
        this.epsilon = player.getPlayerData().getEpsilon();
    }

    @Override
    public void performAbility() {
        new Impact(epsilon.getGame() ,epsilon.getPosition() , DistanceConstants.BANISH_IMPACT_RANGE ,2000 ,true).MakeImpact();
        isActive = false;
    }

    @Override
    public void setUp() {
        epsilon = player.getPlayerData().getEpsilon();
    }
}
