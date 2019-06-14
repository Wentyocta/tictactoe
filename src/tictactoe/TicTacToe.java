/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author quintal-dev-1
 */
public class TicTacToe {
    // Name-constants to represent the seeds and cell contents
   public static final int EMPTY = 0;
   public static final int CROSS = 1;
   public static final int NOUGHT = 2;
 
   // Name-constants to represent the various states of the game
   public static final int PLAYING = 0;
   public static final int DRAW = 1;
   public static final int CROSS_WON = 2;
   public static final int NOUGHT_WON = 3;
 
   // The game board and the game status
   public static int ROWS = 3, COLS = 3; // number of rows and columns
//   public static int[][] board; // game board in 2D array
   public static ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();
   
    //  containing (EMPTY, CROSS, NOUGHT)
   public static int currentState;  // the current state of the game
                                    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
   public static int currentPlayer; // the current player (CROSS or NOUGHT)
   public static int currntRow, currentCol; // current seed's row and column
 
   public static Scanner in = new Scanner(System.in); // the input Scanner
 
   /** The entry main method (the program starts here) */
   public static void main(String[] args) throws IOException {
      // Initialize the game-board and current status
      Scanner reader = new Scanner(System.in);
      System.out.println("Enter board number: ");
      int row = reader.nextInt();
      ROWS = row;
      COLS = row;
      
      initGame();
      // Play the game once
      do {
         playerMove(currentPlayer); // update currentRow and currentCol
         updateGame(currentPlayer, currntRow, currentCol); // update currentState
         printBoard();
         // Print message if game-over
         if (currentState == CROSS_WON) {
            System.out.println("'X' won! Bye!");
         } else if (currentState == NOUGHT_WON) {
            System.out.println("'O' won! Bye!");
         } else if (currentState == DRAW) {
            System.out.println("It's a Draw! Bye!");
         }
         // Switch player
         currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
      } while (currentState == PLAYING); // repeat if not game-over
   }
 
   /** Initialize the game-board contents and the current states */
   public static void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         board.add(new ArrayList<Integer>());
         for (int col = 0; col < COLS; ++col) {
             board.get(row).add(col, EMPTY);
//            board.add(row, col, EMPTY);  // all cells empty
         }
      }
      currentState = PLAYING; // ready to play
      currentPlayer = CROSS;  // cross plays first
   }
 
   /** Player with the "theSeed" makes one move, with input validation.
       Update global variables "currentRow" and "currentCol". */
   public static void playerMove(int theSeed) {
      boolean validInput = false;  // for input validation
      do {
         if (theSeed == CROSS) {
            System.out.print("Player 'X', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
         } else {
            System.out.print("Player 'O', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
         }
         int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
         int col = in.nextInt() - 1;
         if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board.get(row).get(col) == EMPTY) {
            currntRow = row;
            currentCol = col;
            board.get(row).set(col, theSeed);
//            board[currntRow][currentCol] = theSeed;  // update game-board content
            validInput = true;  // input okay, exit loop
         } else {
            System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                  + ") is not valid. Try again...");
         }
      } while (!validInput);  // repeat until input is valid
   }
 
   /** Update the "currentState" after the player with "theSeed" has placed on
       (currentRow, currentCol). */
   public static void updateGame(int theSeed, int currentRow, int currentCol) {
      if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
         currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = DRAW;
      }
      // Otherwise, no change to currentState (still PLAYING).
   }
   
   /** Return true if it is a draw (no more empty cell) */
   // TODO: Shall declare draw if no player can "possibly" win
   public static boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board.get(row).get(col) == EMPTY) {
               return false;  // an empty cell found, not draw, exit
            }
         }
      }
      return true;  // no empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */
   public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
       boolean varWon = false;
       Boolean[] array = new Boolean[3];
       Arrays.fill(array, Boolean.FALSE);

//       check a row
        for(int col = currentCol-2; col < currentCol+2; col++){
            if (col >= 0 && col < COLS){
                if(board.get(currentRow).get(col) == theSeed){
                    for(int index = 0; index <3; index++){
                        if(array[index] == false){
                            array[index] = true;
                            break;
                        }
                    }
                }
                else{
                    Arrays.fill(array, Boolean.FALSE);
                    varWon = false;
                }

                if(array[0] == true && array[1] == true && array[2] == true){
                    varWon = true;
                    break;
                }
            }
        }
