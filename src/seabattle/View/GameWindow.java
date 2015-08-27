package seabattle.View;

import seabattle.Controller.GameController;
import seabattle.Model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by ivan on 23.05.2015.
 */
public class GameWindow extends JFrame {
    JPanel jPanel = new JPanel();
    public SeaPanel seaPanel = new SeaPanel();
    JPanel titlePanel = new JPanel();
    JTextArea jTextArea = new JTextArea();
//    JLabel title1 = new JLabel("man", SwingConstants.CENTER);
//    JLabel title2 = new JLabel("robot", SwingConstants.CENTER);
    JMenuBar jMenuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game Menu");
    JMenuItem[] gameMenuItems = new JMenuItem[5];
    JMenu settings = new JMenu("Settings");
    JMenu placementMan = new JMenu("Man's ships Placement");
    JMenu placementRobot = new JMenu("Robot's ships Placement");
    JMenu robotShootingMethod = new JMenu("Robot's shooting method");
    JRadioButtonMenuItem[] placementManItems = new JRadioButtonMenuItem[2];
    ButtonGroup placementManGroup = new ButtonGroup();
    JRadioButtonMenuItem[] placementRobotItems = new JRadioButtonMenuItem[2];
    ButtonGroup placementRobotGroup = new ButtonGroup();
    JRadioButtonMenuItem[] robotShootingMethodItems = new JRadioButtonMenuItem[2];
    ButtonGroup robotShootingMethodGroup = new ButtonGroup();
    public String playerName;

