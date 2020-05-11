package util;

import java.util.ArrayList;
import java.util.Map;

public class Team {

    Char c1, c2, c3, c4;
    ArrayList<Coab> coabs;

    public Team(String c1Name, String c2Name, String c3Name, String c4Name) {

        Char[] team = initTeamMembers(c1Name, c2Name, c3Name, c4Name);
        this.c1 = team[0];
        this.c2 = team[1];
        this.c3 = team[2];
        this.c4 = team[3];

        this.coabs = initCoabs(team);

        System.out.println("\n♦ ♦ ♦ Weapon Selection");
        initWeapons(team);

        printTeamInfo(team);
    }

    private void initWeapons(Char[] players) {
        for (Char c : players) {
            Weapon.changeWeapon(c);
        }
    }

    private ArrayList<Coab> initCoabs(Char[] team) {
        ArrayList<Coab> coA = new ArrayList<>();
        for (Char c : team) {
            Coab cc = ProcessTxt.COABS_DICTIONARY.get(c.getCC());
            Coab ct = ProcessTxt.COABS_DICTIONARY.get(c.getCT());
            if (!coA.contains(cc)) {
                coA.add(cc);
            }
            if (!coA.contains(ct)) {
                coA.add(ct);
            }
        }

        return coA;
    }

    private Char[] initTeamMembers(String c1Name, String c2Name, String c3Name, String c4Name) {
        String[] names = new String[]{c1Name, c2Name, c3Name, c4Name};
        Char[] chars = new Char[4];
        for (int i = 0; i < 4; i++) {
            System.out.println("\n♦ ♦ ♦ Initializing P" + (i+1) + " as " + names[i]);
            chars[i] = Char.copy(ProcessTxt.CHAR_INFO_DICTIONARY.get(names[i]));
            chars[i].initChar(chars[i]);
        }
        return chars;
    }

    // get team info and prints it to terminal.
    public void printTeamInfo(Char[] team) {
        System.out.println("♥ ♥ ♥ Team Info ♥ ♥ ♥");
        for (Char c : team) {
            System.out.println(c);
        }
        System.out.println("\n♦ ♦ ♦ This team's coabs:");
        for (Coab coab : this.coabs) System.out.println(coab);
    }

}
