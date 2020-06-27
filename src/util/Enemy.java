package util;

import ui.UI;

import javax.swing.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Enemy {

    JLabel volkMarker;
    private int volkX;
    private int volkY;
    private int pixelX;
    private int pixelY;

    // constants
    private final String volkElem = "Wind";
    private final int initHP = 200;
    private final int baseOD = 37;
    private final int baseBreak = 15;
    private final int breakDuration = 3;

    private int currentHP = initHP;
    private int currentOD = 0;
    private int nextStateChangeHP; // todo remove
    public String state = "normal"; // one of "normal", "overdrive", "break", "defeat"

    private ArrayList<RotateLabel> markerOverlays = new ArrayList<RotateLabel>() {
    };

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
        switch (state) {
            case "normal":
                currentOD = Math.min(currentOD + dmg, baseOD);
                ui.volkODBar.changeBarWidth((double) currentOD / baseOD);
                System.out.format("(Enemy) Normal state. HP left: %d / %d | OD: %d / %d %n", currentHP, initHP, currentOD, baseOD); // todo remove
                if (currentOD >= baseOD) {
                    state = "overdrive";
                    ui.currentState.setText("OVERDRIVE");
                }
                break;
            case "overdrive":
                currentOD = Math.max(currentOD - dmg, 0);
                ui.volkODBar.changeBarWidth((double) currentOD / baseOD);
                System.out.format("(Enemy) Overdrive state. HP left: %d / %d | OD: %d / %d %n", currentHP, initHP, currentOD, baseOD); // todo remove
                if (currentOD <= 0) {
                    state = "break";
                    nextStateChangeHP = currentHP - 40; // todo this definitely needs changing.
                    ui.currentState.setText("BREAK!");
                }
                break;
            case "break":
                System.out.format("(Enemy) Break state. HP left: %d / %d | OD: %d / %d %n", currentHP, initHP, currentOD, baseOD); // todo remove
                if (currentHP <= nextStateChangeHP) {
                    state = "normal";
                    ui.currentState.setText("");
                }
                break;
            case "defeat":
                System.out.format("(Enemy) Defeat state. Don't think this will ever print.");
                break;
        }
        ui.volkHPBar.changeBarWidth((double) currentHP / initHP);
        if (currentHP <= 0 && !state.equals("defeat")) {
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
