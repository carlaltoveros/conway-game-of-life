package conway.application;

/** TODO: GameOfLife.java
 *          figure out how to simulate this with a larger / infinite board
 *            if this is the case then don't take a height and width as params and just have a giant board
 *          update the logic when the cells reach the visible boarders (corners?)
 *  TODO: GameOfLifeFrame.java
 *          GUI work
 *          learn Swing lol
 *  TODO: GameOfLifeApplication
 *          input handling, no hardcoded stuff
 */
public class GameOfLifeApplication {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initializing Mini Universe");

        // create GameOfLife obj and handle input here
        GameOfLife gameOfLife = new GameOfLife(11, 11);

        // hard coded for now, use input later
        gameOfLife.setToAlive(gameOfLife.coordinateToIndeces(1, 0));
        gameOfLife.setToAlive(gameOfLife.coordinateToIndeces(0, 1));
        gameOfLife.setToAlive(gameOfLife.coordinateToIndeces(0, -1));
        gameOfLife.setToAlive(gameOfLife.coordinateToIndeces(1, -1));
        gameOfLife.setToAlive(gameOfLife.coordinateToIndeces(-1, -1));

        gameOfLife.printGameBoard();
        gameOfLife.simulate(20, 1000);
    }
}
