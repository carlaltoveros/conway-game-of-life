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

    public Set<Cell> getConvertedCells() {
        Set<Cell> cells = new HashSet<>();
        String line;
        int lineNum = 1;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().strip();
            if (!line.startsWith("#")) {
                String[] splitLine = line.split("[ \\t]+");
                try {
                    int column = Integer.parseInt(splitLine[0]);
                    int row = Integer.parseInt(splitLine[1]);
                    Cell cell = CoordinateToCellConverter.coordinateToIndeces(column, row, GameOfLife.CENTER);
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