//        check a column;
        if(varWon == false){
            Arrays.fill(array, Boolean.FALSE);
            for(int row = currentRow-2; row < currentRow+2; row++){
                if (row >= 0 && row < ROWS){
                    if(board.get(row).get(currentCol) == theSeed){
                        for(int index = 0; index <3; index++){
                            if(array[index] == false){
                                array[index] = true;
                                break;
                            }
                        }
                    }
                    else{
                        Arrays.fill(array, Boolean.FALSE);
                        varWon = false;
                    }

                    if(array[0] == true && array[1] == true && array[2] == true){
                        varWon = true;
                        break;
                    }
                }
            }
            
        }
//        check bittom left diagonal row and column
        
        if(varWon == false){
            Arrays.fill(array, Boolean.FALSE);
            int col = currentCol;
            
            if(currentRow + 2 < ROWS ){
                for(int row = currentRow; row <= currentRow+2; row++){
                    if(col >= 0){
                        if(board.get(row).get(col) == theSeed){
                            for(int index = 0; index <3; index++){
                                if(array[index] == false){
                                    array[index] = true;
                                    break;
                                }
                            }
                            col = col - 1;
                        }
                        else{
                            Arrays.fill(array, Boolean.FALSE);
                            varWon = false;
                        }

                        if(array[0] == true && array[1] == true && array[2] == true){
                            varWon = true;
                            break;
                        }
                    }
                    else{
                        Arrays.fill(array, Boolean.FALSE);
                        varWon = false;
                    }
                }
            }
        }
//        check top left diagonal 
        if(varWon == false){
            Arrays.fill(array, Boolean.FALSE);
            int col = currentCol;
            
            if(currentRow - 2 >= 0 ){
                for(int row = currentRow; row >= currentRow-2; row--){
                    if(col >= 0){
                        if(board.get(row).get(col) == theSeed){
                            System.out.println("--> "+row+" "+col);
                            for(int index = 0; index <3; index++){
                                if(array[index] == false){
                                    array[index] = true;
                                    break;
                                }
                            }
                            col = col - 1;
                        }
                        else{
                            Arrays.fill(array, Boolean.FALSE);
                            varWon = false;
                        }

                        if(array[0] == true && array[1] == true && array[2] == true){
                            varWon = true;
                            break;
                        }
                    }
                    else{
                        Arrays.fill(array, Boolean.FALSE);
                        varWon = false;
                    }
                }
                
            }
        }
//        CHECK BOTTOM  RIGHT
        if(varWon == false){
            Arrays.fill(array, Boolean.FALSE);
            int col = currentCol;
            
            if(currentRow + 2 < ROWS ){
                for(int row = currentRow; row <= currentRow+2; row++){
                    if(col < COLS){
                        if(board.get(row).get(col) == theSeed){
                            for(int index = 0; index <3; index++){
                                if(array[index] == false){
                                    array[index] = true;
                                    break;
                                }
                            }
                            col = col + 1;

                        }
                        else{
                            Arrays.fill(array, Boolean.FALSE);
                            varWon = false;
                        }

                        if(array[0] == true && array[1] == true && array[2] == true){
                            varWon = true;
                            break;
                        }
                    }
                    else{
                        Arrays.fill(array, Boolean.FALSE);
                        varWon = false;
                    }
                }
            }
        }
        
//        CHECK top right
        if(varWon == false){
            Arrays.fill(array, Boolean.FALSE);
            int col = currentCol;
            
            if(currentRow - 2 >= 0 ){
                for(int row = currentRow; row >= currentRow-2; row--){
                    if(col < COLS){
                        if(board.get(row).get(col) == theSeed){
                            for(int index = 0; index <3; index++){
                                if(array[index] == false){
                                    array[index] = true;
                                    break;
                                }
                            }
                            col = col + 1;

                        }
                        else{
                            Arrays.fill(array, Boolean.FALSE);
                            varWon = false;
                        }

                        if(array[0] == true && array[1] == true && array[2] == true){
                            varWon = true;
                            break;
                        }
                    }
                    else{
                        Arrays.fill(array, Boolean.FALSE);
                        varWon = false;
                    }
                }
            }
        }

        return varWon;
   }
 
   /** Print the game board */
   public static void printBoard() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            printCell(board.get(row).get(col)); // print each of the cells
            if (col != COLS - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();
         if (row != ROWS - 1) {
             for(int i=0;i<(ROWS*4);i++){
              System.out.print("-"); // print horizontal partition   
             }
             System.out.println();
         }
      }
      System.out.println();
   }
 
   /** Print a cell with the specified "content" */
   public static void printCell(int content) {
      switch (content) {
         case EMPTY:  System.out.print("   "); break;
         case NOUGHT: System.out.print(" O "); break;
         case CROSS:  System.out.print(" X "); break;
      }
   }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//    }
    
}
