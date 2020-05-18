// TODO make a JComboBoxCustom so that the text isn't so small
// TODO pass charMts into makeTeam so that the cpu doesn't have to recalculate?
// TODO consider removing team
// TODO add the coloured circle to FireLogo.png

package ui;

import util.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class UI {

    // HARD-CODED CONSTANTS
    private static final int FRAME_WIDTH = 1366 + 20;
    private static final int FRAME_HEIGHT = 768 + 55;
    private static final int CT_FIELD_HEIGHT = 40;
    private static final int CT_Y_INCREMENT = 100;
    private static final int CT_P1_Y = 100;
    private static final int CT_P2_Y = 100 + CT_Y_INCREMENT;
    private static final int CT_P3_Y = 100 + 2 * CT_Y_INCREMENT;
    private static final int CT_P4_Y = 100 + 3 * CT_Y_INCREMENT;
    private static final int JCOMBOBOX_CHAR_WIDTH = 90;
    private static final int JCOMBOBOX_CHAR_X = 50;
    private static final int JCOMBOBOX_WEAPON_WIDTH = 180;
    private static final int JCOMBOBOX_WEAPON_X = 200;
    private static final int JLABEL_MT_WIDTH = 150;
    private static final int JLABEL_MT_X = 1000;
    private static final int CT_TEXTSIZE = 18;
    private static final int CT_NEXT_WIDTH = 90;
    private static final int CT_NEXT_X = FRAME_WIDTH - (CT_NEXT_WIDTH + 64);
    private static final int CT_NEXT_Y = FRAME_HEIGHT - (CT_FIELD_HEIGHT + 128);

    // GAMEPLAY
    private static final int ICON_Y_INCREMENT = 52;
    private static final int ELEM_ICON_X = 469;
    private static final int ELEM_ICON_Y = 37;
    private static final int ELEM_ICON_SIZE = 20;
    private static final int CHAR_MIDDLE_PIC_X = 479;
    private static final int CHAR_MIDDLE_PIC_Y = 12;
    private static final int CHAR_MIDDLE_PIC_SIZE = 36;
    private static final int CHAR_SIDE_PIC_INCREMENT = 92;
    private static final int CHAR_SIDE_PIC_X = 36;
    private static final int CHAR_SIDE_PIC_Y = 63;
    private static final int CHAR_SIDE_PIC_SIZE = 64;
    private static final int ARENA_SIZE = 13;


    private static final Color DEFAULT_BKG = new Color(0, 0, 0);
    private static final String IMG_LOCATION = "././data/img/";
    private static final String GAMEPLAY_BKG_LOCATION = IMG_LOCATION + "gameplayBKGv3.png";
    private static final ImageIcon SVOLK_LOGO_ICON = new ImageIcon(IMG_LOCATION + "sVolkLogo.png");
    private static final ImageIcon P1_BIG_MARKER_ICON = new ImageIcon(IMG_LOCATION + "P1Big.png");
    private static final ImageIcon P1_SMALL_MARKER_ICON = new ImageIcon(IMG_LOCATION + "P1Small.png");
    private static final ImageIcon P2_BIG_MARKER_ICON = new ImageIcon(IMG_LOCATION + "P2Big.png");
    private static final ImageIcon P2_SMALL_MARKER_ICON = new ImageIcon(IMG_LOCATION + "P2Small.png");
    private static final ImageIcon SVOLK_ICON = new ImageIcon(IMG_LOCATION + "VolkMarker.png");
    private static final ImageIcon FIRE_ICON = new ImageIcon(IMG_LOCATION + "FireLogo.png");

    // CONSTANTS CALLED AFTER INITIALIZATION
    private String[] availChars = ProcessTxt.findAvailChars();
    Char defaultChar = ProcessTxt.CHAR_INFO_DICTIONARY.get(availChars[0]);

    // VARIABLES FOR THE GAME
    private boolean gameRunning;

    // VARIABLES FOR THE TEAM
    private JFrame fChooseTeam;
    private JFrame fGamePlay;
    private Char[] teamChars;
    private String[] teamNames;
    private String[] weaponNames;
    private int[] charMts;

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

    // VARS FOR MT LABELS
    private JLabelCustom mt1;
    private JLabelCustom mt2;
    private JLabelCustom mt3;
    private JLabelCustom mt4;
    private JLabelCustom mtTeam;

    public UI() {
        this.runGame();
    }

    // runs the game after initiation is complete.
    private void runGame() {
        String defaultCName = availChars[0];

        this.fChooseTeam = new JFrame("sVOLK_v2: Team Selection");
        this.teamNames = new String[]{defaultCName, defaultCName, defaultCName, defaultCName};
        this.weaponNames = new String[4];
        this.charMts = new int[4];

        CTInitFrame();
        initWeaponChoices();
        chooseTeam();
    }

    /**
     * CHARACTER SELECTION JFRAME: INITIATION
     **/

    // sets the defaults of the big frame
    private void CTInitFrame() {
        fChooseTeam.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        fChooseTeam.getContentPane().setBackground(DEFAULT_BKG);
        fChooseTeam.setLayout(null);
        fChooseTeam.setIconImage(SVOLK_LOGO_ICON.getImage());
        fChooseTeam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // select 4 characters and their weapons; when "Next" pressed, calls makeTeam and initiates setupSVolk.
    private void chooseTeam() {
        System.out.println("\n♥ ♥ ♥ Team Initiating ♥ ♥ ♥");

        initCharKits();
        initMtLabels();

        JButton nextB = new JButton("Next");
        nextB.setBounds(CT_NEXT_X, CT_NEXT_Y, CT_NEXT_WIDTH, CT_FIELD_HEIGHT);
        nextB.addActionListener(e -> {
            makeTeam();
            setupSVolk();
        });

        fChooseTeam.add(nextB);

        fChooseTeam.setVisible(true);
    }

    private void initMtLabels() {
        Arrays.fill(this.charMts, defaultChar.getMt());

        this.mt1 = new JLabelCustom(String.valueOf(charMts[0]), CT_TEXTSIZE);
        mt1.setBounds(JLABEL_MT_X, CT_P1_Y, JLABEL_MT_WIDTH, CT_FIELD_HEIGHT);

        this.mt2 = new JLabelCustom(String.valueOf(charMts[1]), CT_TEXTSIZE);
        mt2.setBounds(JLABEL_MT_X, CT_P2_Y, JLABEL_MT_WIDTH, CT_FIELD_HEIGHT);

        this.mt3 = new JLabelCustom(String.valueOf(charMts[2]), CT_TEXTSIZE);
        mt3.setBounds(JLABEL_MT_X, CT_P3_Y, JLABEL_MT_WIDTH, CT_FIELD_HEIGHT);

        this.mt4 = new JLabelCustom(String.valueOf(charMts[3]), CT_TEXTSIZE);
        mt4.setBounds(JLABEL_MT_X, CT_P4_Y, JLABEL_MT_WIDTH, CT_FIELD_HEIGHT);

        fChooseTeam.add(mt1);
        fChooseTeam.add(mt2);
        fChooseTeam.add(mt3);
        fChooseTeam.add(mt4);

        this.mtTeam = new JLabelCustom(String.valueOf(charMts[0] * 4), CT_TEXTSIZE);
        mtTeam.setBounds(JLABEL_MT_X, CT_P4_Y + CT_Y_INCREMENT, JLABEL_MT_WIDTH, CT_FIELD_HEIGHT);
        fChooseTeam.add(mtTeam);
    }

    // inits character choosing and sets up boxes for weapon choices, filling them in as needed.
    private void initCharKits() {
        this.chooseChar1 = new JComboBox<>(availChars);
        chooseChar1.setBounds(JCOMBOBOX_CHAR_X, CT_P1_Y, JCOMBOBOX_CHAR_WIDTH, CT_FIELD_HEIGHT);
        chooseChar1.setSelectedIndex(0);
        chooseChar1.addActionListener(e -> updatePlayer((String) chooseChar1.getSelectedItem(), 1));

        this.chooseChar2 = new JComboBox<>(availChars);
        chooseChar2.setBounds(JCOMBOBOX_CHAR_X, CT_P2_Y, JCOMBOBOX_CHAR_WIDTH, CT_FIELD_HEIGHT);
        chooseChar2.setSelectedIndex(0);
        chooseChar2.addActionListener(e -> updatePlayer((String) chooseChar2.getSelectedItem(), 2));

        this.chooseChar3 = new JComboBox<>(availChars);
        chooseChar3.setBounds(JCOMBOBOX_CHAR_X, CT_P3_Y, JCOMBOBOX_CHAR_WIDTH, CT_FIELD_HEIGHT);
        chooseChar3.setSelectedIndex(0);
        chooseChar3.addActionListener(e -> updatePlayer((String) chooseChar3.getSelectedItem(), 3));

        this.chooseChar4 = new JComboBox<>(availChars);
        chooseChar4.setBounds(JCOMBOBOX_CHAR_X, CT_P4_Y, JCOMBOBOX_CHAR_WIDTH, CT_FIELD_HEIGHT);
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
        chooseWep1.setBounds(JCOMBOBOX_WEAPON_X, CT_P1_Y, JCOMBOBOX_WEAPON_WIDTH, CT_FIELD_HEIGHT);
        chooseWep1.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep1);
        chooseWep1.addActionListener(e -> updateWeapon((String) chooseWep1.getSelectedItem(), 1));

        chooseWep2 = new JComboBox<>(new DefaultComboBoxModel<>(defaultWeps));
        chooseWep2.setBounds(JCOMBOBOX_WEAPON_X, CT_P2_Y, JCOMBOBOX_WEAPON_WIDTH, CT_FIELD_HEIGHT);
        chooseWep2.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep2);
        chooseWep2.addActionListener(e -> updateWeapon((String) chooseWep2.getSelectedItem(), 2));

        chooseWep3 = new JComboBox<>(new DefaultComboBoxModel<>(defaultWeps));
        chooseWep3.setBounds(JCOMBOBOX_WEAPON_X, CT_P3_Y, JCOMBOBOX_WEAPON_WIDTH, CT_FIELD_HEIGHT);
        chooseWep3.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep3);
        chooseWep3.addActionListener(e -> updateWeapon((String) chooseWep3.getSelectedItem(), 3));

        chooseWep4 = new JComboBox<>(new DefaultComboBoxModel<>(defaultWeps));
        chooseWep4.setBounds(JCOMBOBOX_WEAPON_X, CT_P4_Y, JCOMBOBOX_WEAPON_WIDTH, CT_FIELD_HEIGHT);
        chooseWep4.setSelectedItem(defaultWeapon);
        fChooseTeam.add(chooseWep4);
        chooseWep4.addActionListener(e -> updateWeapon((String) chooseWep4.getSelectedItem(), 4));
    }

    /**
     * NEW CHAR PICKED PROCEDURES
     **/
    // called when a new player was picked. Updates teamNames[i] and calls updateWeapons.
    // TODO: Make this call updateWyrmprints later on.
    private void updatePlayer(String cName, int playerNum) throws RuntimeException {
        this.teamNames[(playerNum - 1)] = cName;
        updateWeaponChoices(this.teamNames[(playerNum - 1)], playerNum);
        updateMt(playerNum);
    }

    // called when the weapons selected is changed.
    private void updateWeapon(String weaponName, int playerNum) {
        int index = playerNum - 1;
        this.weaponNames[index] = weaponName;
        updateMt(playerNum);
    }

    // changes the weapons available for picking and sets default weapon to Battleworn [wT] when corresponding character is changed.
    private void updateWeaponChoices(String cName, int playerNum) throws RuntimeException {
        String wT = ProcessTxt.CHAR_INFO_DICTIONARY.get(cName).getWT();
        switch (playerNum) {
            case 1:
                chooseWep1.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
                chooseWep1.setSelectedItem("Battleworn " + wT);
                break;
            case 2:
                chooseWep2.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
                chooseWep2.setSelectedItem("Battleworn " + wT);
                break;
            case 3:
                chooseWep3.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
                chooseWep3.setSelectedItem("Battleworn " + wT);
                break;
            case 4:
                chooseWep4.setModel(new DefaultComboBoxModel<>(Weapon.getAvailWeapons(wT)));
                chooseWep4.setSelectedItem("Battleworn " + wT);
                break;
            default:
                throw new RuntimeException("Invalid player number: " + playerNum);
        }

        fChooseTeam.setVisible(true);
    }

    // updates might displayed when any field is changed.
    private void updateMt(int playerNum) throws RuntimeException {
        int index = playerNum - 1;
        Weapon targetWeapon = ProcessTxt.WEAPONS_DICTIONARY.get(weaponNames[index]);
        int mt = Char.getNewMt(teamNames[index], targetWeapon);
        switch (playerNum) {
            case 1:
                mt1.setText(String.valueOf(mt));
                break;
            case 2:
                mt2.setText(String.valueOf(mt));
                break;
            case 3:
                mt3.setText(String.valueOf(mt));
                break;
            case 4:
                mt4.setText(String.valueOf(mt));
                break;
            default:
                throw new RuntimeException("Invalid player number: " + playerNum);
        }
        this.charMts[index] = mt;

        int temp = 0;
        for (int i : charMts) {
            temp += i;
        }
        mtTeam.setText(String.valueOf(temp));

        fChooseTeam.setVisible(true);
    }

    /**
     * NEXT BUTTON CLICKED: FINALIZES TEAM AND SENDS IT OFF TO GAMEPLAY
     **/
    // makes a Team from teamNames & weaponNames by initiating the characters and returns the created Team.
    // TODO: Add Wyrmprints here.
    private void makeTeam() {
        Char[] returnTeam = new Char[4];
        for (int i = 0; i < 4; i++) {
            System.out.println("\n♦ ♦ ♦ Initializing P" + (i + 1) + " as " + teamNames[i]);
            Char c = Char.initChar(teamNames[i]);
            returnTeam[i] = c;
            c.setWeapon(weaponNames[i]);
            Char.setNewMt(c);
        }
        this.teamChars = returnTeam;
        new Team(returnTeam);
    }

    /**
     * GAMEPLAY INITIALIZATION
     */
    // calls necessary initiation functions for sVolk Gameplay stage.
    private void setupSVolk() {
        this.fGamePlay = new JFrame("sVOLK_v2");
        fGamePlay.setResizable(true);
        GPInitGraphics();
        GPInitFrame();
        gameRunning = true;
        GPInitGame();
    }

    // loads the sVOLK bkg and then calls player bars initiation.
    private void GPInitGraphics() {
        try {
            final Image gameplayBKG = ImageIO.read(new File(GAMEPLAY_BKG_LOCATION));
            fGamePlay.setContentPane(new JPanelBkg(gameplayBKG));
        } catch (IOException e) {
            System.out.println("Gameplay BKG not found:");
            e.printStackTrace();
        }
        configurePlayerBars();
    }

    // sets up the gameplay frame.
    private void GPInitFrame() {
        fGamePlay.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        fGamePlay.getContentPane().setBackground(DEFAULT_BKG);
        fGamePlay.setLayout(null);
        fGamePlay.setIconImage(SVOLK_LOGO_ICON.getImage());
        fGamePlay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fGamePlay.setVisible(true);
    }

    // sets the 4 element lil circles in the middle of the screen
    private void configurePlayerBars() {
        for (int i = 0; i < 4; i++) {
            switch (teamChars[i].getElem()) {
                case "Flame":
                    JLabel elemIcon = new JLabel(FIRE_ICON);
                    elemIcon.setBounds(ELEM_ICON_X, (ELEM_ICON_Y + ICON_Y_INCREMENT * i), ELEM_ICON_SIZE, ELEM_ICON_SIZE);
                    fGamePlay.add(elemIcon);
                    break;
                case "Wind":
                    // todo add wind
                    break;
                default:
                    throw new RuntimeException("Cannot determine the element of P" + (i + 1));
            }

            Image unsizedCharPic = new ImageIcon(IMG_LOCATION + "chars/" + teamChars[i].getName() + ".png").getImage();

            JLabel charSidePic = new JLabel(new ImageIcon(unsizedCharPic));
            charSidePic.setBounds(CHAR_SIDE_PIC_X, (CHAR_SIDE_PIC_Y + CHAR_SIDE_PIC_INCREMENT * i), CHAR_SIDE_PIC_SIZE, CHAR_SIDE_PIC_SIZE);
            fGamePlay.add(charSidePic);

            JLabel charMiddlePic = new JLabel(new ImageIcon((unsizedCharPic.getScaledInstance(CHAR_MIDDLE_PIC_SIZE, CHAR_MIDDLE_PIC_SIZE, Image.SCALE_DEFAULT))));
            charMiddlePic.setBounds(CHAR_MIDDLE_PIC_X, (CHAR_MIDDLE_PIC_Y + ICON_Y_INCREMENT * i), CHAR_MIDDLE_PIC_SIZE, CHAR_MIDDLE_PIC_SIZE);
            fGamePlay.add(charMiddlePic);
        }
    }

    // todo
    // this so far only works for player 1, person 1.
    private void GPInitGame() {
        fGamePlay.add(new JPanelPlay());
        fGamePlay.setVisible(true);
        // place p1 marker at coordinate x, y
        /**
         * X) import marker images as imageicons
         * 1.5) check that enterkeys works via println
         * 2) when game starts, initiate the marker at (5, 9)
         * 3) when arrowkey pressed, move marker in the correct direction by CONSTANT pixels
         * 4) check that when arrowkey pressed, the person can actually move up or whatever
         * 5) plop a volk at (7, 7)
         * 6) when ya run into volk u move up two instead of 1
         * 7) do damage to volk when "f" is pressed (not held) and volk is within WEP_DEP_CONSTANT squares.
         */
    }

}
