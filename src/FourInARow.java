import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TicTacToe class implements the interface
 *
 * @author relkharboutly
 * @author Thomas Kwashnak
 * @date 2/12/2022
 */
public class FourInARow implements IGame {

    // The game board and the game status
    private static final int ROWS = 6, COLS = 6; // number of rows and columns
    private final int[][] board = new int[ROWS][COLS]; // game board in 2D array

    public static int COLOR_PLAYER = RED, COLOR_COMPUTER = BLUE;


    /**
     * clear board and set current player (???)
     */
    public FourInARow() {
        clearBoard();
    }

    @Override
    public void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    @Override
    public void setMove(int player, int location) {
        if(getLocation(location) == EMPTY) {
            setLocation(location,player);
        }
    }

    @Override
    public int getComputerMove() {
        List<Integer> validMoves = new ArrayList<>();

        for(int i = 0; i < ROWS * COLS; i++) {
            if(getLocation(i) == EMPTY) {
                validMoves.add(i);
            }
        }

        //Check if any will win
        for(Integer move : validMoves) {
            if(checkMoveResult(COLOR_COMPUTER,move) != PLAYING) {
                return move;
            }
        }

        //Check if any will lose
        for(Integer move : validMoves) {
            if(checkMoveResult(COLOR_PLAYER,move) != PLAYING) {
                return move;
            }
        }

        return 0;
    }

    public int getLocation(int location) {
        return board[location / COLS][location % COLS];
    }

    public void setLocation(int location, int value) {
        board[location / COLS][location % COLS] = value;
    }

    /**
     * Temporarily makes a move, checks for winner, reverts the move, and returns the result.
     * @param player
     * @param location
     * @return
     */
    private int checkMoveResult(int player, int location) {
        int original = getLocation(location);
        setLocation(location,player);
        int result = checkForWinner();
        setLocation(location,original);
        return result;
    }

    @Override
    public int checkForWinner() {

        for(int col = 0; col < COLS; col++) {
            for(int row = 0; row < ROWS; row++) {
                if(board[row][col] == EMPTY) {
                    return PLAYING;
                }
            }
        }

        //I'm pretty sure this works
        int c = 4;
        for (int row = 0; row < ROWS - c; row++) {
            for (int col = 0; col < COLS - c; col++) {
                for (int d = 1; d <= 3; d++) {
                    int dx = d / 2, dy = d % 2;
                    for (int i = 0; i < c; i++) {
                        if (board[row + dy * i][col + dx * i] != board[row][col]) {
                            break;
                        } else if (i == c - 1) {
                            return board[row][col] == BLUE ? BLUE_WON : RED_WON;
                        }
                    }
                }
            }
        }
        return TIE;
    }

    /**
     * Print the game board
     */
    public void printBoard() {

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
     *
     * @param content either BLUE, RED or EMPTY
     */
    public void printCell(int content) {
        switch (content) {
            case EMPTY: System.out.print("   ");
                break;
            case BLUE: System.out.print(" B ");
                break;
            case RED: System.out.print(" R ");
                break;
        }
    }
}
