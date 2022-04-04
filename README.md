# Conway's Game of Life
Game of Life Java Solution

#Instructions:

 ####On IDE (I personally used IntelliJ CE)
    1. Clone the project to a directory

    2. Open the conway-game-of-life directory/ as a project
    
    3. Edit cells.txt and fill in coordinates as desired
    
    4. Run GameOfLifeApplication.java
        - Feel free to edit the iteration amount, I have it defaulted to 100
    
    5. A directory called 'output/' will be created
        - an iteration-#.txt file will be created for each iteration of the simulation
        - Each text file consists of the number of alive cells plus coordinates for each of those cells
    
    6. To re-run, delete the 'output/' directory and repeat from step 2

 ####On command line
    1. Clone the project to a directory

    2. Edit the cells.txt and fill in coordinates as desired

    3. cd /conway-game-of-life/src/main/java/conway

    4. javac -cp ../ GameOfLifeApplication.java

    5. java -cp ../ -Diterations={{fill this in}} -DfileName={{absolute file path}} conway/GameOfLifeApplication

    6. A directory called 'output/' will be created
        - an iteration-#.txt file will be created for each iteration of the simulation
        - Each text file consists of the number of alive cells plus coordinates for each of those cells

    7. To re-run, delete the 'output/' directory and repeat from step 2
