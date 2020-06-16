package util;

import org.jetbrains.annotations.NotNull;
import ui.UI;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;

public class Char {

    // used for checking location relative to map
    private JPanelPlay jpp;

    // movement directions
    private static final HashSet<Integer> MOVE_DOWN = new HashSet<>(Arrays.asList(83, 40));
    private static final HashSet<Integer> MOVE_UP = new HashSet<>(Arrays.asList(87, 38));
    private static final HashSet<Integer> MOVE_LEFT = new HashSet<>(Arrays.asList(65, 37));
    private static final HashSet<Integer> MOVE_RIGHT = new HashSet<>(Arrays.asList(68, 39));
    private static final HashSet<Integer> MOVE_HIT = new HashSet<>(Arrays.asList(70, 17, 157));

    // Skill / Player switch directions
    private static final HashSet<Integer> KEY_1 = new HashSet<>(Arrays.asList(49, 55));
    private static final HashSet<Integer> KEY_2 = new HashSet<>(Arrays.asList(50, 56));
    private static final HashSet<Integer> KEY_3 = new HashSet<>(Arrays.asList(51, 57));
    private static final HashSet<Integer> KEY_4 = new HashSet<>(Arrays.asList(52, 48));


    // refer to char_info.txt for unabbreviated versions.
    String name;
    String elem;
    String wT;
    String s1N;
    String s2N;
    String a1;
    String a2;
    String a3;
    String ct;
    String cc;
    int mt, hp, str, def;
    private Ability[] cAbils = new Ability[3];
    private Skill[] cSkills = new Skill[2];
    Weapon w;

    private boolean p1Char;
    private boolean inControl;
    private RotateLabel charMarker;
    private int locationX;
    private int locationY;
    private int volkX;
    private int volkY;
    private boolean canHitVolk;
    private Enemy volk;

    // The Defaults ====================================================================================================
    public Char(String name, String elem, String wT, String s1N, String s2N, String a1, String a2, String a3, String cc, String ct, int mt, int hp, int str, int def, Weapon w) {
        this.name = name;
        this.elem = elem;
        this.wT = wT;
        this.s1N = s1N;
        this.s2N = s2N;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.cc = cc;
        this.ct = ct;
        this.mt = mt;
        this.hp = hp;
        this.str = str;
        this.def = def;
        this.w = w;
    }

    public String toString() {
        return "This is " + name + ", a " + elem + " attuned " + wT + " adventurer \n\twith the abilities " + a1 + ", " + a2 + ", and " + a3 + ", \n\tas well as the coab " + cc + " and the chain coab " + ct + ". \n\tThey currently have [might " + mt + "], [hp " + hp + "], [str " + str + "], and [def " + def + "]. They have the weapon " + w.wN + ".";
    }

    // Initialization functions ========================================================================================
    // makes a new instance of that character based on dictionary's template
    public static Char initChar(String name) {
        Char c = Char.copy(ProcessTxt.CHAR_INFO_DICTIONARY.get(name));
        c.charAbilInit(c);
        c.charSkillInit(c);
        return c;
    }

    private void charAbilInit(Char c) {
        Ability a1 = ProcessTxt.ABILITIES_DICTIONARY.get(c.getA1());
        Ability a2 = ProcessTxt.ABILITIES_DICTIONARY.get(c.getA2());
        Ability a3 = ProcessTxt.ABILITIES_DICTIONARY.get(c.getA3());
        c.cAbils[0] = a1;
        c.cAbils[1] = a2;
        c.cAbils[2] = a3;
        for (Ability ability : cAbils) {
            c.printAbilities(ability);
        }
    }

    private void charSkillInit(Char c) {
        Skill s1 = ProcessTxt.SKILLS_DICTIONARY.get(c.getS1());
        Skill s2 = ProcessTxt.SKILLS_DICTIONARY.get(c.getS2());
        c.cSkills[0] = s1;
        c.cSkills[1] = s2;
        for (Skill skill : cSkills) {
            c.printSkills(skill);
        }
    }

    // Markers =========================================================================================================
    // decides on the marker Image for the character
    public void setMarker(boolean p1, boolean control) {
        p1Char = p1;
        inControl = control;
    }

    // gives the character a marker and correspondingly sets that character in control's focus
    public JLabel setCharMarker(int x, int y, JPanelPlay jpp) {
        if (inControl) {
            this.jpp = jpp;
            this.volk = jpp.getEnemy();
            if (p1Char) {
                charMarker = new RotateLabel("./" + UI.P1_BIG_MARKER_LOCATION, UI.CHAR_DIRXN_INIT);
            } else {
                charMarker = new RotateLabel("./" + UI.P2_BIG_MARKER_LOCATION, UI.CHAR_DIRXN_INIT);
            }
            jpp.changeControl(p1Char, this);
        } else {
            if (p1Char) {
                charMarker = new RotateLabel("./" + UI.P1_SMALL_MARKER_LOCATION, UI.CHAR_DIRXN_INIT);
            } else {
                charMarker = new RotateLabel("./" + UI.P2_SMALL_MARKER_LOCATION, UI.CHAR_DIRXN_INIT);
            }
        }
        return placeChar(x, y);
    }

