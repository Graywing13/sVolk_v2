package util;

import javax.swing.*;

public class RotateLabel extends JLabel {

    String fileLocation;
    double radiansRotate;

    public RotateLabel(String fileLocation, double radiansRotate){
        super();
        this.fileLocation = fileLocation;
        try {
            changeAngle(radiansRotate);
            updateUI();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void changeAngle(double newRadians) {
        this.radiansRotate = newRadians;
        setIcon(new RotateIcon(new ImageIcon(fileLocation), radiansRotate));
        repaint();
    }
}
