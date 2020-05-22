package util;

import ui.UI;

import javax.swing.*;

public class Enemy {

    JPanelPlay jpp;
    JLabel volkMarker;

    private int locationX;
    private int locationY;

    public Enemy(JPanelPlay jpp) {
        this.jpp = jpp;
    }

    // initializes Volk's marker, calls placeVolk for placement.
    public JLabel setVolkMarker(int x, int y) {
        volkMarker = new JLabel(UI.SVOLK_ICON);
        placeVolk(x, y);
        return volkMarker;
    }

    // puts the character at the specified spot
    private JLabel placeVolk(int x, int y) {
        locationX = x;
        locationY = y;
        volkMarker.setBounds((UI.GRID_X + x * UI.GRID_SQUARE_DISTANCE), (UI.GRID_Y + y * UI.GRID_SQUARE_DISTANCE), UI.GRID_SQUARE_SIDE_LENGTH, UI.GRID_SQUARE_SIDE_LENGTH);
        return volkMarker;
    }

    // Return Values ===================================================================================================
    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }
}
