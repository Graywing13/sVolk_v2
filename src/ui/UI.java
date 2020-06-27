// TODO make a JComboBoxCustom so that the text isn't so small
// TODO pass charMts into makeTeam so that the cpu doesn't have to recalculate?
// TODO consider removing team
// TODO add the coloured circle to FireLogo.png

// Notes:
//   - P1_FOCUS and P2_FOCUS have to be 23x23; see Enemy.java, overlayVolkMarkers(), for details.

package ui;

import util.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.Timer;

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
    private static final int ICON_Y_INCREMENT = 60;
    private static final int ELEM_ICON_X = 468;
    private static final int ELEM_ICON_Y = 60;
    private static final int ELEM_ICON_SIZE = 25;
    private static final int CHAR_MIDDLE_PIC_X = 479;
    private static final int CHAR_MIDDLE_PIC_Y = 32;
    private static final int CHAR_MIDDLE_PIC_SIZE = 36;
    private static final int CHAR_SIDE_PIC_INCREMENT = 97;
    private static final int CHAR_SIDE_PIC_X = 36;
    private static final int CHAR_SIDE_PIC_Y = 63;
    private static final int CHAR_SIDE_PIC_SIZE = 64;
    public static final int GRID_X = 539;
    public static final int GRID_Y = 291;
    public static final int GRID_SQUARE_SIDE_LENGTH = 18;
    public static final int GRID_SQUARE_DISTANCE = 23;
    public static final int ARENA_SIZE = 13;

    // VARS FOR BARS
    public JLabelCustom currentState;

    private static final Color DEFAULT_BKG = new Color(0, 0, 0);
    private static final String IMG_LOCATION = "././data/img/";
    private static final String GAMEPLAY_BKG_LOCATION = IMG_LOCATION + "gameplayBKGv5.png";
    private static final ImageIcon SVOLK_LOGO_ICON = new ImageIcon(IMG_LOCATION + "sVolkLogo.png");
    public static final String P1_BIG_MARKER_LOCATION = IMG_LOCATION + "P1Big.png";
    public static final String P1_SMALL_MARKER_LOCATION = IMG_LOCATION + "P1Small.png";
    public static final String P2_BIG_MARKER_LOCATION = IMG_LOCATION + "P2Big.png";
    public static final String P2_SMALL_MARKER_LOCATION = IMG_LOCATION + "P2Small.png";
    public static final String VOLK_MARKER_LOCATION = IMG_LOCATION + "VolkMarker.png";
    public static final RotateLabel P1_FOCUS = new RotateLabel(IMG_LOCATION + "P1Focus.png", 0);
    public static final RotateLabel P2_FOCUS = new RotateLabel(IMG_LOCATION + "P2Focus.png", 0);
    private static final ImageIcon FIRE_ICON = new ImageIcon(IMG_LOCATION + "FireLogo.png");
    public static final double CHAR_DIRXN_INIT = Math.PI / 4;
    public static final double VOLK_DIRXN_INIT = 5 * Math.PI / 4;

    // CONSTANTS CALLED AFTER INITIALIZATION
    private String[] availChars = ProcessTxt.findAvailChars();
    private Char defaultChar = ProcessTxt.CHAR_INFO_DICTIONARY.get(availChars[0]);
    public GridSquare[] grid_13x13 = new GridSquare[ARENA_SIZE * ARENA_SIZE];

    // VARIABLES FOR THE GAME
    private boolean gameRunning; // todo remove?
    public JPanelPlay JPP;
    private JFrame fChooseTeam;
    private JFrame fGamePlay;
    public Enemy sVolk = new Enemy(this);
    public DrawRect volkHPBar;
    public DrawRect volkODBar;
    private JLabelCustom shadowRect;

    // VARIABLES FOR THE TEAM
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

    // GamePlay
    private static int msTimeLeft = 10 * 60 * 1000;
    private Timer timer;

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
            fChooseTeam.setVisible(false);
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
        int mt = Char.calculateNewMt(teamNames[index], targetWeapon);
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
        initGridSquares();
    }

    private void GPInitClearScreen() {
        shadowRect = new JLabelCustom("", 24) {
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        shadowRect.setBounds(0, 0, 1366, 768);
        shadowRect.setBackground(new Color(0,0,0,125));
        shadowRect.setHorizontalAlignment(JLabel.CENTER);
        shadowRect.setVerticalAlignment(JLabel.CENTER);
        shadowRect.setOpaque(false);
        shadowRect.setVisible(false);
        fGamePlay.add(shadowRect);
    }

    // loads the sVOLK bkg and then calls player bars and player initiation.
    private void GPInitGraphics() {
        try {
            final Image gameplayBKG = ImageIO.read(new File(GAMEPLAY_BKG_LOCATION));
            fGamePlay.setContentPane(new JPanelBkg(gameplayBKG));
        } catch (IOException e) {
            System.out.println("Gameplay BKG not found:");
            e.printStackTrace();
        }
        GPInitClearScreen();
        configureBars();
        configurePlayerGraphics();
    }

    private void configureBars() {
        currentState = new JLabelCustom("", 14);
        currentState.setBounds(685, 48, 100, 16); // todo hard code values
        currentState.setHorizontalAlignment(JLabel.CENTER);
        currentState.setVisible(true);
        fGamePlay.add(currentState);

        volkHPBar = DrawRect.initVolkHPBar();
        volkHPBar.setLocation(587, 39);
        fGamePlay.add(volkHPBar);

        volkODBar = DrawRect.initVolkODBar();
        volkODBar.setLocation(637, 47);
        fGamePlay.add(volkODBar);
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
    private void configurePlayerGraphics() {
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

    // makes the grid that movement is based on.
    private void initGridSquares() {
        int i = 0;
        for (int x = 0; x < ARENA_SIZE; x++) {
            for (int y = 0; y < ARENA_SIZE; y++) {
                grid_13x13[i] = new GridSquare(x, y);
                i++;
            }
        }

        GridSquare.editSqrStates(grid_13x13);

        for (GridSquare sqr : grid_13x13) {
            if (!(sqr.getSqrState() == 'n')) {
                fGamePlay.add(sqr.initSquare());
            }
        }
    }

    // starts the game up. // todo rename all these to more logical things and maybe draw a map who knows
    private void GPInitGame() {
        JPP = new JPanelPlay(teamChars, grid_13x13, sVolk);
        fGamePlay.add(JPP);
        fGamePlay.setVisible(true);
        determineMarkerTypes();
        placePlayers();

        fGamePlay.add(sVolk.setVolkMarker(6, 6));
        fGamePlay.add(P1_FOCUS);
        fGamePlay.add(P2_FOCUS);
        initTimeComponents();
    }

    private void initTimeComponents() {
        JLabelCustom timeElapsed = new JLabelCustom("", 18);
        timeElapsed.setText(JLabelCustom.formatTime(msTimeLeft));
        timeElapsed.setBounds(900, 40, 100, 20); // todo hard code values
        timeElapsed.setVisible(true);
        fGamePlay.add(timeElapsed);

        timer = new Timer(250, e -> {
            msTimeLeft -= 250;
            if (msTimeLeft % 1000 == 0) {
                timeElapsed.setText(JLabelCustom.formatTime(msTimeLeft));
                if (msTimeLeft <= 0) gameOver();
            }
        });

        timer.start();
    }

    private void placePlayers() { // todo add the other characters.
        // initiate and place player markers at coordinate x, y
        fGamePlay.add(teamChars[0].setCharMarker(4, 8, JPP));
        fGamePlay.add(teamChars[1].setCharMarker(2, 8, JPP));
    }

    private void determineMarkerTypes() { // todo add to this + modify it to depend on user input...
        teamChars[0].setMarker(true, true);
        teamChars[1].setMarker(false, true);
    }

    public void gameOver() {
        System.out.println("gg no re"); // todo edit and tbh maybe we need the gamerunning variable as a STOP ALL button
        timer.stop();
        sVolk.state = "defeat";
        shadowRect.setText("<html><div style='text-align: center;'>Clear. <br/> <br/> Clear time: " + JLabelCustom.formatTime(600000 - msTimeLeft) + "</div></html>");
        shadowRect.setVisible(true);
    }

}
