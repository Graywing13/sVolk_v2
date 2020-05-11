package ui;

import util.ProcessTxt;
import util.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {

    private static final int FRAME_WIDTH = 1366;
    private static final int FRAME_HEIGHT = 768;
    private static final Color DEFAULT_BKG = new Color(255,255,255);
    private static final ImageIcon ICON_LOCATION = new ImageIcon("././data/logo.png");
    private JFrame f;
    private Team team;

    private String[] availChars = ProcessTxt.findAvailChars();

    private final String[] teamNames = new String[4];

    // runs the game after initiation is complete.
    public void runGame() {
        JFrame f = new JFrame("sVOLK_v2");
        this.f = f;

        initFrame();
        selectTeam();
    }

    // sets the defaults of the big frame
    private void initFrame() {
        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.getContentPane().setBackground(DEFAULT_BKG);
        f.setLayout(null);

        f.setIconImage(ICON_LOCATION.getImage());
    }

    // select 4 characters and their weapons // todo call initPrints in here, move initWeapons to here instead of Team.
    private void selectTeam() {
        System.out.println("\n♥ ♥ ♥ Team Initiating ♥ ♥ ♥");

        final Team[] t = new Team[1];

        JButton nextB = new JButton("Next");
        nextB.setBounds(200,100,75,20);

        JComboBox<String> chooseChar1 = new JComboBox<>(availChars);
        chooseChar1.setBounds(50, 100,90,20);

        JComboBox<String> chooseChar2 = new JComboBox<>(availChars);
        chooseChar2.setBounds(50, 150,90,20);

        JComboBox<String> chooseChar3 = new JComboBox<>(availChars);
        chooseChar3.setBounds(50, 200,90,20);

        JComboBox<String> chooseChar4 = new JComboBox<>(availChars);
        chooseChar4.setBounds(50, 250,90,20);

        nextB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teamNames[0] = (String) chooseChar1.getSelectedItem();
                teamNames[1] = (String) chooseChar2.getSelectedItem();
                teamNames[2] = (String) chooseChar3.getSelectedItem();
                teamNames[3] = (String) chooseChar4.getSelectedItem();
                System.out.println("Team: " + teamNames[0] + teamNames[1] + teamNames[2] + teamNames[3]);
                t[0] = new Team(teamNames[0], teamNames[1], teamNames[2], teamNames[3]);
            }
        });

        f.add(nextB);
        f.add(chooseChar1);
        f.add(chooseChar2);
        f.add(chooseChar3);
        f.add(chooseChar4);

        f.setVisible(true);
    }

    public static void main(String[] args) { // todo delete
        UI testUI = new UI();
        testUI.runGame();
    }
}
