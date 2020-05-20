// todo modify this such that if there's only one player and they press the arrow keys, it points to the same thing and does the same thing as wasd.

package util;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

public class JPanelPlay extends JPanel implements KeyListener {

    private static final List<Integer> REGULAR_CODES_ALT = Arrays.asList(32, 37, 38, 39, 40, 55, 56, 57, 17, 157); // SPACE, ARROWS, NUMS 7-9, CTRL, CMD
    private static final List<Integer> REGULAR_CODES_SHIFT = Arrays.asList(49, 50, 51, 65, 68, 70, 83, 87); // NUMS 1-3, a, d, f, s, w
    private static final List<Integer> MODIFIER_CODES_SHIFT = Arrays.asList(17, 157, 40, 49, 50, 51, 52); // CTRL, CMD, DOWN ARROW, NUMS 1-4
    private static final List<Integer> MODIFIER_CODES_ALT= Arrays.asList(70, 83, 55, 56, 57, 48); // F, S, NUMS 7-0

    private Char[] teamChars;
    private Char p1Control;
    private Char p2Control;

    public JPanelPlay(Char[] teamChars) {
        addKeyListener(this);
        this.teamChars = teamChars;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (e.getModifiersEx() != 0 && !e.isControlDown() && !e.isMetaDown()) {
            if (MODIFIER_CODES_ALT.contains(code)) {
                if (e.isAltDown()) {
                    System.out.println("Pressed ALT + " + code);//todo remove
                } else {
                    System.out.println("Pressed " + code);//todo remove
                }
            }
            if (MODIFIER_CODES_SHIFT.contains(code)) {
                if (e.isShiftDown()) {
                    System.out.println("Pressed SHIFT + " + code);//todo remove
                } else {
                    System.out.println("Pressed " + code);//todo remove
                }
            }
        } else if (REGULAR_CODES_SHIFT.contains(code)) {
            p1Control.moveChar(code, false);
            System.out.println("Pressed " + code);//todo remove
        } else if (REGULAR_CODES_ALT.contains(code)) {
            p2Control.moveChar(code, false);
            System.out.println("Pressed " + code);//todo remove
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void changeControl(boolean p1, Char c) {
        if (p1) {
            p1Control = c;
        } else {
            p2Control = c;
        }
    }
}
