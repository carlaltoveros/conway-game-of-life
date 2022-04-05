package conway.helper;

import conway.logic.GameOfLife;

import java.util.Objects;

public class Cell implements Comparable<Cell> {

    private int column;
    private int row;

    public Cell(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return column == cell.column && row == cell.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public int compareTo(Cell other) {
        if (this.getColumn() > other.getColumn()) {
            return 1;
        } else if (this.getColumn() < other.getColumn()) {
            return -1;
        } else if (this.getColumn() == other.getColumn()) {
            return -1 * Integer.compare(this.getRow(), other.getRow());
        }
        return 0;
    }

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
     * @return
     */

    public static Cell fromCoordinates(int x, int y) {
        return new Cell(GameOfLife.CENTER + x, GameOfLife.CENTER - y);
    }

    /**
     * Inverse of the above function
     *   x = column - middle
     *   y = row - middle
     * @param
     * @param
     * @return
     */
    public Cell toCoordinates() {
        return new Cell(this.column - GameOfLife.CENTER, GameOfLife.CENTER - this.row);
    }
}
