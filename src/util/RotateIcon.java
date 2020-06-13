// Based off http://www.camick.com/java/source/RotatedIcon.java

package util;

import javax.swing.*;
import java.awt.*;

public class RotateIcon implements Icon {

    private Icon icon;
    private double radians;
    private int width;
    private int height;

    public RotateIcon(ImageIcon icon, double radians) {
        this.icon = icon;
        this.radians = radians;
        this.width = icon.getIconWidth();
        this.height = icon.getIconHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();

        int cWidth = this.width / 2;
        int cHeight = this.height / 2;

        int tMX; // translate modifier for x
        int tMY; // translate modifier for y
        if (radians < 0.1 && radians > -0.1){
            tMX = 0;
            tMY = 1;
        } else if (radians < Math.PI) {
            tMX = 0;
            tMY = -1;
        } else if (radians == Math.PI){
            tMX = 1;
            tMY = 0;
        } else {
            tMX = -2;
            tMY = 1;
        }

        g2.setClip(x, y, getIconWidth(), getIconHeight());
        g2.translate((getIconWidth() - this.width) / 2 + tMX, (getIconHeight() - this.height) / 2 + tMY);
        g2.rotate(this.radians, x + cWidth, y + cHeight);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        icon.paintIcon(c, g2, x, y);

        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        double sin = Math.abs( Math.sin( radians ) );
        double cos = Math.abs( Math.cos( radians ) );
        int width = (int)Math.floor(icon.getIconWidth() * cos + icon.getIconHeight() * sin);
        return width;
    }

    @Override
    public int getIconHeight() {
        double sin = Math.abs( Math.sin( radians ) );
        double cos = Math.abs( Math.cos( radians ) );
        int height = (int)Math.floor(icon.getIconHeight() * cos + icon.getIconWidth() * sin);
        return height;
    }
}
