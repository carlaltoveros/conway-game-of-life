package conway;

import conway.logic.GameOfLife;
import conway.helper.Cell;
import conway.helper.FileToCellConverter;

import java.io.IOException;
import java.util.Set;

/**
 *  TODO: GameOfLifeFrame.java
 *          GUI work
 *          learn Swing lol
 *          :(
 */
public class GameOfLifeApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Initializing Mini Universe");

        int iterations = 100;
        String iterationsStr = System.getProperty("iterations");
        try {
            iterations = Integer.parseInt(System.getProperty("iterations", "100"));
        } catch (NumberFormatException e) {
            System.out.printf("Invalid iteration input: %s, using default value of 100%n", iterationsStr);
        }

        String fileName = System.getProperty("fileName", "cells.txt");
        FileToCellConverter fileToCellConverter = new FileToCellConverter(fileName);

        Set<Cell> currentAliveCells = fileToCellConverter.getConvertedCells();

        GameOfLife gameOfLife = new GameOfLife(currentAliveCells);
        gameOfLife.simulate(iterations);

    }
}
