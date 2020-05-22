package util;

import ui.UI;

import javax.swing.*;
import java.awt.*;

public class GridSquare {
    /*
    Small Description
    This describes the circular grid that the characters and Volk move on.
    The 13x13 grid is described in 0-based coordinates, where the top left is 0, 0 and the bottom right is 12, 12.
    The GridSquare point (7, 6) has a xComponent of 7 and a yComponent of 6.
     */

    // Components go row by row from the top tow to the bottom row.
    private static final int[] xVoidComponents = new int[]{0,0,0,0,0,0,0,0,1,1,1,1,2,2,3,3,9,9,10,10,11,11,11,11,12,12,12,12,12,12,12,12};
    private static final int[] yVoidComponents = new int[]{0,1,2,3,9,10,11,12,0,1,11,12,0,12,0,12,0,12,0,12,0,1,11,12,0,1,2,3,9,10,11,12};
    private static final int voidCompNum = xVoidComponents.length;

    // non-component constants that maybe should go into UI
    private static final int GRID_SQUARE_GAP = 3;
    private static final int GRID_X_GAP = UI.GRID_X - GRID_SQUARE_GAP;
    private static final int GRID_Y_GAP = UI.GRID_Y - GRID_SQUARE_GAP;

    private static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);
    private static final Color COLOR_RED_DARK = new Color(102, 0, 0, 64);
    private static final Color COLOR_RED_BRIGHT = new Color(255, 0, 0, 96);
    private static final Color COLOR_PURPLE_DARK = new Color(102, 0, 102, 64);
    private static final Color COLOR_PURPLE_BRIGHT = new Color(204, 0, 204, 96);


    int x;
    int y;

    public GridSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void wipeVoidSquares(GridSquare[] grid) {

        assert(grid.length == 169);

        for (int i = 0; i < voidCompNum; i++) {
            int gridIndex = (xVoidComponents[i] * 13 + yVoidComponents[i]);
            grid[gridIndex] = null;
        }

    }

    public JPanel initSquare() {
        JPanel sqr = new JPanel();
        sqr.setBounds(GRID_X_GAP + x * UI.GRID_SQUARE_DISTANCE, GRID_Y_GAP + y * UI.GRID_SQUARE_DISTANCE, UI.GRID_SQUARE_DISTANCE, UI.GRID_SQUARE_DISTANCE);
        sqr.setBackground(COLOR_TRANSPARENT);
        sqr.setOpaque(true);
        return sqr;
    }
}
