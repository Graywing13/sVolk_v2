package util;

public class Ability {
    // refer to aities.txt for unabbreviated versions.
    String n;
    String a1;
    int a1P;
    String a1R;
    int a1RC;
    String a2;
    int a2P;
    String a2R;
    int a2RC;

    // The Defaults ====================================================================================================
    public Ability(String n, String a1, int a1P, String a1R, int a1RC, String a2, int a2P, String a2R, int a2RC) {
        this.n = n;
        this.a1 = a1;
        this.a1P = a1P;
        this.a1R = a1R;
        this.a1RC = a1RC;
        this.a2 = a2;
        this.a2P = a2P;
        this.a2R = a2R;
        this.a2RC = a2RC;
    }

    public String toString() {
        String returnString = n + ", which makes ";

        // write string for Ability 1
        if (a1.contains("-")) {
            returnString += ProcessTxt.stringSplit(a1, "-", 0) + " happen for " + ProcessTxt.stringSplit(a1, "-", 1) + " seconds at " + a1P + "% when " + a1R + " happens " + a1RC + " time(s).";
        } else {
            returnString += a1 + " happen at " + a1P + "%" + " when " + a1R + " occurs " + a1RC + " times.";
        }

        // see if there's a second Ability
        if (a2.equals("none")) {
            return returnString;
        } else {
            returnString += " It also makes ";

            // if there is a second Ability, write string for Ability 2
            if (a2.contains("-")) {
                returnString += ProcessTxt.stringSplit(a2, "-", 0) + " happen for " + ProcessTxt.stringSplit(a2, "-", 1) + " seconds at " + a2P + "% when " + a2R + " happens " + a2RC + " time(s).";
            } else {
                returnString += a2 + " happen at " + a2P + "%" + " when " + a2R + " occurs " + a2RC + " times.";
            }
        }

        return returnString;
    }

}
