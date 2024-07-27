package model.viewRequests;

import constants.SizeConstants;
import controller.gameController.enums.InGameAbilityType;
import controller.gameController.enums.ModelType;
import controller.gameController.manager.Spawner;
import model.inGameAbilities.InGameAbilityHandler;
import model.inGameAbilities.Slaughter;
import model.objectModel.fighters.EpsilonModel;
import utils.Math;
import utils.Vector;

public class ShootRequest {

    private EpsilonModel epsilon;
    private static int extraAim;
    private static int slaughterBulletCount;

    public ShootRequest(EpsilonModel epsilon){
        this.epsilon = epsilon;
    }

    public static boolean canShoot() {
        return true;
    }

    public static void setExtraAim(int extraAim) {
        ShootRequest.extraAim = extraAim;
    }

    public static void setSlaughterBulletCount(int slaughterBulletCount) {
        ShootRequest.slaughterBulletCount = slaughterBulletCount;
    }

    public void shoot(Vector clickedPoint) {
        Vector direction = Math.VectorAdd(Math.ScalarInVector(-1 ,epsilon.getPosition()) ,clickedPoint);
        Vector position = Math.VectorAdd(
                Math.VectorWithSize(
                        direction ,
                        SizeConstants.EPSILON_BULLET_RADIOS + SizeConstants.EPSILON_DIMENSION.width / 2d + 1
                )
                ,epsilon.getPosition()
        );
        int constant = -1;
        if (slaughterBulletCount >= 1){
            Spawner.addProjectile(position ,direction ,ModelType.slaughterBullet);
            slaughterBulletCount -= 1;
            Slaughter slaughter = (Slaughter) InGameAbilityHandler.getInGameAbility(InGameAbilityType.slaughter);
            slaughter.setUsed(true);
        }
        else {
            Spawner.addProjectile(position, direction, ModelType.epsilonBullet);
        }
        for (int i = 0; i < extraAim ;i++) {
            constant = constant * (-1);
            Vector direction2 = Math.RotateByTheta(
                    direction,
                    new Vector(0 ,0),
                    java.lang.Math.PI / 12 * constant
            );
            Vector spawnPosition = Math.VectorAdd(
                    Math.VectorWithSize(direction2 , SizeConstants.EPSILON_BULLET_RADIOS + 1),
                    position
            );
            Spawner.addProjectile(
                    spawnPosition,
                    direction2,
                    ModelType.epsilonBullet
            );
        }
    }

    public static int getSlaughterBulletCount() {
        return slaughterBulletCount;
    }
}
