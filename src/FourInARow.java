import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Coefficient of how the computer should weigh the worth of a location to a player
     */
    private static final int COEFFICIENT_PLAYER = 2;
    /**
     * Coefficient of how the computer should weigh the worth of a location to itself
     */
    private static final int COEFFICIENT_COMPUTER = 1;
    /**
     * Coefficient of how an evaluation should weigh the worth of a populated space near it (in a valid line)
     */
    private static final int COEFFICIENT_POPULATED = 3;
    /**
     * Coefficient of how an evaluation should weigh the worth of an empty space near it (in a valid line)
     */
    private static final int COEFFICIENT_EMPTY = 1;

    private final Random random;

    /**
     * clear board and set current player (???)
     */
    public FourInARow() {
        clearBoard();
        random = new Random();
    }

    /**
     * Clears the board
     */
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
        List<Integer> bestMoves = new ArrayList<>();
        int bestEval = 0;
        for (int move = 0; move < ROWS * COLS; move++) {
            int eval = evaluatePosition(move, COLOR_COMPUTER) * COEFFICIENT_COMPUTER + evaluatePosition(move, COLOR_PLAYER) * COEFFICIENT_PLAYER;
            if (eval > bestEval) {
                bestEval = eval;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (eval == bestEval) {
                bestMoves.add(move);
            }
        }
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    /**
     * Evaluates how good a position is based on how close it is to making a four-in-a-row
     *
     * @param location The location to evaluate, between 0-35
     * @param player   The player to evaluate the location based on
     *
     * @return The locations' evaluation. Higher numbers means the spot is a better move for the specified player, and lower numbers means it is a
     * worse move for the player
     */
    private int evaluatePosition(int location, int player) {
        int y = location / COLS, x = location % COLS;

        if (getLocation(y, x) != EMPTY) {
            return -1;
        }

        int eval = 0;

        for (int[] d : new int[][]{{0, 1}, {1, -1}, {1, 0}, {1, 1}}) {
            int dy = d[0], dx = d[1];
            int dirDistance = 0;
            int dirEval = 0;
            for (int c = -1; c <= 1; c += 2) {
                for (int i = 1; i < 4; i++) {
                    int ty = y + dy * i * c, tx = x + dx * i * c;
                    int tVal = getLocation(ty, tx);

                    if (tVal == EMPTY || tVal == player) {
                        dirDistance++;
                        dirEval += tVal == player ? COEFFICIENT_POPULATED : COEFFICIENT_EMPTY;
                    } else {
                        break;
                    }
                }
            }
            if (dirDistance >= 3) {
                eval += dirEval;
            }
        }
        return eval;
    }

    /**
     * Returns the value at the provided location if it is valid
     *
     * @param location A location number between 0-35
     *
     * @return The value at that given location. Returns -1 if location is not in the correct bounds.
     */
    public int getLocation(int location) {
        return getLocation(location / COLS, location % COLS);
    }

    @Override
    public int checkForWinner() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int value = getLocation(row, col);
                if (value != EMPTY) {
                    for (int[] d : new int[][]{{0, 1}, {1, -1}, {1, 0}, {1, 1}}) {
                        for (int i = 0; i < 4; i++) {
                            if (getLocation(row + d[0] * i, col + d[1] * i) != value) {
                                break;
                            } else if (i == 3) {
                                return value == BLUE ? BLUE_WON : RED_WON;
                            }
                        }
                    }
                }
            }
        }

        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (board[row][col] == EMPTY) {
                    return PLAYING;
                }
            }
        }
        return TIE;
    }

    /**
     * Whether or not a give location is within the specified bounds
     *
     * @param location The location number in question
     *
     * @return True if the location is within the proper bounds, False if it is not in the bounds
     */
    public boolean inBounds(int location) {
        return location >= 0 && location < ROWS * COLS;
    }

    public int getLocation(int y, int x) {
        return inBounds(y, x) ? board[y][x] : -1;
    }

    public void setLocation(int location, int value) {
        if (inBounds(location)) {
            board[location / COLS][location % COLS] = value;
        }
    }

    public boolean inBounds(int y, int x) {
        return y >= 0 && y < ROWS && x >= 0 && x < COLS;
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
                System.out.println("-----------------------"); // print horizontal partition
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
