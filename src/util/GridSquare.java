// A SINGLE square in a grid of squares.

package util;

import ui.UI;

import javax.swing.*;
import java.awt.*;

public class GridSquare {
    /*
    Small Description
    This builds the circular grid that the characters and Volk move on.
    The 13x13 grid of GridSquares is described in 0-based coordinates, where the top left is 0, 0 and the bottom right is 12, 12.
    The GridSquare point (7, 6) has a xComponent of 7 and a yComponent of 6. It has an GridSquare[] index of 7*13+6 = 97 (in classes UI and Char).
     */

    // Components go row by row from the top tow to the bottom row.
    private static final int[] xNullComponents = new int[]{0,0,0,0,0,0,0,0,1,1,1,1,2,2,3,3,9,9,10,10,11,11,11,11,12,12,12,12,12,12,12,12};
    private static final int[] yNullComponents = new int[]{0,1,2,3,9,10,11,12,0,1,11,12,0,12,0,12,0,12,0,12,0,1,11,12,0,1,2,3,9,10,11,12};
    private static final int nullCompCount = xNullComponents.length; // should be 32 (2020.06.13)
    private static final int[] xPlayerComponents = new int[]{0,0,0,0,0,1,1,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,11,11,12,12,12,12,12};
    private static final int[] yPlayerComponents = new int[]{4,5,6,7,8,2,3,9,10,1,11,1,11,0,12,0,12,0,12,0,12,0,12,1,11,1,11,2,3,9,10,4,5,6,7,8};
    private static final int playerCompCount = xPlayerComponents.length; // should be 36 (2020.06.13)

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
    char sqrState; // one of 'b' (accessible by both player and volk), 'p' (acc. by player only), and 'n' (acc. by neither).

    public GridSquare(int x, int y) {
        this.x = x;
        this.y = y;
        this.sqrState = 'b';
    }

    public static void editSqrStates(GridSquare[] grid) {

        assert(grid.length == UI.ARENA_SIZE * UI.ARENA_SIZE);

        for (int i = 0; i < nullCompCount; i++) {
            int gridIndex = (xNullComponents[i] * UI.ARENA_SIZE + yNullComponents[i]);
            grid[gridIndex].sqrState = 'n';
        }

        for (int i = 0; i < playerCompCount; i++) {
            int gridIndex = (xPlayerComponents[i] * UI.ARENA_SIZE + yPlayerComponents[i]);
            grid[gridIndex].sqrState = 'p';
        }
    }

    public JPanel initSquare() {
        JPanel sqr = new JPanel();
        sqr.setBounds(GRID_X_GAP + x * UI.GRID_SQUARE_DISTANCE, GRID_Y_GAP + y * UI.GRID_SQUARE_DISTANCE, UI.GRID_SQUARE_DISTANCE, UI.GRID_SQUARE_DISTANCE);
        sqr.setBackground(COLOR_TRANSPARENT);
        sqr.setOpaque(true);
        // if (sqrState == 'p') sqr.setBackground(COLOR_RED_DARK); // hehe remove but this is cool todo
        return sqr;
    }

    public char getSqrState() {
        return sqrState;
    }
}
