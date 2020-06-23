package util;

import ui.UI;

import javax.swing.*;
import java.util.ArrayList;

public class Enemy {

    JLabel volkMarker;
    private int volkX;
    private int volkY;
    private int pixelX;
    private int pixelY;

    private int initHP = 100;
    private int currentHP = initHP;

    private ArrayList<RotateLabel> markerOverlays = new ArrayList<RotateLabel>(){};

    // constants
    private String volkElem = "Wind";

    private UI ui;

    public Enemy(UI ui) {
        this.ui = ui;
    }

    // initializes Volk's marker, calls placeVolk for placement.
    public JLabel setVolkMarker(int x, int y) {
        volkMarker = new RotateLabel(UI.VOLK_MARKER_LOCATION, UI.VOLK_DIRXN_INIT);
        placeVolk(x, y);
        return volkMarker;
    }

    // puts the character at the specified spot
    private void placeVolk(int x, int y) {
        volkX = x;
        volkY = y;
        pixelX = UI.GRID_X + x * UI.GRID_SQUARE_DISTANCE;
        pixelY = UI.GRID_Y + y * UI.GRID_SQUARE_DISTANCE;
        checkDistFromPlayers();
        overlayVolkMarkers();
        volkMarker.setBounds(pixelX, pixelY, UI.GRID_SQUARE_SIDE_LENGTH, UI.GRID_SQUARE_SIDE_LENGTH);
    }

    // puts other images on top of the volk marker
    public void overlayVolkMarkers() {
        for (RotateLabel targetImg : markerOverlays) {
            targetImg.setBounds(pixelX - 2, pixelY - 2, UI.GRID_SQUARE_DISTANCE, UI.GRID_SQUARE_DISTANCE);
        }
    }

    public void addOverlay(RotateLabel rl) {
        if (!markerOverlays.contains(rl)) {
            markerOverlays.add(rl);
            overlayVolkMarkers();
            rl.setVisible(true);
        }
    }

    public void removeOverlay(RotateLabel rl) {
        rl.setVisible(false);
        markerOverlays.remove(rl);
        markerOverlays.remove(rl);
    }

    private void checkDistFromPlayers() {
        // todo make sure that after volk moves, the character is still in range.
    }

    // todo if i add a swing timer, this should tick like twice per second
    public void takeDamage(int dmg) {
        currentHP -= dmg;
        System.out.format("(Enemy) HP left: %d / %d%n", currentHP, initHP); // todo remove
        ui.volkHPBar.changeBarWidth((double) currentHP / initHP);
        if (currentHP <= 0) {
            ui.gameOver();
        }
    }

    // Return Values ===================================================================================================
    public int getVolkX() {
        return volkX;
    }

    public int getVolkY() {
        return volkY;
    }

    public int getDamageRes() {
        return 0; // todo
    }

    public String getElem() {
        return volkElem;
    }

    public double getDef() {
        return 1; // todo
    }

    public double calcDefChange() {
        return 1; // todo
    }

    public int getHP() {
        return currentHP;
    }
}
