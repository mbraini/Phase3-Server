package controller.game.listeners.keyHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.game.listeners.EpsilonAiming;
import controller.game.listeners.PanelKeyListener;
import utils.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class KeyHelper {

    public KeyHelper() {

    }

    public char SHOP_KEY = 'p';
    public char ARES_KEY = 'q';
    public char ASTRAPE_KEY = 'a';
    public char CERBERUS_KEY = 'z';
    public char ACESO_KEY = 'w';
    public char MELAMPUS_KEY = 's';
    public char CHIRON_KEY = 'x';
    public char PROTEUS_KEY = 'e';
    public char EMPUSA_KEY = 'd';
    public char DOLUS_KEY = 'c';
    public char ATHENA_KEY = 'r';
    public int AIME_CODE = 1;

    public static void randomize() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        StringBuilder text = Helper.readFile("src/controller/configs/keyConfigs.json");

        KeyHelper keyHelper = gson.fromJson(text.toString(), KeyHelper.class);
        ArrayList<Character> characters = getCharacters(keyHelper);
        Collections.shuffle(characters);

        PanelKeyListener.ARES_KEY = characters.get(0);
        PanelKeyListener.ACESO_KEY = characters.get(1);
        PanelKeyListener.ATHENA_KEY = characters.get(2);
        PanelKeyListener.EMPUSA_KEY = characters.get(3);
        PanelKeyListener.PROTEUS_KEY = characters.get(4);
        PanelKeyListener.ASTRAPE_KEY = characters.get(5);
        PanelKeyListener.SHOP_KEY = characters.get(6);
        PanelKeyListener.CERBERUS_KEY = characters.get(7);
        PanelKeyListener.CHIRON_KEY = characters.get(8);
        PanelKeyListener.MELAMPUS_KEY = characters.get(9);
        PanelKeyListener.DOLUS_KEY = characters.get(10);

        EpsilonAiming.AIM_CODE = new Random().nextInt(1 ,4);
    }

    private static ArrayList<Character> getCharacters(KeyHelper keyHelper) {
        ArrayList<Character> characters = new ArrayList<>();
        characters.add(keyHelper.ATHENA_KEY);
        characters.add(keyHelper.ACESO_KEY);
        characters.add(keyHelper.ASTRAPE_KEY);
        characters.add(keyHelper.ARES_KEY);
        characters.add(keyHelper.CERBERUS_KEY);
        characters.add(keyHelper.CHIRON_KEY);
        characters.add(keyHelper.DOLUS_KEY);
        characters.add(keyHelper.EMPUSA_KEY);
        characters.add(keyHelper.MELAMPUS_KEY);
        characters.add(keyHelper.PROTEUS_KEY);
        characters.add(keyHelper.SHOP_KEY);
        return characters;
    }

    public static void reorder() {
        Gson gson = new Gson();

        StringBuilder text = Helper.readFile("src/controller/configs/keyConfigs.json");

        KeyHelper keyHelper = gson.fromJson(text.toString() , KeyHelper.class);
        PanelKeyListener.SHOP_KEY = keyHelper.SHOP_KEY;
        PanelKeyListener.ARES_KEY = keyHelper.ARES_KEY;
        PanelKeyListener.ASTRAPE_KEY = keyHelper.ASTRAPE_KEY;
        PanelKeyListener.CERBERUS_KEY = keyHelper.CERBERUS_KEY;
        PanelKeyListener.ACESO_KEY = keyHelper.ACESO_KEY;
        PanelKeyListener.MELAMPUS_KEY = keyHelper.MELAMPUS_KEY;
        PanelKeyListener.CHIRON_KEY = keyHelper.CHIRON_KEY;
        PanelKeyListener.PROTEUS_KEY = keyHelper.PROTEUS_KEY;
        PanelKeyListener.EMPUSA_KEY = keyHelper.EMPUSA_KEY;
        PanelKeyListener.DOLUS_KEY = keyHelper.DOLUS_KEY;
        PanelKeyListener.ATHENA_KEY = keyHelper.ATHENA_KEY;

        EpsilonAiming.AIM_CODE = keyHelper.AIME_CODE;
    }

}
