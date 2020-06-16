package util;

import java.util.*;

public class Weapon implements Cloneable{
    // refer to weapons.txt for unabbreviated versions.
    String wN, elem;
    WeaponType wT;
    int mtE, mtOE, hp, str;
    String aN;

    public Weapon(String wN, String elem, String wT, int mtE, int mtOE, int hp, int str, String aN) {
        this.wN = wN;
        this.elem = elem;
        this.wT = ProcessTxt.WEAPON_TYPES_DICTIONARY.get(wT);
        this.mtE = mtE;
        this.mtOE = mtOE;
        this.hp = hp;
        this.str = str;
        this.aN = aN;
    }

    // gets a list of weapons that a character can select from in the init page.
    public static String[] getAvailWeapons(String wT) throws RuntimeException {
        if (!ProcessTxt.WEAPON_TYPES_DICTIONARY.containsKey(wT)) throw new RuntimeException("Unrecognized weapon type: " + wT);
        ArrayList<String> availWeapons = new ArrayList<>();
        for (Map.Entry<String, Weapon> w : ProcessTxt.WEAPONS_DICTIONARY.entrySet()) {
            if (w.getValue().wT.getwT().equals(wT)) {
                availWeapons.add(w.getKey());
            }
        }
        return availWeapons.toArray(new String[0]);
    }

    // checks a weapon's range and distance and returns whether volk can be hit from the char's location
    public boolean canHitEnemy(int charX, int charY, Enemy enemy) {
        int xDistToVolk = charX - enemy.getLocationX();
        int yDistToVolk = charY - enemy.getLocationY();
        double wepRange = wT.getwR();
        return (wepRange * wepRange > (xDistToVolk * xDistToVolk + yDistToVolk * yDistToVolk));
    }
}
