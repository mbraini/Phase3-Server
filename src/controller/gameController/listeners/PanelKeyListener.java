package controller.gameController.listeners;

import controller.gameController.ViewRequestController;
import controller.gameController.configs.Configs;
import controller.gameController.enums.SkillTreeAbilityType;
import controller.gameController.manager.GameState;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelKeyListener extends KeyAdapter {

    public static char SHOP_KEY = 'p';
    public static char ARES_KEY = 'q';
    public static char ASTRAPE_KEY = 'a';
    public static char CERBERUS_KEY = 'z';
    public static char ACESO_KEY = 'w';
    public static char MELAMPUS_KEY = 's';
    public static char CHIRON_KEY = 'x';
    public static char PROTEUS_KEY = 'e';
    public static char EMPUSA_KEY = 'd';
    public static char DOLUS_KEY = 'c';
    public static char ATHENA_KEY = 'r';

    public PanelKeyListener(){
        SHOP_KEY = Configs.KeyConfigs.SHOP_KEY;
        ARES_KEY = Configs.KeyConfigs.ARES_KEY;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (GameState.isInAnimation())
            return;
        if (e.getKeyChar() == SHOP_KEY && !GameState.isPause()){
//            Controller.pause();
//            new Shop(new ShopFrame());
        }
        if (e.getKeyChar() == ARES_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.ares);
        }
        if (e.getKeyChar() == ASTRAPE_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.astrape);
        }
        if (e.getKeyChar() == CERBERUS_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.cerberus);
        }
        if (e.getKeyChar() == ACESO_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.aceso);
        }
        if (e.getKeyChar() == MELAMPUS_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.melampus);
        }
        if (e.getKeyChar() == CHIRON_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.chiron);
        }
        if (e.getKeyChar() == PROTEUS_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.proteus);
        }
        if (e.getKeyChar() == EMPUSA_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.empusa);
        }
        if (e.getKeyChar() == DOLUS_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.dolus);
        }
        if (e.getKeyChar() == ATHENA_KEY) {
            ViewRequestController.skillTreeAbilityRequest(SkillTreeAbilityType.athena);
        }
    }
}
