import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * @date 2/12/2022
 */
public class FourInARow implements IGame {
		 
	   // The game board and the game status
	   private static final int ROWS = 6, COLS = 6; // number of rows and columns
	   private int[][] board = new int[ROWS][COLS]; // game board in 2D array
	  
	/**
	 * clear board and set current player   
	 */
	public FourInARow(){
		
	}
	
	@Override
	public void clearBoard() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				board[i][j] = EMPTY; //huh?
			}
		}
	}

	@Override
	public void setMove(int player, int location) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getComputerMove() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkForWinner() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	  /**
	   *  Print the game board 
	   */
	   public  void printBoard() {
		  
	      for (int row = 0; row < ROWS; ++row) {
	         for (int col = 0; col < COLS; ++col) {
	            printCell(board[row][col]); // print each of the cells
	            if (col != COLS - 1) {
	               System.out.print("|");   // print vertical partition
	            }
	         }
	         System.out.println();
	         if (row != ROWS - 1) {
	            System.out.println("-----------"); // print horizontal partition
	         }
	      }
	      System.out.println(); 
	   }
	 
	   /**
	    * Print a cell with the specified "content" 
	    * @param content either BLUE, RED or EMPTY
	    */
	   public  void printCell(int content) {
	      switch (content) {
	         case EMPTY:  System.out.print("   "); break;
	         case BLUE: System.out.print(" B "); break;
	         case RED:  System.out.print(" R "); break;
	      }
	   }

}