    // puts the character at the specified spot
    private JLabel placeChar(int x, int y) {
        locationX = x;
        locationY = y;
        canHitVolk = w.canHitEnemy(x, y, volk);
        charMarker.setBounds((UI.GRID_X + x * UI.GRID_SQUARE_DISTANCE), (UI.GRID_Y + y * UI.GRID_SQUARE_DISTANCE), UI.GRID_SQUARE_SIDE_LENGTH, UI.GRID_SQUARE_SIDE_LENGTH);
        return charMarker;
    }

    // Character Movement ==============================================================================================
    // processes information from arrow keys and places the character at the new location
    public void charEvent(int key, boolean modifier) {
        volkX = volk.getLocationX();
        volkY = volk.getLocationY();
        if (!modifier) {
            charEventRegular(key);
        } else {
            charEventModifier(key);
        }
    }

    // does a regular char movement; see more in JPanelPlay
    private void charEventRegular(int key) {
        if (MOVE_DOWN.contains(key)) {
            move(0, 1);
        } else if (MOVE_UP.contains(key)) {
            move(0, -1);
        } else if (MOVE_LEFT.contains(key)) {
            move(-1, 0);
        } else if (MOVE_RIGHT.contains(key)) {
            move(1, 0);
        } else if (MOVE_HIT.contains(key)) {
            attack();
        } else if (KEY_1.contains(key)) {
            System.out.println("Using S1"); // todo add guard later on to make sure s1 is actually available etc
        } else if (KEY_2.contains(key)) {
            System.out.println("Using s2");
        } else if (KEY_3.contains(key)) {
            System.out.println("Using s3");
        } else {
            throw new RuntimeException("The following invalid regular key was passed: " + key);
        }
    }

    // does a modified char movement; see more in JPanelPlay
    private void charEventModifier(int key) {
        if (KEY_1.contains(key)) {
            System.out.println("Shifting into p1"); // todo add guard later on to make sure p1 exists etc.
        } else if (KEY_2.contains(key)) {
            System.out.println("Shifting into p2");
        } else if (KEY_3.contains(key)) {
            System.out.println("Shifting into p3");
        } else if (KEY_4.contains(key)) {
            System.out.println("Shifting into p4");
        } else {
            throw new RuntimeException("The following invalid modifier key was passed: " + key);
        }
    }

    // moves a player 1 square in the 4 cardinal directions.
    /*
     * variables:
     *    - moveX: the x-distance that the desired move moves the character (-1 is left, 1 is right)
     *    - moveY: the y-distance that the desired move moves the character (-1 is up, 1 is down)
     */
    private void move(int moveX, int moveY) {
        if (locationX == (volkX + moveX * -1) && locationY == (volkY + moveY * -1)) {
            placeChar(locationX + 2 * moveX, locationY + 2 * moveY);
        } else {
            int tryX = locationX + moveX;
            int tryY = locationY + moveY;
            if (tryX >= 0 && tryY >= 0 && tryX < UI.ARENA_SIZE && tryY < UI.ARENA_SIZE && !(jpp.grid[tryX * 13 + tryY].getSqrState() == 'n')) {
                placeChar(locationX + moveX, locationY + moveY);
            }
        }
        if (canHitVolk) {
            System.out.println("Volk is close enough to hit!");
            setAngle(moveX, moveY);
        } else {
            setAngle(moveX, moveY);
        }
    }

    private void setAngle(int moveX, int moveY) {

        this.charMarker.changeAngle(Math.PI / 2 * (moveX * moveX * (2 - moveX) + moveY * (1 + moveY)));
    }

    private void attack() {
        System.out.format("Attacking Volk @%d, %d from %d, %d%n", volkX, volkY, locationX, locationY); // todo edit
    }

    // Get variable values =============================================================================================
    public String getA1() {
        return this.a1;
    }

    public String getA2() {
        return this.a2;
    }

    public String getA3() {
        return this.a3;
    }

    public String getS1() {
        return this.s1N;
    }

    public String getS2() {
        return this.s2N;
    }

    public String getCC() {
        return this.cc;
    }

    public String getCT() {
        return this.ct;
    }

    public String getWT() {
        return this.wT;
    }

    public String getElem() {
        return this.elem;
    }

    public String getName() {
        return this.name;
    }

    public int getMt() {
        return this.mt;
    }

    // Other helper functions ==========================================================================================
    public void printAbilities(@NotNull Ability ability) {
        System.out.println(name + "'s ability is " + ability.toString());
    }

    public void printSkills(@NotNull Skill skill) {
        System.out.println(name + "'s skill is " + skill.toString());
    }

    public static Char copy(@NotNull Char c) {
        return new Char(c.name, c.elem, c.wT, c.s1N, c.s2N, c.a1, c.a2, c.a3, c.cc, c.ct, c.mt, c.hp, c.str, c.def, c.w);
    }

    public static void setNewMt(@NotNull Char c) {
        c.mt = getNewMt(c.name, c.w);
    }

    public static int getNewMt(@NotNull String name, @NotNull Weapon w) {
        Char targetChar = ProcessTxt.CHAR_INFO_DICTIONARY.get(name);
        int might = targetChar.mt;

        if (w.elem.equals(targetChar.elem)) might += w.mtE;
        else might += w.mtOE;

        return might;
    }

    public void setWeapon(String wepName) {
        Weapon target = ProcessTxt.WEAPONS_DICTIONARY.get(wepName);
        this.a3 = target.aN;
        this.w = target;
    }
}
