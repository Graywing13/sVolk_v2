// maybe this should be an enum?
// Specifies the details of weapon types

package util;

public class WeaponType {

    private String wT;
    private double wR;

    public WeaponType(String wT, double wR) {
        this.wT = wT;
        this.wR = wR;
    }

    public String getwT() {
        return wT;
    }

    public double getwR() {
        return wR;
    }
}
