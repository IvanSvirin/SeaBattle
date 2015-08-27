package seabattle.Controller;

import seabattle.Model.Game;
import seabattle.Model.Player;
import seabattle.Model.Point;

import javax.swing.*;

/**
 * Created by ivan on 24.05.2015.
 */
public class GameController {
    private static String about = "  Игра реализована согласно классических правил, изложенных на ru.wikipedia.org/wiki/Морской_бой_(игра).\n" +
            "  Соперниками являются человек и компьютер.\n" +
            "  Реализованы 2 варианта игры: консольный и графический.\n" +
            "  В консольном варианте реализованы следующие возможности:\n" +
            "  - автоматическая расстановка кораблей обоих противников \n" +
            "  - ручная расстановка кораблей человека \n" +
            "  - проверка возможности установки каждого корабля по указанным координатам \n" +
            "  - случайная стрельба обоих противников в режиме демонстрации \n" +
            "  - добивание раненых кораблей \n" +
            "  - ручная стрельба человеком \n" +
            "  В графическом варианте реализованы те же возможности, кроме того добавлены:\n" +
            "  - выбор для человека ручной или автоматической расстановки своих кораблей \n" +
            "  - выбор для робота случайной расстановки кораблей или расстановки по усовершенствованному методу Перельмана\n" +
            "  (корабли более эффективно расставлять не в угол поля, а к одной из его сторон)\n" +
            "  - добавлен для робота метод оптимальной стрельбы (робот сначала пытается найти большие корабли)\n" +
            "  В планах:\n" +
            "  - переделать меню, добавить выбор языка\n" +
            "  - организовать полноценный диалог с игроком\n" +
            "  - усовершенствовать код, навести порядок в структуре";
    public static void doMenu(int n) {
        switch (n) {
            //play
            case 0: {
                if (Game.isPlayAvailable) {
                    Game.isDemo = false;
                    Thread t = new Thread(new Game());
                    t.start();
                }
            }
            break;
            //restart
            case 1: {
                if (Game.isRestartAvailable) {
                    Game.isRestart = true;
                }
            }
            break;
            //demo
            case 2: {
                if (Game.isDemoAvailable) {
                    Game.isDemo = true;
                    Thread t = new Thread(new Game());
                    t.start();
                }
            }
            break;
            //about
            case 3: {
                JOptionPane.showConfirmDialog(null, about, "About", JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
            break;
            //exit
            case 4: {
                System.exit(0);
            }
            break;
        }
    }

    public static void doClick(Point point) {
        Player.point = point;
        Player.isClick = true;
    }

    public static void doPlacementAuto(boolean isAuto) {
        Game.isPlacementAuto = isAuto;
    }

    public static void doPerelmanPlus(boolean isPerelman) {
        Game.isPerelman = isPerelman;
    }

    public static void doOptimal(boolean isOptimal) {
        Game.isOptimal = isOptimal;
    }
}
