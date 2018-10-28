package edu.depaul.se359.agilegame;

import java.awt.*;

public class Player {

    final int xDimension = 8; // creating a 6x6 maps
    final int yDimension = 4;
    int xCell;
    int yCell;
    GameBoard gameBoard;

    public Player(){
        this.gameBoard = GameBoard.getInstance(xDimension, yDimension);
        xCell = 0;
        yCell = 0;
    }

    public Point getPlayerLocation(){
        return new Point(xCell, yCell);
    }
    
}