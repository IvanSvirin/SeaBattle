package seabattle;

import seabattle.Model.GameText;

import java.util.Scanner;

import static seabattle.View.Printable.println;

/**
 * Created by ivan on 29.04.2015.
 */
public class MainText {
    public static void main(String[] args) {
        int n = 0;
        while (n != 3) {
            println("Вы находитесь в главном меню. Выберите режим работы:");
            println("1 - посмотреть демонстрационный пример и условные обозначения");
            println("2 - сыграть в игру с умным роботом");
            println("3 - выйти из программы");
            n = Integer.parseInt((new Scanner(System.in)).nextLine());
            if (n == 1) {
                GameText game = new GameText();
                game.demoGame();
            }
            if (n == 2) {
                GameText game = new GameText();
                game.newStart();
            }
        }
    }
}
