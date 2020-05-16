package util;

import java.util.ArrayList;

public class Team {

    Char c1, c2, c3, c4;
    ArrayList<Coab> coabs;
    Char[] team;

    public Team(Char[] team) {
        this.c1 = team[0];
        this.c2 = team[1];
        this.c3 = team[2];
        this.c4 = team[3];
        this.coabs = initCoabs(team);
        this.team = team;
        printTeamInfo();
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

    // get team info and prints it to terminal.
    public void printTeamInfo() {
        System.out.println("♥ ♥ ♥ Team Info ♥ ♥ ♥");
        Char[] chars = new Char[]{this.c1, this.c2, this.c3, this.c4};
        for (Char c : chars) {
            System.out.println(c);
        }
        System.out.println("\n♦ ♦ ♦ This team's coabs:");
        for (Coab coab : this.coabs) System.out.println(coab);
    }

    public Char getC1() {
        return this.c1;
    }
    public Char getC2() {
        return this.c2;
    }
    public Char getC3() {
        return this.c3;
    }
    public Char getC4() {
        return this.c4;
    }

}
