package seabattle.Model;

import seabattle.View.GameWindow;
import seabattle.View.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by ivan on 25.05.2015.
 */
public class Game implements Runnable {
    public volatile static boolean isRestart = false;
    public static boolean isDemo;
    public static boolean isPlacementAuto = true;
    public static boolean isPerelman = false;
    public static boolean isPlayAvailable = true;
    public static boolean isDemoAvailable = true;
    public static boolean isRestartAvailable = false;
    public static boolean isOptimal = false;
    Player player1 = new Player();
    Player player2 = new Player();
    public GameWindow gameWindow = new GameWindow();
    TextView textView = new TextView();
    boolean player1ShotRight;
    Field field1StartState, field2StartState;

    public void demoGame() {
        isPlayAvailable = false;
        isDemoAvailable = false;
        isRestartAvailable = false;
        gameWindow.init();
        player1.name = "man";
        player2.name = "robot";
        init();
        player1ShotRight = true;
        while (player1.allDeckQty != 0 && player2.allDeckQty != 0) {
            if (player1ShotRight) {
                if (!player1.shooting(player2.field, true, isOptimal)) {
                    player1ShotRight = !player1ShotRight;
                }
            } else {
                if (!player2.shooting(player1.field, true, isOptimal)) {
                    player1ShotRight = !player1ShotRight;
                }
            }
            gameWindow.update(textView.fieldToColor(player1, true), 1);
            gameWindow.update(textView.fieldToColor(player2, true), 2);
            gameWindow.repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (player1.allDeckQty == 0) gameWindow.textOut("Победил игрок " + player2.name + "!");
        else gameWindow.textOut("Победил игрок " + player1.name + "!");
        isPlayAvailable = true;
        isDemoAvailable = true;
    }

    public void newStart() {
        isPlayAvailable = false;
        isDemoAvailable = false;
        isRestartAvailable = true;
        gameWindow.init();
        player1.name = gameWindow.playerName;
        player2.name = "robot";
        // создание и расстановка кораблей игроков
        init();
        field1StartState = new Field(player1);
        field2StartState = new Field(player2);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field1StartState.cells[i][j].ship = player1.field.cells[i][j].ship;
                field1StartState.cells[i][j].isShoot = player1.field.cells[i][j].isShoot;
                field2StartState.cells[i][j].ship = player2.field.cells[i][j].ship;
                field2StartState.cells[i][j].isShoot = player2.field.cells[i][j].isShoot;
            }
        }
        gameWindow.textOut("Ваш выстрел, " + player1.name + "!");
        gameWindow.update(textView.fieldToColor(player1, true), 1);
        gameWindow.update(textView.fieldToColor(player2, false), 2);
        gameWindow.repaint();
        player1ShotRight = true;
        while (player1.allDeckQty != 0 && player2.allDeckQty != 0) {
            if (isRestart) {
                restart();
                gameWindow.update(textView.fieldToColor(player1, true), 1);
                gameWindow.update(textView.fieldToColor(player2, false), 2);
                gameWindow.textOut("Ваш выстрел, " + player1.name + "!");
                gameWindow.repaint();
            }
            if (player1ShotRight) {
                if (!player1.shooting(player2.field, player1.getClick())) player1ShotRight = !player1ShotRight;
            } else {
                if (!player2.shooting(player1.field, true, isOptimal)) player1ShotRight = !player1ShotRight;
            }
            gameWindow.update(textView.fieldToColor(player1, true), 1);
            gameWindow.update(textView.fieldToColor(player2, false), 2);
            gameWindow.repaint();
        }
        if (player1.allDeckQty == 0) gameWindow.textOut("Победил игрок " + player2.name + "!");
        else gameWindow.textOut("Победил игрок " + player1.name + "!");
        isPlayAvailable = true;
        isDemoAvailable = true;
        isRestartAvailable = false;
    }

    void init() {
        //создание и расстановка кораблей игрока 2 - робота
        if (!isPerelman) player2.field.placeShipsRandomly(player2.ships);
        else player2.field.perelmanPlusPlacement(player2.ships);
        //создание и расстановка кораблей игрока 1
        if (isPlacementAuto) player1.field.placeShipsRandomly(player1.ships);
        else manualPlacement(player1);
    }

    void manualPlacement(Player player) {
        Point firstPoint, secondPoint;
        boolean isHorizontal;
        int deckQty = 1;
        gameWindow.update(textView.fieldToColor(player1, true), 1);
        gameWindow.update(textView.fieldToColor(player2, false), 2);
        gameWindow.repaint();
        for (int i = 0; i < 4; i++) {
            gameWindow.textOut("Укажите местоположение " + (i + 1) + "-го 1-палубного корабля");
            do {
                isHorizontal = true;
                firstPoint = player.getClick();
                if (!player.field.checkFreeSpace(firstPoint.x, firstPoint.y, deckQty, isHorizontal, player.field)) {
                    gameWindow.textOut("Здесь нельзя располагать корабль. Укажите местоположение " + (i + 1) + "-го 1-палубного корабля");
                }
            } while (!player.field.checkFreeSpace(firstPoint.x, firstPoint.y, deckQty, isHorizontal, player.field));
            player.ships[0][i] = new Ship(player1.field.cells[firstPoint.x][firstPoint.y], 1, isHorizontal, player.field);
            gameWindow.update(textView.fieldToColor(player1, true), 1);
            gameWindow.repaint();
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < player.ships[i].length; j++) {
                deckQty = i + 1;
                gameWindow.textOut("Укажите местоположение начала и конца " + (j + 1) + "-го " + deckQty + "-х палубного корабля");
                do {
                    firstPoint = player.getClick();
                    secondPoint = player.getClick();
                    if (firstPoint.y == secondPoint.y) isHorizontal = true;
                    else isHorizontal = false;
                    if (isHorizontal && secondPoint.x < firstPoint.x || !isHorizontal && secondPoint.y < firstPoint.y)
                        firstPoint = secondPoint;
                    if (!player.field.checkFreeSpace(firstPoint.x, firstPoint.y, deckQty, isHorizontal, player.field)) {
                        gameWindow.textOut("Здесь нельзя располагать корабль. Укажите местоположение начала и конца " + (j + 1) + "-го " + deckQty + "-х палубного корабля");
                    }
                } while (!player.field.checkFreeSpace(firstPoint.x, firstPoint.y, deckQty, isHorizontal, player.field));
                player.ships[i][j] = new Ship(player1.field.cells[firstPoint.x][firstPoint.y], deckQty, isHorizontal, player.field);
                gameWindow.update(textView.fieldToColor(player1, true), 1);
                gameWindow.repaint();
            }
        }
    }

    @Override
    public void run() {
        if (isDemo == true) demoGame();
        else newStart();
    }

    public void restart() {
        isRestart = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                player1.field.cells[i][j].ship = field1StartState.cells[i][j].ship;
                player1.field.cells[i][j].isShoot = field1StartState.cells[i][j].isShoot;
                player2.field.cells[i][j].ship = field2StartState.cells[i][j].ship;
                player2.field.cells[i][j].isShoot = field2StartState.cells[i][j].isShoot;
            }
        }
        player1.allDeckQty = 20;
        player2.allDeckQty = 20;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < player1.ships[i].length; j++) {
                player1.ships[i][j].liveDeckQty = player1.ships[i][j].deckQty;
                player2.ships[i][j].liveDeckQty = player2.ships[i][j].deckQty;
            }
        }
        player1ShotRight = true;
    }
}
