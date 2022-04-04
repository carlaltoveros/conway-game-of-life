package conway.helper;

public class CoordinateToCellConverter {

    /**
     * Converts given coordinates to the indeces representation in the 2D array
     * for example:
     *  in a 11x11 board MIDDLE = rounded down width / 2 and height / 2
     *    (0, 0) represents the the middle of the board so      (0, 0) is column 5 row 5
     *    (5, 5) represents the top right of the board so       (5, 5) is column 10, row 0
     *    (-5, 5) represents the top left of the board so       (-5, 5) is column 0, row 0
     *    (5, -5) represents the bottom right of the board so   (5, -5) is column 10, row 10
     *    (-5, -5) represents the bottom left of the board so   (-5, -5) is column 0, row 10
     *
     *      FIRST QUADRANT (top right)
     *                                                          (1, 1) is column 6, row 4       (middle + x, middle - y)
     *                                                          (2, 3) is column 7, row 2       (middle + x, middle - y)
     *      SECOND QUADRANT (top left)
     *                                                          (-3, 4) is column 1, row 1      (middle + x, middle - y)
     *                                                          (-1, 2) is column 4, row 3      (middle + x, middle - y)
     *      THIRD QUADRANT (bottom left)
     *                                                          (-2, -5) is column 3, row 10    (middle + x, middle - y)
     *                                                          (-3, -1) is column 2, row 6     (middle + x, middle - y)
     *                                                          (-2, -2) is column 3, row 7     (middle + x, middle - y)
     *      FOURTH QUADRANT (bottom right)
     *                                                          (4, -4) is column 9, row 9      (middle + x, middle - y)
     *                                                          (3, -2) is column 8, row 7      (middle + x, middle - y)
     * @param x
     * @param y
     * @param center
     * @return
     */
    public static Cell coordinateToIndeces(int x, int y, int center) {
        return new Cell (center + x, center - y);
    }

    /**
     * Inverse of the above function
     *   x = column - middle
     *   y = row - middle
     * @param cell
     * @param center
     * @return
     */
    public static Cell indecesToCoordinates(Cell cell, int center) {
        int column = cell.getColumn();
        int row = cell.getRow();
        return new Cell(column - center, center - row);
    }
}
