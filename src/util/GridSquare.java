package util;

import java.util.HashSet;

public class GridSquare {
    /*
    Small Description
    This describes the circular grid that the characters and Volk move on.
    The grid is described in coordinates, where the top left is 1, 1 and the bottom right is 13, 13.
    The GridSquare point (7, 6) has a xComponent of 7 and a yComponent of 6.
     */

    // Components go row by row from the top tow to the bottom row.
    private static final int[] xVoidComponents = new int[]{1,1,1,1,1,1,1,1,2,2,2,2,3,3,4,4,10,10,11,11,12,12,12,12,13,13,13,13,13,13,13,13};
    private static final int[] yVoidComponents = new int[]{1,2,3,4,10,11,12,13,1,2,12,13,1,13,1,13,1,13,1,13,1,2,12,13,1,2,3,4,10,11,12,13};
    private static final int voidCompNum = xVoidComponents.length;

    int x;
    int y;
    public GridSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static HashSet<GridSquare> initVoidSquares() {
        HashSet<GridSquare> returnSet = new HashSet<>();
        for (int i = 0; i < voidCompNum; i++) {
            returnSet.add(new GridSquare(xVoidComponents[i], yVoidComponents[i]));
        }
        return returnSet;
    }
}
