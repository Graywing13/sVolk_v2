// Based off http://www.camick.com/java/source/RotatedIcon.java

package util;

import javax.swing.*;
import java.awt.*;

public class RotateIcon implements Icon {

    private Icon icon;
    private double radians;

    public RotateIcon(ImageIcon icon, double radians) {
        this.icon = icon;
        this.radians = radians;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();

        int cWidth = icon.getIconWidth() / 2;
        int cHeight = icon.getIconHeight() / 2;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(x, y, getIconWidth(), getIconHeight());
        g2.translate((getIconWidth() - icon.getIconWidth()) / 2, (getIconHeight() - icon.getIconHeight()) / 2);
        g2.rotate(this.radians, x + cWidth, y + cHeight);
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
