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

    public static String[] getAvailWeapons(String wT) throws RuntimeException {
        if (!ProcessTxt.WEAPON_TYPES.contains(wT)) throw new RuntimeException("Unrecognized weapon type: " + wT);
        ArrayList<String> availWeapons = new ArrayList<>();
        for (Map.Entry<String, Weapon> w : ProcessTxt.WEAPONS_DICTIONARY.entrySet()) {
            if (w.getValue().wT.equals(wT)) {
                availWeapons.add(w.getKey());
            }
        }
        return availWeapons.toArray(new String[0]);
    }
}
