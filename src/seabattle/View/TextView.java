package seabattle.View;

import seabattle.Model.Player;

import java.awt.*;

import static seabattle.View.Printable.print;
import static seabattle.View.Printable.println;

/**
 * Created by ivan on 22.05.2015.
 */
public class TextView {
    String[][] showOne;
    String[][] showAll;
    Color[][] showOneColor;
    public TextView() {
        showOne = new String[11][11];
        showOneColor = new Color[11][11];
        showAll = new String[11][25];
    }

    public Color[][] fieldToColor(Player player, boolean shipsIsVisible) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (player.field.cells[i - 1][j - 1].ship == null) {
                    if (player.field.cells[i - 1][j - 1].isShoot) showOneColor[j][i] = Color.BLUE;
                    else showOneColor[j][i] = Color.CYAN;
                } else {
                    if (player.field.cells[i - 1][j - 1].isShoot) showOneColor[j][i] = Color.RED;
                    else {
                        if (shipsIsVisible) showOneColor[j][i] = Color.YELLOW;
                        else showOneColor[j][i] = Color.CYAN;
                    }
                    if (player.field.cells[i - 1][j - 1].ship.liveDeckQty == 0) showOneColor[j][i] = Color.GRAY;
                }
            }
        }
        return showOneColor;
    }

    public String[][] fieldToText(Player player, boolean shipsIsVisible) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (player.field.cells[i - 1][j - 1].ship == null) {
                    if (player.field.cells[i - 1][j - 1].isShoot) showOne[j][i] = "X";
                    else showOne[j][i] = "0";
                } else {
                    if (player.field.cells[i - 1][j - 1].isShoot) showOne[j][i] = "$";
                    else {
                        if (shipsIsVisible) showOne[j][i] = "S";
                        else showOne[j][i] = "0";
                    }
                    if (player.field.cells[i - 1][j - 1].ship.liveDeckQty == 0) showOne[j][i] = "T";
                }
            }
        }
        showOne[0][0] = "  ";
        for (int i = 0; i < 10; i++) {
            showOne[0][i + 1] = Character.toString((char) ('a' + i));
            showOne[i + 1][0] = i != 9 ? " " + Integer.toString(i + 1) : Integer.toString(i + 1);
        }
        return showOne;
    }

    public void showSea(Player player1, Player player2, boolean player1ShipsIsVisible, boolean player2ShipsIsVisible) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                showAll[i][j] = fieldToText(player1, player1ShipsIsVisible)[i][j];
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 14; j < 25; j++) {
                showAll[i][j] = fieldToText(player2, player2ShipsIsVisible)[i][j - 14];
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 11; j < 14; j++) {
                showAll[i][j] = "  ";
            }
        }
        println("               " + player1.name + "                                            " + player2.name);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 25; j++) {
                print(showAll[i][j] + "  ");
            }
            println("");
        }
    }

    public void showSea(Player player1, boolean player1ShipsIsVisible) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                showAll[i][j] = fieldToText(player1, player1ShipsIsVisible)[i][j];
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 11; j < 25; j++) {
                showAll[i][j] = "  ";
            }
        }
        println("               " + player1.name);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 25; j++) {
                print(showAll[i][j] + "  ");
            }
            println("");
        }
    }


}
