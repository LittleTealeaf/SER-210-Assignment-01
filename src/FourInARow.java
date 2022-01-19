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

    /**
     * clear board and set current player
     */
    public FourInARow() {

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
        int row = getColumnOpenings(location);
        if(row != -1) {
            board[row][location] = player;
        }
    }

    @Override
    public int getComputerMove() {
        /*
        Computer Strategy:
        1. If the opponent is able to win, prevent it
        2. Place a piece on the location that makes the longest line

         */

        return 0;
    }

    @Override
    public int checkForWinner() {

        for(int i = 0; i < COLS; i++) {
            if(board[0][i] == EMPTY) {
                return PLAYING;
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
     * Returns the first open spot from a given column
     * @param col Column to check the height for
     * @return
     */
    private int getColumnOpenings(int col) {
        for(int i = 0; i < ROWS; i++) {
            if(board[col][i] != EMPTY) {
                return i - 1;
            }
        }
        return -1;
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
