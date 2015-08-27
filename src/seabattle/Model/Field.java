package seabattle.Model;

import seabattle.View.TextView;

import java.util.Random;
import java.util.Scanner;

import static seabattle.View.Printable.println;

/**
 * Created by ivan on 25.04.15.
 */
public class Field {
    public Cell[][] cells = new Cell[10][10];
    Player player;

    Field(Player player) {
        this.player = player;
        init();
    }

    void init() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell(i, j, null, false);
            }
        }
    }

    void placeShipsRandomly(Ship[][] ships) {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                int upperLeftX, upperLeftY, deckQty;
                boolean isHorizontal;
                do {
                    upperLeftX = random.nextInt(10);
                    upperLeftY = random.nextInt(10);
                    deckQty = i + 1;
                    isHorizontal = random.nextBoolean();
                } while (!checkFreeSpace(upperLeftX, upperLeftY, deckQty, isHorizontal, this));
                ships[i][j] = new Ship(this.cells[upperLeftX][upperLeftY], deckQty, isHorizontal, this);
            }
        }
    }

    void perelmanPlusPlacement(Ship[][] ships) {
        int news, deckQty;
        int upperLeftX, upperLeftY;
        boolean isHorizontal;
        Random random = new Random();
        news = random.nextInt(4);
//        for (int i = 1; i < 4; i++) {
//            for (int j = 0; j < ships[i].length; j++) {
//                do {
//                    upperLeftX = random.nextInt(10);
//                    upperLeftY = random.nextInt(3);
//                    deckQty = i + 1;
//                } while (!checkFreeSpace(upperLeftX, upperLeftY, deckQty, isHorizontal, this));
//                ships[i][j] = new Ship(this.cells[upperLeftX][upperLeftY], deckQty, isHorizontal, this);
//            }
//        }
        if (news == 0) {
            isHorizontal = true;
            ships[1][0] = new Ship(this.cells[0][0], 2, isHorizontal, this);
            ships[1][1] = new Ship(this.cells[3][0], 2, isHorizontal, this);
            ships[1][2] = new Ship(this.cells[0][2], 2, isHorizontal, this);
            ships[2][0] = new Ship(this.cells[3][2], 3, isHorizontal, this);
            ships[2][1] = new Ship(this.cells[7][2], 3, isHorizontal, this);
            ships[3][0] = new Ship(this.cells[6][0], 4, isHorizontal, this);
        }
        if (news == 1) {
            isHorizontal = false;
            ships[1][0] = new Ship(this.cells[7][5], 2, isHorizontal, this);
            ships[1][1] = new Ship(this.cells[7][8], 2, isHorizontal, this);
            ships[1][2] = new Ship(this.cells[9][8], 2, isHorizontal, this);
            ships[2][0] = new Ship(this.cells[9][0], 3, isHorizontal, this);
            ships[2][1] = new Ship(this.cells[9][4], 3, isHorizontal, this);
            ships[3][0] = new Ship(this.cells[7][0], 4, isHorizontal, this);
        }
        if (news == 2) {
            isHorizontal = false;
            ships[1][0] = new Ship(this.cells[0][5], 2, isHorizontal, this);
            ships[1][1] = new Ship(this.cells[0][8], 2, isHorizontal, this);
            ships[1][2] = new Ship(this.cells[2][8], 2, isHorizontal, this);
            ships[2][0] = new Ship(this.cells[2][0], 3, isHorizontal, this);
            ships[2][1] = new Ship(this.cells[2][4], 3, isHorizontal, this);
            ships[3][0] = new Ship(this.cells[0][0], 4, isHorizontal, this);
        }
        if (news == 3) {
            isHorizontal = true;
            ships[1][0] = new Ship(this.cells[0][7], 2, isHorizontal, this);
            ships[1][1] = new Ship(this.cells[3][7], 2, isHorizontal, this);
            ships[1][2] = new Ship(this.cells[0][9], 2, isHorizontal, this);
            ships[2][0] = new Ship(this.cells[3][9], 3, isHorizontal, this);
            ships[2][1] = new Ship(this.cells[7][9], 3, isHorizontal, this);
            ships[3][0] = new Ship(this.cells[6][7], 4, isHorizontal, this);
        }
        isHorizontal = true;
        for (int i = 0; i < 4; i++) {
            do {
                upperLeftX = random.nextInt(10);
                upperLeftY = random.nextInt(10);
                deckQty = 1;
            } while (!checkFreeSpace(upperLeftX, upperLeftY, deckQty, isHorizontal, this));
            ships[0][i] = new Ship(this.cells[upperLeftX][upperLeftY], deckQty, isHorizontal, this);
        }
    }

    void placeShipsByPlayer(Ship[][] ships, TextView textView) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                int upperLeftX, upperLeftY, deckQty;
                boolean isHorizontal = true;
                String isHorizontalStr;
                do {
                    boolean error;
                    deckQty = i + 1;
                    if (i > 0) {
                        do {
                            error = false;
                            println("Задайте ориентацию " + (j + 1) + "-го " + deckQty + "-палубного корабля(h - горизонтальная, v - вертикальная):");
                            isHorizontalStr = (new Scanner(System.in)).nextLine();
                            if (isHorizontalStr.equals("h")) isHorizontal = true;
                            else if (isHorizontalStr.equals("v")) isHorizontal = false;
                            else error = true;
                            if (error) println("Неверный символ! Повторите ввод.");
                        } while (error);
                    }
                    do {
                        error = false;
                        println("Введите координату верхней или левой палубы " + (j + 1) + "-го " + deckQty + "-палубного корабля(пример ввода: d4)");
                        String s = (new Scanner(System.in)).nextLine();
                        upperLeftX = s.charAt(0) - 97;
                        upperLeftY = s.charAt(1) - 49;
                        if (s.length() > 2) upperLeftY = (s.charAt(1) - 48) * 10 + s.charAt(2) - 49;
                        if (upperLeftX < 0 || upperLeftX > 9 || upperLeftY < 0 || upperLeftY > 9) error = true;
                        if (error) println("Значение за пределами поля! Попробуйте еще раз.");
                    } while (error);
                    if (!checkFreeSpace(upperLeftX, upperLeftY, deckQty, isHorizontal, this))
                        println("Здесь нельзя располагать корабль. Попробуйте еще раз.");
                } while (!checkFreeSpace(upperLeftX, upperLeftY, deckQty, isHorizontal, this));
                ships[i][j] = new Ship(this.cells[upperLeftX][upperLeftY], deckQty, isHorizontal, this);
                textView.showSea(this.player, true);
            }
        }
    }

    //проверка наличия свободного места на игровом поле в момент создания нового корабля
    boolean checkFreeSpace(int a, int b, int deckQty, boolean isHorizontal, Field field) {
        if (isHorizontal && (a + deckQty) > 10) return false;
        if (!isHorizontal && (b + deckQty) > 10) return false;
        for (int i = 0; i < deckQty; i++) {
            for (int j = a - 1; j < a + 2; j++) {
                for (int k = b - 1; k < b + 2; k++) {
                    try {
                        if (isHorizontal && field.cells[j + i][k].ship != null) return false;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (!isHorizontal && field.cells[j][k + i].ship != null) return false;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }

        }
        return true;
    }
}

