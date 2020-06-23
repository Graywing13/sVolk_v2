package util;

import javax.swing.*;
import java.awt.*;

public class DrawRect extends JPanel {

    private Color color;
    private int w;
    private int h;
    private int origWidth;

    private final static int BARS_VOLK_HEIGHT = 8;
    private static Color HP_BAR_VOLK_COLOR = new Color(237, 0, 0, 130);
    private final static int HP_BAR_VOLK_WIDTH = 250;
    private static Color OD_BAR_VOLK_COLOR = new Color(255, 138, 0, 143);
    private final static int OD_BAR_VOLK_WIDTH = 200;

    public DrawRect(int width, int height, Color color) {
        super();
        this.w = width;
        this.h = height;
        this.origWidth = width;
        this.color = color;
        setBackground(color);
        setOpaque(true);
        setVisible(true);
        setBounds(0, 0, w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(w, h);
        g.drawRect(0, 0, w, h);
    }

    public void changeBarWidth(double percentage) {
        w = (int) Math.floor(origWidth * percentage);
        repaint();
    }

    public static DrawRect initVolkHPBar() {
        return new DrawRect(HP_BAR_VOLK_WIDTH, BARS_VOLK_HEIGHT, HP_BAR_VOLK_COLOR);
    }

    public static DrawRect initVolkODBar() {
        return new DrawRect(OD_BAR_VOLK_WIDTH, BARS_VOLK_HEIGHT, OD_BAR_VOLK_COLOR);
    }
}
