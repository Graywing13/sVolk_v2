package util;

public class Coab {

    // refer to coabs.txt for unabbreviated versions.
    String sN, cA;
    double cP;
    boolean wT;

    public Coab(String sN, String cA, double cP, boolean wT) {
        this.sN = sN;
        this.cA = cA;
        this.cP = cP;
        this.wT = wT;
    }

    public String toString() {
        return sN + ", which does " + cA + " for " + cP + " seconds. It is " + wT + " that this skill does not do an element check.";
    }
}
