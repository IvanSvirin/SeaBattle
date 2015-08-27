package seabattle.Model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static seabattle.View.Printable.println;

/**
 * Created by ivan on 25.04.2015.
 */
public class Player {
    public String name;
    public Field field;
    int allDeckQty; //общее количество палуб
    Ship[][] ships;

    public static Point point;
    public volatile static boolean isClick;
    int count;
    int count50 = 0;

    Player() {
        field = new Field(this);
        allDeckQty = 20;
        ships = new Ship[4][];
        for (int i = 0; i < 4; i++) {
            ships[i] = new Ship[4 - i];
        }
    }

    public Point getClick() {
        isClick = false;
        while (!isClick) ;
        return point;
    }

    boolean shooting(Field field, boolean randomShot, boolean isOptimal) {
        int x = 0, y = 0;
        Point nextPoint;
        if (randomShot) {
            if (isOptimal) {
                if (field.player.ships[3][0].liveDeckQty == 4) {
                    count = 0;
                    do {
                        if (findHalfDeadShip(field) != null) {
                            x = findHalfDeadShip(field).x;
                            y = findHalfDeadShip(field).y;
                        } else {
                            nextPoint = searchFourDeck();
                            x = nextPoint.x;
                            y = nextPoint.y;
                        }
                    } while (field.cells[x][y].isShoot);

                } else {
                    if (count50 < 49) {
                        do {
                            if (findHalfDeadShip(field) != null) {
                                x = findHalfDeadShip(field).x;
                                y = findHalfDeadShip(field).y;
                            } else {
                                nextPoint = searchTwoDeck();
                                x = nextPoint.x;
                                y = nextPoint.y;
                            }
                        } while (field.cells[x][y].isShoot);

                    } else {
                        Random random = new Random();
                        do {
                            x = random.nextInt(10);
                            y = random.nextInt(10);
                        } while (field.cells[x][y].isShoot);
                        if (findHalfDeadShip(field) != null) {
                            x = findHalfDeadShip(field).x;
                            y = findHalfDeadShip(field).y;
                        }
                    }
                }

            } else {
                Random random = new Random();
                do {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                } while (field.cells[x][y].isShoot);
                if (findHalfDeadShip(field) != null) {
                    x = findHalfDeadShip(field).x;
                    y = findHalfDeadShip(field).y;
                }
            }
        } else {
            boolean error;
            do {
                error = false;
                println("Ваш выстрел, " + this.name + ". Введите координату (пример ввода: d4)");
                String s = (new Scanner(System.in)).nextLine();
                x = s.charAt(0) - 97;
                y = s.charAt(1) - 49;
                if (s.length() > 2) y = (s.charAt(1) - 48) * 10 + s.charAt(2) - 49;
                if (x < 0 || x > 9 || y < 0 || y > 9) error = true;
                if (error) println("Значение за пределами поля! Попробуйте еще раз.");
            } while (error);
        }
        if (field.cells[x][y].ship == null) {
            field.cells[x][y].isShoot = true;
            return false;
        } else {
            field.cells[x][y].ship.liveDeckQty--;
            if (field.cells[x][y].ship.liveDeckQty == 0) {
                for (int i = 0; i < field.cells[x][y].ship.deckQty; i++) {
                    for (int j = field.cells[x][y].ship.upperLeftCell.x - 1; j < field.cells[x][y].ship.upperLeftCell.x + 2; j++) {
                        for (int k = field.cells[x][y].ship.upperLeftCell.y - 1; k < field.cells[x][y].ship.upperLeftCell.y + 2; k++) {
                            try {
                                if (field.cells[x][y].ship.isHorizontal) field.cells[j + i][k].isShoot = true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            try {
                                if (!field.cells[x][y].ship.isHorizontal) field.cells[j][k + i].isShoot = true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                    }

                }
            }
            field.player.allDeckQty--;
            field.cells[x][y].isShoot = true;
            return true;
        }
    }

    Point searchFourDeck() {
        Point searchPoint = new Point(0, 0);
        searchPoint.y = count % 4;
        searchPoint.x = count % 4;
        if (count / 4 == 0) {
            searchPoint.y += 6;
        }
        if (count / 4 == 1) {
            searchPoint.x += 4;
            searchPoint.y += 6;
        }
        if (count / 4 == 2) {
            searchPoint.x += 6;
            searchPoint.y += 4;
        }
        if (count / 4 == 3) {
            searchPoint.y += 2;
        }
        if (count / 4 == 4) {
            searchPoint.x += 2;
        }
        if (count / 4 == 5) {
            searchPoint.x += 6;
        }
        count++;
        return searchPoint;
    }

    Point searchTwoDeck() {
        Point searchPoint = new Point(0, 0);
        int i = count50 / 5;
        searchPoint.x = i;
        int j = count50 % 5;
        if (i % 2 == 0) searchPoint.y = j * 2;
        else searchPoint.y = j * 2 + 1;
        count50++;
        return searchPoint;
    }

    boolean shooting(Field field, Point point) {
        int x, y;
        x = point.x;
        y = point.y;

        if (field.cells[x][y].isShoot == true) {
            return false;
        }

        if (field.cells[x][y].ship == null) {
            field.cells[x][y].isShoot = true;
            return false;
        } else {
            field.cells[x][y].ship.liveDeckQty--;
            if (field.cells[x][y].ship.liveDeckQty == 0) {
                for (int i = 0; i < field.cells[x][y].ship.deckQty; i++) {
                    for (int j = field.cells[x][y].ship.upperLeftCell.x - 1; j < field.cells[x][y].ship.upperLeftCell.x + 2; j++) {
                        for (int k = field.cells[x][y].ship.upperLeftCell.y - 1; k < field.cells[x][y].ship.upperLeftCell.y + 2; k++) {
                            try {
                                if (field.cells[x][y].ship.isHorizontal) field.cells[j + i][k].isShoot = true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            try {
                                if (!field.cells[x][y].ship.isHorizontal) field.cells[j][k + i].isShoot = true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                    }

                }
            }
            field.player.allDeckQty--;
            field.cells[x][y].isShoot = true;
            return true;
        }
    }


    Cell findHalfDeadShip(Field field) {
        ArrayList<Cell> cell = new ArrayList<>();
        Cell cellReturn = null;
        int x, y;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (field.cells[i][j].isShoot && field.cells[i][j].ship != null && field.cells[i][j].ship.liveDeckQty != 0) {
                    cell.add(field.cells[i][j]);
                }
            }
        }
        switch (cell.size()) {
            case 0: {
                cellReturn = null;
            }
            break;
            case 1: {
                x = cell.get(0).x;
                y = cell.get(0).y;
                if (x > 0 && !field.cells[x - 1][y].isShoot) cellReturn = field.cells[x - 1][y];
                if (y > 0 && !field.cells[x][y - 1].isShoot) cellReturn = field.cells[x][y - 1];
                if (x < 9 && !field.cells[x + 1][y].isShoot) cellReturn = field.cells[x + 1][y];
                if (y < 9 && !field.cells[x][y + 1].isShoot) cellReturn = field.cells[x][y + 1];
            }
            break;
            case 2: {
                if (cell.get(0).x == cell.get(1).x) {
                    x = cell.get(0).x;
                    if (cell.get(0).y < cell.get(1).y) y = cell.get(0).y;
                    else y = cell.get(1).y;
                    if (y > 0 && !field.cells[x][y - 1].isShoot) cellReturn = field.cells[x][y - 1];
                    if (y < 8 && !field.cells[x][y + 2].isShoot) cellReturn = field.cells[x][y + 2];
                }
                if (cell.get(0).y == cell.get(1).y) {
                    y = cell.get(0).y;
                    if (cell.get(0).x < cell.get(1).x) x = cell.get(0).x;
                    else x = cell.get(1).x;
                    if (x > 0 && !field.cells[x - 1][y].isShoot) cellReturn = field.cells[x - 1][y];
                    if (x < 8 && !field.cells[x + 2][y].isShoot) cellReturn = field.cells[x + 2][y];
                }
            }
            break;
            case 3: {
                if (cell.get(0).x == cell.get(2).x) {
                    x = cell.get(0).x;
                    if (cell.get(0).y < cell.get(2).y) y = cell.get(0).y;
                    else y = cell.get(2).y;
                    if (y > 0 && !field.cells[x][y - 1].isShoot) cellReturn = field.cells[x][y - 1];
                    if (y < 7 && !field.cells[x][y + 3].isShoot) cellReturn = field.cells[x][y + 3];
                }
                if (cell.get(0).y == cell.get(2).y) {
                    y = cell.get(0).y;
                    if (cell.get(0).x < cell.get(2).x) x = cell.get(0).x;
                    else x = cell.get(1).x;
                    if (x > 0 && !field.cells[x - 1][y].isShoot) cellReturn = field.cells[x - 1][y];
                    if (x < 7 && !field.cells[x + 3][y].isShoot) cellReturn = field.cells[x + 3][y];
                }
                break;
            }
        }
        return cellReturn;
    }
}