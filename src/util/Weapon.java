package util;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Weapon implements Cloneable{
    // refer to weapons.txt for unabbreviated versions.
    String wN, elem, wT;
    int mtE, mtOE, hp, str;
    String aN;

    public Weapon(String wN, String elem, String wT, int mtE, int mtOE, int hp, int str, String aN) {
        this.wN = wN;
        this.elem = elem;
        this.wT = wT;
        this.mtE = mtE;
        this.mtOE = mtOE;
        this.hp = hp;
        this.str = str;
        this.aN = aN;
    }

    public static void changeWeapon(Char c) {
        Scanner getInput = new Scanner(System.in);
        ArrayList<String> availWeapons = new ArrayList<>();
        int w1Name = 0;

        System.out.println("What weapon would you like for " + c.name + "? Available options: ");
        for (Map.Entry<String, Weapon> w : ProcessTxt.WEAPONS_DICTIONARY.entrySet()) {
            if (w.getValue().wT.equals(c.wT)) {
                availWeapons.add(w.getKey());
                System.out.println("[" + availWeapons.size() + "]: " + w.getKey());
            }
        }

        int maxIndex = availWeapons.size();

        while (w1Name < 1 || w1Name > maxIndex) {
            System.out.println("Pick a weapon by entering a number between 1 and " + maxIndex);
            w1Name = getInput.nextInt();
        }

        Weapon target = ProcessTxt.WEAPONS_DICTIONARY.get(availWeapons.get(w1Name - 1));
        c.a3 = target.aN;
        c.w = target;
        Char.updateMt(c);
    }
}
