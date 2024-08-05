package model.inGameAbilities;

import controller.game.enums.InGameAbilityType;
import controller.game.player.Player;
import model.ModelData;
import model.objectModel.fighters.EpsilonModel;

public class Heal extends InGameAbility{

    private EpsilonModel epsilon;

    public Heal(Player player){
        super(player);
        type = InGameAbilityType.heal;
        xpCost = 50;
        this.epsilon = player.getPlayerData().getEpsilon();
    }

    @Override
    public void performAbility() {
        epsilon.setHP(epsilon.getHP() + 10);
        if (epsilon.getHP() > 100)
            epsilon.setHP(100);
        isActive = false;
    }

    @Override
    public void setUp() {
//        epsilon = ModelData.getEpsilon();
    }


}
