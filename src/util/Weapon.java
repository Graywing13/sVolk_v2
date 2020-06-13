package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Weapon implements Cloneable{
    // refer to weapons.txt for unabbreviated versions.
    String wN, elem, wT;
    int mtE, mtOE, hp, str;
    String aN;

    // notes: the weapon range values are 1 less than that weapon's most prevelant range listed in https://dragalialost.gamepedia.com/Combat_Mechanics
    public final static List<String> WEAPON_TYPES = Arrays.asList("Axe", "Blade", "Bow", "Dagger", "Lance", "Staff", "Sword", "Wand");
    public final static List<Double> WEAPON_RANGE = Arrays.asList(1.5, 1.0, 4.5, 1.5, 2.0, 5.0, 1.0, 5.5); // todo think of a better way to store these values.

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

    // gets a list of weapons that a character can select from in the init page.
    public static String[] getAvailWeapons(String wT) throws RuntimeException {
        if (!WEAPON_TYPES.contains(wT)) throw new RuntimeException("Unrecognized weapon type: " + wT);
        ArrayList<String> availWeapons = new ArrayList<>();
        for (Map.Entry<String, Weapon> w : ProcessTxt.WEAPONS_DICTIONARY.entrySet()) {
            if (w.getValue().wT.equals(wT)) {
                availWeapons.add(w.getKey());
            }
        }
        return availWeapons.toArray(new String[0]);
    }

    // checks a weapon's range and distance and returns whether volk can be hit from the char's location
    public boolean canHitEnemy(int charX, int charY) {
        return true;
        // todo WISH LIST FUNCTION
        /*
        * need to get volk's location
        * 
         */
    }
}
