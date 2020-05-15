package ui;

import util.Char;
import util.ProcessTxt;
import util.Team;
import util.Weapon;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class UI {

    // HARD-CODED CONSTANTS
    private static final int FRAME_WIDTH = 1366;
    private static final int FRAME_HEIGHT = 768;
    private static final Color DEFAULT_BKG = new Color(255, 255, 255);
    private static final ImageIcon ICON_LOCATION = new ImageIcon("././data/logo.png");

    // CONSTANTS CALLED AFTER INITIALIZATION
    private String[] availChars = ProcessTxt.findAvailChars();

    // VARIABLES FOR THE TEAM
    private JFrame fChooseTeam;
    private Team team; // todo add this in later?
    private String[] teamNames;
    private String[] weaponNames;

    // VARS FOR WEAPONS
    private JComboBox<String> chooseChar1;
    private JComboBox<String> chooseChar2;
    private JComboBox<String> chooseChar3;
    private JComboBox<String> chooseChar4;

    // VARS FOR WEAPONS
    private JComboBox<String> chooseWep1;
    private JComboBox<String> chooseWep2;
    private JComboBox<String> chooseWep3;
    private JComboBox<String> chooseWep4;

    public UI() {

    }

    // runs the game after initiation is complete.
    public void runGame() {
        Arrays.sort(availChars);
        String defaultCName = availChars[0];

        this.fChooseTeam = new JFrame("sVOLK_v2");
        this.teamNames = new String[]{defaultCName, defaultCName, defaultCName, defaultCName};
        this.weaponNames = new String[4];
        boolean teamSelected = false; // todo incorporate

        initFrame();
        initWeaponChoices();
        selectTeam();
    }

    /**
     * CHARACTER SELECTION JFRAME: INITIATION
     **/

    // sets the defaults of the big frame
    private void initFrame() {
        fChooseTeam.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        fChooseTeam.getContentPane().setBackground(DEFAULT_BKG);
        fChooseTeam.setLayout(null);
        fChooseTeam.setIconImage(ICON_LOCATION.getImage());
        fChooseTeam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // select 4 characters and their weapons
    private void selectTeam() {
        System.out.println("\n♥ ♥ ♥ Team Initiating ♥ ♥ ♥");

        initCharKits();

        JButton nextB = new JButton("Next");
        nextB.setBounds(500, 500, 75, 20);
        nextB.addActionListener(e -> {
            this.team = makeTeam();
            //setupSVolk(); // TODO
        });

        fChooseTeam.add(nextB);

        fChooseTeam.setVisible(true);
    }
    // inits character choosing and sets up boxes for weapon choices, filling them in as needed.
    private void initCharKits() {
        this.chooseChar1 = new JComboBox<>(availChars);
        chooseChar1.setBounds(50, 100, 90, 20);
        chooseChar1.setSelectedIndex(0);
        chooseChar1.addActionListener(e -> updatePlayer((String) chooseChar1.getSelectedItem(), 1));

        this.chooseChar2 = new JComboBox<>(availChars);
        chooseChar2.setBounds(50, 150, 90, 20);
        chooseChar2.setSelectedIndex(0);
        chooseChar2.addActionListener(e -> updatePlayer((String) chooseChar2.getSelectedItem(), 2));

        this.chooseChar3 = new JComboBox<>(availChars);
        chooseChar3.setBounds(50, 200, 90, 20);
        chooseChar3.setSelectedIndex(0);
        chooseChar3.addActionListener(e -> updatePlayer((String) chooseChar3.getSelectedItem(), 3));

        this.chooseChar4 = new JComboBox<>(availChars);
        chooseChar4.setBounds(50, 250, 90, 20);
        chooseChar4.setSelectedIndex(0);
        chooseChar4.addActionListener(e -> updatePlayer((String) chooseChar4.getSelectedItem(), 4));

        fChooseTeam.add(chooseChar1);
        fChooseTeam.add(chooseChar2);
        fChooseTeam.add(chooseChar3);
        fChooseTeam.add(chooseChar4);
    }

    // configures weaponNames and chooseWep# JComboBoxes.
    private void initWeaponChoices() {
        String defaultWT = ProcessTxt.CHAR_INFO_DICTIONARY.get(availChars[0]).getWT();
        String[] defaultWeps = Weapon.getAvailWeapons(defaultWT);
        Arrays.sort(defaultWeps);
        String defaultWeapon = "Battleworn " + defaultWT;
        Arrays.fill(this.weaponNames, defaultWeapon);

        chooseWep1 = new JComboBox<>(new DefaultComboBoxModel<>(defaultWeps));
        chooseWep1.setBounds(200, 100, 180, 20);
        chooseWep1.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep1);
        chooseWep1.addActionListener(e -> this.weaponNames[0] = (String) chooseWep1.getSelectedItem());

        chooseWep2 = new JComboBox<>(new DefaultComboBoxModel<>(defaultWeps));
        chooseWep2.setBounds(200, 150, 180, 20);
        chooseWep2.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep2);
        chooseWep2.addActionListener(e -> this.weaponNames[1] = (String) chooseWep2.getSelectedItem());

        chooseWep3 = new JComboBox<>(new DefaultComboBoxModel<>(defaultWeps));
        chooseWep3.setBounds(200, 200, 180, 20);
        chooseWep3.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep3);
        chooseWep3.addActionListener(e -> this.weaponNames[2] = (String) chooseWep3.getSelectedItem());

        chooseWep4 = new JComboBox<>(new DefaultComboBoxModel<>(defaultWeps));
        chooseWep4.setBounds(200, 250, 180, 20);
        chooseWep4.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep4);
        chooseWep4.addActionListener(e -> this.weaponNames[3] = (String) chooseWep4.getSelectedItem());
    }

    /**
     * NEW CHAR PICKED PROCEDURES
     **/
    // called when a new player was picked. Updates teamNames[i] and calls updateWeapons.
    // TODO: Make this call updateWyrmprints later on.
    private void updatePlayer(String cName, int playerNum) throws RuntimeException {
        this.teamNames[(playerNum - 1)] = cName;
        updateWeapons(this.teamNames[(playerNum - 1)], playerNum);
    }

    // changes the weapons available for picking and sets default weapon to Battleworn ____ when corresponding character is changed.
    private void updateWeapons(String cName, int playerNum) throws RuntimeException {
        String wT = ProcessTxt.CHAR_INFO_DICTIONARY.get(cName).getWT();
        if (playerNum == 1) {
            chooseWep1.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
            chooseWep1.setSelectedItem("Battleworn " + wT);
        } else if (playerNum == 2) {
            chooseWep2.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
            chooseWep2.setSelectedItem("Battleworn " + wT);
        } else if (playerNum == 3) {
            chooseWep3.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
            chooseWep3.setSelectedItem("Battleworn " + wT);
        } else if (playerNum == 4) {
            chooseWep4.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
            chooseWep4.setSelectedItem("Battleworn " + wT);
        } else {
            throw new RuntimeException("Invalid player number: " + playerNum);
        }

        fChooseTeam.setVisible(true);
    }

    /**
     * NEXT BUTTON CLICKED: FINALIZES TEAM AND SENDS IT OFF TO GAMEPLAY
     **/
    // makes a Team from teamNames & weaponNames by initiating the characters and returns the created Team.
    // TODO: Add Wyrmprints here.
    private Team makeTeam() {
        Char[] returnTeam = new Char[4];
        for (int i = 0; i < 4; i++) {
            System.out.println("\n♦ ♦ ♦ Initializing P" + (i + 1) + " as " + teamNames[i]);
            Char c = Char.initChar(teamNames[i]);
            returnTeam[i] = c;
            c.setWeapon(weaponNames[i]);
            Char.updateMt(c);
        }

        return new Team(returnTeam);
    }
}
