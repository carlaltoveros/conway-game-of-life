# Conway's Game of Life
Game of Life Java Solution

#Instructions:

 ####On IDE (I personally used IntelliJ CE)
    
    1. Clone the project to a directory

    2. Open the conway-game-of-life directory/ as a project
    
    3. Edit cells.txt and fill in coordinates as desired (it is pre-filled with example coordinates)
    
    4. Run GameOfLifeApplication.java
        - Properties you may change ( Run --> Edit Configurations --> Add VM options:
            -Dgui = true if you want to see the basic GUI of the program, default is false
            -Diterations = num of iterations, defaults to 300
            -DfileName = name of file, defaults to cells.txt
    
    5. A directory called 'output/' will be created
        - an iteration-#.txt file will be created for each iteration of the simulation
        - Each text file consists of the number of alive cells plus coordinates for each of those cells

 ####On command line
    
    1. Clone the project to a directory

    2. Edit the cells.txt and fill in coordinates as desired

    3. cd /conway-game-of-life/

    4. javac -cp artifacts/conway-game-of-life.jar src/main/java/conway/GameOfLifeApplication.java

    5. java -cp artifacts/conway-game-of-life.jar -Diterations=100 conway.GameOfLifeApplication
            -Dgui = {{true or false}} to determine whether the GUI will be used
            -Diterations = {{num}} to change number of iterations
            -DfileName = {{name}} to change file path to use

    6. A directory called 'output/' will be created
        - an iteration-#.txt file will be created for each iteration of the simulation
        - Each text file consists of the number of alive cells plus coordinates for each of those cells


#Credits

    https://rosettacode.org/wiki/Conway%27s_Game_of_Life/Java/Swing

    This was used to implement the GUI/front-end since I have limited experience with Swing.
    I changed the logic to use my GameOfLife backend and made some stylistic changes as well.