package constants;

import java.awt.*;

public class SizeConstants {


    public static final Dimension barD = new Dimension(14,37);
    public static final Dimension NECROPICK_DIMENSION = new Dimension(40 ,60);
    public static final Dimension OMENOCT_DIMENTION = new Dimension(100 ,100);
    public static final double OMENOCT_RADIOS = (
            OMENOCT_DIMENTION.width / (Math.sqrt(2) + 1)
    ) / Math.sqrt(2 - Math.sqrt(2));
    public static final Dimension ARCHMIRE_DIMENSION = new Dimension(100 ,100);
    public static final Dimension WYRM_FRAME_DIMENSION = new Dimension(180 ,130);
    public static final Dimension WYRM_DIMENSION = new Dimension(180 ,130);
    public static final Dimension BLACK_ORB_FRAME_DIMENSION = new Dimension(100 ,100);
    public static final Dimension ORB_DIMENSION = new Dimension(70 ,70);
    public static final Dimension HAND_DIMENSION = new Dimension(200 ,200);
    public static final Dimension HEAD_DIMENSION = new Dimension(200 ,200);
    public static final Dimension PUNCH_DIMENSION = new Dimension(200 ,200);
    public static final Dimension BARRICADOS_DIMENSION = new Dimension(200 ,200);
    public static final Dimension MINIMUM_FRAME_DIMENSION = new Dimension(300 ,300);
    public static final Dimension ABILITY_VIEW_DIMENSION = new Dimension(
            MINIMUM_FRAME_DIMENSION.width / 11,
            MINIMUM_FRAME_DIMENSION.height / 11
    );
    public static final Dimension COLLECTIVE_BOX_DIMENSION = new Dimension(100 ,100);
    public static final double BOSS_BULLET_RADIOS = 20;
    public static final double NECROPICK_SPAWN_RADIOS = 300;
    public static final double NECROPICIK_BULLET_RADIOS = 5;
    public static final double WYRM_NAVIGATION_RADIOS = 500;
    public static final int ARCHMIRE_AOE_TIME_LIMIT = 5000;
    public static final double VOMIT_RADIOS = 50;
    public static final double PROJECTILE_NAVIGATE_RADIOS = 500;
    public static final double PROJECTILE_ACTIVATION_RADIOS = 700;
    public static final int DISMAY_RADIOS = 300;
    public static final double SLAUGHTER_BULLET_RADIOS = 5 ;
    public static final double CERBERUS_RADIOS = 20;
    public static final double PORTAL_RADIOS = 100;
    public static final double EPSILON_BULLET_RADIOS = 5;
    public static final double COLLECTIVE_RADIOS = 5;
    public static final int COLLECTIVE_ABILITY_ACTIVATION_RADIOS = 100;
    public static final int EPSILON_VERTICES_RADIOS = 3;
    public final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int GAME_HEIGHT = 700;
    public static final int GAME_WIDTH = 700;
    public static final double OMENOCT_BULLET_RADIOS = 8;
    public static final double WYRM_BULLET_RADIOS = 6;
    public static final double BLACK_ORB_DIAGONAL_SIZE = DistanceConstants.BLACK_ORB_DISTANCE / (2 * Math.sin(Math.toRadians(36)));
    public static Dimension EPSILON_DIMENSION = new Dimension(25 ,25);
    public static Dimension TRIGORATH_DIMENTION = new Dimension(50 ,50);
    public static Dimension Squarantine_DIMENTION = new Dimension(50 ,50);
}
