package conway;

import conway.logic.GameOfLife;
import conway.helper.Cell;
import conway.helper.FileToCellConverter;

import java.io.IOException;
import java.util.Set;

public class GameOfLifeApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Initializing Mini Universe");

        int iterations = 300;
        String iterationsStr = System.getProperty("iterations");
        try {
            iterations = Integer.parseInt(System.getProperty("iterations", "300"));
        } catch (NumberFormatException e) {
            System.out.printf("Invalid iteration input: %s, using default value of 300%n", iterationsStr);
        }

        String fileName = System.getProperty("fileName", "cells.txt");
        FileToCellConverter fileToCellConverter = new FileToCellConverter(fileName);

        Set<Cell> currentAliveCells = fileToCellConverter.getConvertedCells();

        GameOfLife gameOfLife = new GameOfLife(currentAliveCells);
        System.out.println("Iteration: 0");
        gameOfLife.printCenterBoard();
        gameOfLife.simulate(iterations);

    }
}
