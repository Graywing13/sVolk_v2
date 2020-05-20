package util;

import org.jetbrains.annotations.NotNull;
import ui.UI;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;

public class Char {

    // movement directions
    private static final HashSet<Integer> MOVE_DOWN = new HashSet<>(Arrays.asList(83, 40));
    private static final HashSet<Integer> MOVE_UP = new HashSet<>(Arrays.asList(87, 38));
    private static final HashSet<Integer> MOVE_LEFT = new HashSet<>(Arrays.asList(65, 37));
    private static final HashSet<Integer> MOVE_RIGHT = new HashSet<>(Arrays.asList(68, 39));
    private static final HashSet<Integer> MOVE_HIT = new HashSet<>(Arrays.asList(70, 17, 157));


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
    private JLabel charMarker;
    private int locationX;
    private int locationY;

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

    // GamePlay ========================================================================================================
    // decides on the marker Image for the character
    public void setMarker(boolean p1, boolean control) {
        p1Char = p1;
        inControl = control;
    }
    // gives the character a marker and correspondingly sets that character in control's focus
    public JLabel setCharMarker(int x, int y, JPanelPlay jpp){
        if (inControl) {
            if (p1Char) {
                charMarker = new JLabel(UI.P1_BIG_MARKER_ICON);
            } else {
                charMarker = new JLabel(UI.P2_BIG_MARKER_ICON);
            }
            jpp.changeControl(p1Char, this);
        } else {
            if (p1Char) {
                charMarker = new JLabel(UI.P1_SMALL_MARKER_ICON);
            } else {
                charMarker = new JLabel(UI.P2_SMALL_MARKER_ICON);
            }
        }
        return placeChar(x, y);
    }

    // puts the character at the specified spot
    private JLabel placeChar(int x, int y) {
        locationX = x;
        locationY = y;
        charMarker.setBounds((UI.GRID_X + x * UI.GRID_SQUARE_DISTANCE), (UI.GRID_Y + y * UI.GRID_SQUARE_DISTANCE), UI.GRID_SQUARE_SIDE_LENGTH, UI.GRID_SQUARE_SIDE_LENGTH);
        return charMarker;
    }

    // processes information from arrow keys and places the character at the new location
    public void moveChar(int key, boolean modifier) {
        if (!modifier) {
            moveCharRegular(key);
        } else {
            moveCharModifier(key);
        }
    }

    // does a regular char movement; see more in JPanelPlay
    private void moveCharRegular(int key) {
        if (key == 32) {
            System.out.println("Pause Pressed"); // todo edit
        } else if (MOVE_DOWN.contains(key)) {
            placeChar(locationX, locationY + 1);
        } else if (MOVE_UP.contains(key)) {
            placeChar(locationX, locationY - 1);
        } else if (MOVE_LEFT.contains(key)) {
            placeChar(locationX - 1, locationY);
        } else if (MOVE_RIGHT.contains(key)) {
            placeChar(locationX + 1, locationY);
        } else if (MOVE_HIT.contains(key)) {
            System.out.println("Attacking"); // todo edit
        } else {
            throw new RuntimeException("The following invalid key was passed: " + key);
        }
    }

    // does a modified char movement; see more in JPanelPlay
    private void moveCharModifier(int key) {
        if (MOVE_DOWN.contains(key)) {
            placeChar(locationX, locationY + 1);
        }
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
