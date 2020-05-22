package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class rotateLabel extends JLabel {

    String fileLocation;
    double degreesRotate;

    public rotateLabel(String fileLocation, double degreesRotate) throws IOException {
        this.fileLocation = fileLocation;
        this.degreesRotate = degreesRotate;
        setIcon(new ImageIcon(fileLocation));
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    BufferedImage bi = ImageIO.read(new File(fileLocation));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(Math.toRadians(degreesRotate));
        g2.drawImage(bi, 0, 0, null);
    }
}
