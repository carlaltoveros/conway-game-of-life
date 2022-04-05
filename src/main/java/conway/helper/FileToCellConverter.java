package conway.helper;

import conway.logic.GameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileToCellConverter {

    private File file;
    private Scanner scanner;

    public FileToCellConverter(String fileName) throws FileNotFoundException {
        file = new File(fileName);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.printf("File with name: %s was not found, try again with a proper file name%n", fileName);
            throw e;
        }
    }

    /**
     * For each line in the file
     *   ignore comment lines that start with #
     *   split each line (formatted to have X (space or tabs) Y)
     *   convert the indeces from x and y values to the appropriate column and row values
     * @return
     */
    public Set<Cell> getConvertedCells() {
        Set<Cell> cells = new HashSet<>();
        String line;
        int lineNum = 1;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().strip();
            if (!line.startsWith("#") && !line.isBlank()) {
                String[] splitLine = line.split("[ \\t]+");
                try {
                    int x = Integer.parseInt(splitLine[0]);
                    int y = Integer.parseInt(splitLine[1]);
                    Cell cell = CoordinateToCellConverter.coordinateToIndeces(x, y, GameOfLife.CENTER);
                    cells.add(cell);
                } catch (NumberFormatException e) {
                    System.out.printf("Found illegal int value on line: %d, skipping...%n", lineNum);
                }
            }
            lineNum++;
        }
        scanner.close();
        return cells;
    }
}
