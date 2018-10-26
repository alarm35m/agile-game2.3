package edu.depaul.se359.agilegame;

public class GameBoard {

    private static GameBoard uniqueInstance;
    int scalingFactor = 25;
    int xN;
    int yN;
    int[][] gameGrid;


    private GameBoard(int xBoardSize, int yBoardSize) {
        xN = xBoardSize;
        yN = yBoardSize;
        gameGrid = new int[xBoardSize][yBoardSize];

    }

    public static GameBoard getInstance(int xsize, int ysize) {
        if(uniqueInstance == null) {
            uniqueInstance = new GameBoard(xsize, ysize);

        }
        return uniqueInstance;
    }

    // Return generated map
    public int[][] getMap(){
        return gameGrid;
    }

    public int getMapLength() {
        return gameGrid.length;
    }

    // returns the value of the Coordinate
    public int getCoordinateValue(int x, int y) {
        return gameGrid[x][y];
    }

    public void displayMap() {
        System.out.println();
        for(int i = 0; i < gameGrid[1].length; i++) {
            for(int j = 0; j < gameGrid.length;  j++) {
                System.out.print(gameGrid[j][i] + " ");

            }
            System.out.println();
        }
    }

}
