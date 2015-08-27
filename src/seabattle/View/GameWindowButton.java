package seabattle.View;

import seabattle.Controller.GameController;
import seabattle.Model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ivan on 23.05.2015.
 */
public class GameWindowButton extends JFrame {
    JPanel jPanel = new JPanel();
    JPanel field1Panel = new JPanel();
    JPanel field2Panel = new JPanel();
    JPanel titlePanel = new JPanel();
    JTextArea jTextArea = new JTextArea();
    JLabel title1 = new JLabel("man", SwingConstants.CENTER);
    JLabel title2 = new JLabel("robot", SwingConstants.CENTER);
    JButton[][] field1Buttons = new JButton[11][11];
    JButton[][] field2Buttons = new JButton[11][11];

    JMenuBar jMenuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game Menu");
    JMenuItem[] gameMenuItems = new JMenuItem[5];
    JMenu settings = new JMenu("Settings");
    JMenu placementMan = new JMenu("Man's ships Placement");
    JMenu placementRobot = new JMenu("Robot's ships Placement");
    JRadioButtonMenuItem[] placementManItems = new JRadioButtonMenuItem[2];
    ButtonGroup placementManGroup = new ButtonGroup();
    JRadioButtonMenuItem[] placementRobotItems = new JRadioButtonMenuItem[2];
    ButtonGroup placementRobotGroup = new ButtonGroup();


    public void init() {
        setSize(1100, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sea Battle");

        jPanel.setLayout(new BorderLayout());
        field1Panel.setLayout(new GridLayout(11, 11));
        field2Panel.setLayout(new GridLayout(11, 11));
        titlePanel.setLayout(new GridLayout(1, 2));

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                field1Panel.add(field1Buttons[i][j] = new JButton("   "));
                field2Panel.add(field2Buttons[i][j] = new JButton("   "));
                if (i > 0 && j > 0) {
                    field1Buttons[i][j].setBackground(Color.CYAN);
                    field2Buttons[i][j].setBackground(Color.CYAN);
                }
                final int finalJ = j;
                final int finalI = i;
                field2Buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameController.doClick(new Point(finalJ - 1, finalI - 1));
                    }
                });
                field1Buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameController.doClick(new Point(finalJ - 1, finalI - 1));
                    }
                });
            }
        }
        for (int k = 0; k < 10; k++) {
            field1Buttons[0][k + 1].setText(Character.toString((char) ('a' + k)));
            field1Buttons[k + 1][0].setText(k != 9 ? " " + Integer.toString(k + 1) : Integer.toString(k + 1));
            field2Buttons[0][k + 1].setText(Character.toString((char) ('a' + k)));
            field2Buttons[k + 1][0].setText(k != 9 ? " " + Integer.toString(k + 1) : Integer.toString(k + 1));
        }
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


        gameMenu.add(gameMenuItems[0]);
        gameMenu.add(gameMenuItems[1]);
        gameMenu.add(gameMenuItems[2]);
        gameMenu.add(gameMenuItems[3]);
        gameMenu.add(gameMenuItems[4]);
        placementMan.add(placementManItems[0]);
        placementMan.add(placementManItems[1]);
        placementRobot.add(placementRobotItems[0]);
        placementRobot.add(placementRobotItems[1]);
        settings.add(placementMan);
        settings.add(placementRobot);
        jMenuBar.add(gameMenu);
        jMenuBar.add(settings);

        titlePanel.add(title1);
        titlePanel.add(title2);

        jPanel.add(field1Panel, BorderLayout.WEST);
        jPanel.add(field2Panel, BorderLayout.EAST);
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

        gameMenu.add(gameMenuItems[0]);
        gameMenu.add(gameMenuItems[1]);
        gameMenu.add(gameMenuItems[2]);
        gameMenu.add(gameMenuItems[3]);
        gameMenu.add(gameMenuItems[4]);
        placementMan.add(placementManItems[0]);
        placementMan.add(placementManItems[1]);
        placementRobot.add(placementRobotItems[0]);
        placementRobot.add(placementRobotItems[1]);
        settings.add(placementMan);
        settings.add(placementRobot);
        jMenuBar.add(gameMenu);
        jMenuBar.add(settings);
        setJMenuBar(jMenuBar);
        setVisible(true);
    }

    public void update(String[][] str, Color[][] col, int num) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (num == 1) field1Buttons[i][j].setText(str[i][j]);
                if (num == 2) field2Buttons[i][j].setText(str[i][j]);
            }
        }
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (num == 1) field1Buttons[i][j].setBackground(col[i][j]);
                if (num == 2) field2Buttons[i][j].setBackground(col[i][j]);
            }
        }
    }

    public void setFieldsZero() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                field1Buttons[i][j].setText("   ");
                field2Buttons[i][j].setText("   ");
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
