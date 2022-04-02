package conway.application;

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
