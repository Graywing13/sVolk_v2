package util;

public class Skill {

    // refer to skills.txt for unabbreviated versions.
    String sN;
    int spC;
    double iFT;
    boolean cM;
    boolean eA;
    int d1P, d1H, d2P, d2H;
    String b1N;
    double b1P;
    String b2N;
    double b2P;
    String b3N;
    double b3P;

    public Skill(String sN, int spC, double iFT, boolean cM, boolean eA, int d1P, int d1H, int d2P, int d2H, String b1N, double b1P, String b2N, double b2P, String b3N, double b3P) {
        this.sN = sN;
        this.spC = spC;
        this.iFT = iFT;
        this.cM = cM;
        this.eA = eA;
        this.d1P = d1P;
        this.d1H = d1H;
        this.d2P = d2P;
        this.d2H = d2H;
        this.b1N = b1N;
        this.b1P = b1P;
        this.b2N = b2N;
        this.b2P = b2P;
        this.b3N = b3N;
        this.b3P = b3P;
    }

    public String toString() {
        String returnString = sN + ": this skill costs " + spC + " SP and has an iFrame time of " + iFT + ", during which the character can";
        if (!cM) {
            returnString += "not";
        }
        returnString += " move. This skill is ";
        if (!eA) {
            returnString += "not ";
        }
        returnString += "affected by energize. ";
        if (d1P != 0) {
            returnString += "\n\tIt does " + d1H + " hit(s) at " + d1P + "% damage per hit";
            if (d2P != 0) {
                returnString += ", then " + d2H + " hit(s) at " + d2P + "% damage per hit";
            }
            returnString += ".";
        }
        if (!b1N.equals("none")) {
            returnString += "\n\tIt does the boost " + b1N + " at " + b1P + "%";
            if (!b2N.equals("none")) {
                returnString += "and the boost " + b2N + " at " + b2P + "%";
                if (!b3N.equals("none")) {
                    returnString += "and the boost " + b2N + " at " + b2P + "%";
                }
            }
            returnString += ".";
        }
        return returnString;
    }
}