    public void init() {
        seaPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        JLabel title1 = new JLabel("man", SwingConstants.CENTER);
        JLabel title2 = new JLabel("robot", SwingConstants.CENTER);
        if (!Game.isDemo) {
            playerName = JOptionPane.showInputDialog("Введите Ваше имя: ");
            title1.setText(playerName);
        }
        setSize(1100, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sea Battle");
        jPanel.setLayout(new BorderLayout());
        titlePanel.setLayout(new GridLayout(1, 2));
        jTextArea.setEditable(false);

        gameMenuItems[0] = new JMenuItem("Play game");
        gameMenuItems[1] = new JMenuItem("Restart");
        gameMenuItems[2] = new JMenuItem("Demo");
        gameMenuItems[3] = new JMenuItem("About");
        gameMenuItems[4] = new JMenuItem("Exit");
        placementManItems[0] = new JRadioButtonMenuItem("Auto", true);
        placementManItems[1] = new JRadioButtonMenuItem("Manual");
        placementManGroup.add(placementManItems[0]);
        placementManGroup.add(placementManItems[1]);
        placementRobotItems[0] = new JRadioButtonMenuItem("Auto", true);
        placementRobotItems[1] = new JRadioButtonMenuItem("PerelmanPlus");
        placementRobotGroup.add(placementRobotItems[0]);
        placementRobotGroup.add(placementRobotItems[1]);
        robotShootingMethodItems[0] = new JRadioButtonMenuItem("Auto", true);
        robotShootingMethodItems[1] = new JRadioButtonMenuItem("Optimal");
        robotShootingMethodGroup.add(robotShootingMethodItems[0]);
        robotShootingMethodGroup.add(robotShootingMethodItems[1]);
        for (int i = 0; i < gameMenuItems.length; i++) {
            final int finalI = i;
            gameMenuItems[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ((finalI == 0 || finalI == 2) && Game.isPlayAvailable) setVisible(false);
                    if (finalI == 1 && Game.isRestartAvailable)
                        textOut("Чтобы снова начать игру, нажмите на любое поле");
                    GameController.doMenu(finalI);
                }
            });

        }
        placementManItems[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementManItems[0].isSelected()) GameController.doPlacementAuto(true);
            }
        });
        placementManItems[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementManItems[1].isSelected()) GameController.doPlacementAuto(false);
            }
        });
        placementRobotItems[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementRobotItems[0].isSelected()) GameController.doPerelmanPlus(false);
            }
        });
        placementRobotItems[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementRobotItems[1].isSelected()) GameController.doPerelmanPlus(true);
            }
        });
        robotShootingMethodItems[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (robotShootingMethodItems[0].isSelected()) GameController.doOptimal(false);
            }
        });
        robotShootingMethodItems[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (robotShootingMethodItems[1].isSelected()) GameController.doOptimal(true);
            }
        });
        seaPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int xClick = e.getX() / 50;
                int yClick = e.getY() / 50;
                if (xClick > 10) xClick -= 11;
                GameController.doClick(new seabattle.Model.Point(yClick, xClick));
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        gameMenu.add(gameMenuItems[0]);
        gameMenu.add(gameMenuItems[1]);
        gameMenu.add(gameMenuItems[2]);
        gameMenu.add(gameMenuItems[3]);
        gameMenu.add(gameMenuItems[4]);
        placementMan.add(placementManItems[0]);
        placementMan.add(placementManItems[1]);
        placementRobot.add(placementRobotItems[0]);
        placementRobot.add(placementRobotItems[1]);
        robotShootingMethod.add(robotShootingMethodItems[0]);
        robotShootingMethod.add(robotShootingMethodItems[1]);
        settings.add(placementMan);
        settings.add(placementRobot);
        settings.add(robotShootingMethod);
        jMenuBar.add(gameMenu);
        jMenuBar.add(settings);


        titlePanel.add(title1);
        titlePanel.add(title2);

        jPanel.add(seaPanel, BorderLayout.CENTER);
        jPanel.add(jTextArea, BorderLayout.SOUTH);
        jPanel.add(titlePanel, BorderLayout.NORTH);

        add(jPanel);
        setJMenuBar(jMenuBar);
        setVisible(true);
    }

    public void createFrame() {
        setSize(1100, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sea Battle");
        gameMenuItems[0] = new JMenuItem("Play game");
        gameMenuItems[1] = new JMenuItem("Restart");
        gameMenuItems[2] = new JMenuItem("Demo");
        gameMenuItems[3] = new JMenuItem("About");
        gameMenuItems[4] = new JMenuItem("Exit");
        placementManItems[0] = new JRadioButtonMenuItem("Auto", true);
        placementManItems[1] = new JRadioButtonMenuItem("Manual");
        placementManGroup.add(placementManItems[0]);
        placementManGroup.add(placementManItems[1]);
        placementRobotItems[0] = new JRadioButtonMenuItem("Auto", true);
        placementRobotItems[1] = new JRadioButtonMenuItem("PerelmanPlus");
        placementRobotGroup.add(placementRobotItems[0]);
        placementRobotGroup.add(placementRobotItems[1]);
        robotShootingMethodItems[0] = new JRadioButtonMenuItem("Auto", true);
        robotShootingMethodItems[1] = new JRadioButtonMenuItem("Optimal");
        robotShootingMethodGroup.add(robotShootingMethodItems[0]);
        robotShootingMethodGroup.add(robotShootingMethodItems[1]);
        for (int i = 0; i < gameMenuItems.length; i++) {
            final int finalI = i;
            gameMenuItems[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (finalI < 3) setVisible(false);
                    GameController.doMenu(finalI);
                }
            });

        }
        placementManItems[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementManItems[0].isSelected()) GameController.doPlacementAuto(true);
            }
        });
        placementManItems[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementManItems[1].isSelected()) GameController.doPlacementAuto(false);
            }
        });
        placementRobotItems[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementRobotItems[0].isSelected()) GameController.doPerelmanPlus(false);
            }
        });
        placementRobotItems[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (placementRobotItems[1].isSelected()) GameController.doPerelmanPlus(true);
            }
        });
        robotShootingMethodItems[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (robotShootingMethodItems[0].isSelected()) GameController.doOptimal(false);
            }
        });
        robotShootingMethodItems[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (robotShootingMethodItems[1].isSelected()) GameController.doOptimal(true);
            }
        });

        gameMenu.add(gameMenuItems[0]);
        gameMenu.add(gameMenuItems[1]);
        gameMenu.add(gameMenuItems[2]);
        gameMenu.add(gameMenuItems[3]);
        gameMenu.add(gameMenuItems[4]);
        placementMan.add(placementManItems[0]);
        placementMan.add(placementManItems[1]);
        placementRobot.add(placementRobotItems[0]);
        placementRobot.add(placementRobotItems[1]);
        robotShootingMethod.add(robotShootingMethodItems[0]);
        robotShootingMethod.add(robotShootingMethodItems[1]);
        settings.add(placementMan);
        settings.add(placementRobot);
        settings.add(robotShootingMethod);
        jMenuBar.add(gameMenu);
        jMenuBar.add(settings);
        setJMenuBar(jMenuBar);
        setVisible(true);
    }

    public void update(Color[][] col, int num) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (num == 1) {
                    SeaPanel.color1[i - 1][j - 1] = col[i][j];
                }
                if (num == 2) {
                    SeaPanel.color2[i - 1][j - 1] = col[i][j];
                }
            }
        }
    }

    public void textOut(String s) {
        Font font = new Font("Default", Font.ITALIC, 20);
        jTextArea.setBackground(Color.GREEN);
        jTextArea.setFont(font);
        jTextArea.setText(s);
    }
}

class SeaPanel extends JPanel {
    public static Color[][] color1 = new Color[10][10];
    public static Color[][] color2 = new Color[10][10];

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 1100, 600);
        g.setColor(Color.BLACK);
        for (int i = 0; i < 11; i++) {
            g.drawLine(i * 50, 0, i * 50, 500);
            g.drawLine(0, i * 50, 500, i * 50);

        }
        for (int i = 0; i < 11; i++) {
            g.drawLine(i * 50 + 550, 0, i * 50 + 550, 500);
            g.drawLine(550, i * 50, 1050, i * 50);

        }
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                g.setColor(color1[x][y]);
                g.fillRect(x * 50 + 1, y * 50 + 1, 49, 49);
            }
        }
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                g.setColor(color2[x][y]);
                g.fillRect(x * 50 + 1 + 550, y * 50 + 1, 49, 49);
            }
        }
    }

}