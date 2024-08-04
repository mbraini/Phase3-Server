import controller.Application;
import controller.game.Controller;
import controller.game.Game;

public class Main {
    public static void main(String[] args) {
        Controller.startGame();
        new Application().run();

    }
}