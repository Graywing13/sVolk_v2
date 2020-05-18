package util;

import jdk.internal.util.xml.impl.Input;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

public class JPanelPlay extends JPanel implements KeyListener {

    private static final List<Character> registeredCharsLeft= Arrays.asList('S', 'a', 'd', 's', 'w', 'f', '1', '2', '3', '4', '!', '@', '#', '$');
    private static final List<Character> registeredCharsRight = Arrays.asList('7', '8', '9', '0');
    private static final List<Integer> registeredCodes= Arrays.asList(17, 157, 32, 37, 38, 39, 40); // CTRL/CMD key, SPACE key, and arrow keys

    public JPanelPlay() {
        addKeyListener(this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (registeredCharsLeft.contains(e.getKeyChar())) {
            System.out.println("Typed " + e.getKeyChar());
        } else if (registeredCharsRight.contains(e.getKeyChar())) {
            if ((e.getModifiersEx() != 0) && e.isAltDown()) {
                System.out.println("Typed ALT + " + e.getKeyChar());//todo remove
            } else {
                System.out.println("Typed " + e.getKeyChar());//todo remove
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (registeredCodes.contains(e.getKeyCode())) {
            if ((e.getModifiersEx() != 0) && e.isAltDown()) {
                System.out.println("Pressed ALT + " + e.getKeyCode());//todo remove
            } else {
                System.out.println("Pressed " + e.getKeyCode());//todo remove
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
