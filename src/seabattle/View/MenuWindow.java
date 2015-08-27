package seabattle.View;

import seabattle.Controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ivan on 24.05.2015.
 */
public class MenuWindow extends JFrame {
    private JPanel jPanel = new JPanel();
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel title = new JLabel("HEAD MENU", SwingConstants.CENTER);
    Font font = new Font("Verdana", Font.PLAIN, 15);
    private JButton[] jButtons = new JButton[4];

    public void init() {
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sea Battle");
        jPanel.setLayout(new BorderLayout());
        title.setFont(font);
        titlePanel.setLayout(new GridLayout(1, 1));
        buttonPanel.setLayout(new GridLayout(4, 1));
        jButtons[0] = new JButton("Play game");
        jButtons[1] = new JButton("Demo");
        jButtons[2] = new JButton("About");
        jButtons[3] = new JButton("Exit");
        titlePanel.add(title);
        for (int i = 0; i < 4; i++) {
            final int finalI = i;
            buttonPanel.add(jButtons[i]);
            jButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameController.doMenu(finalI);
                }
            });
        }
        jPanel.add(titlePanel, BorderLayout.NORTH);
        jPanel.add(buttonPanel, BorderLayout.CENTER);
        add(jPanel);
        setVisible(true);
    }
}
