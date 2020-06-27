package util;

import javax.swing.*;
import java.awt.*;

public class JLabelCustom extends JLabel {

    int fontSize;

    public JLabelCustom(String s, int fontSize) {
        super();
        this.fontSize = fontSize;
        this.setFont(new Font("Courier New", Font.PLAIN, fontSize));
        this.setForeground(Color.WHITE);
        this.setText(s);
    }

    public static String formatTime(int timeInMS) {
        int sTimeLeft = timeInMS / 1000;
        return sTimeLeft / 60 + ":" + String.format("%02d", sTimeLeft % 60);
    }
}
