package util;

import javax.swing.*;
import java.awt.*;

public class JPanelBkg extends JPanel {
    Image image;
    public JPanelBkg(Image image) {
        super();
        this.image = image;
        setBackground(Color.green);
    }
    @Override public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
