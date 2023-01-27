package rockpaperscissors;

import java.io.File;
import java.io.IOException;

public class Main {
    static File file = new File("../../rating.txt");
    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        gameEngine.startGame();
    }
}