package edu.quinnipiac.ser210.fourinarow.game;

import android.graphics.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * TicTacToe class implements the interface
 *
 * I have beaten the computer before, so it is not perfect
 *
 * @author relkharboutly
 * @author Thomas Kwashnak
 * @date 2/12/2022
 */
public class FourInARow implements IGame {

    /**
     * Color IDs indicating which player gets which color (I wish the assignment was more specific)
     */
    public static final int ID_PLAYER, ID_COMPUTER;
    private static final int ROWS, COLS, LINE_LENGTH;
    /**
     * Directional points used for checks
     */
    private static final Point[] DIRECTIONAL_POINTS = new Point[]{
            new Point(1, 0), new Point(-1, 1), new Point(0, 1), new Point(1, 1)
    };

    static {
        ROWS = COLS = 6;
        LINE_LENGTH = 4;
        ID_PLAYER = RED;
        ID_COMPUTER = BLUE;
    }

    private final int[][] board;
    private final Random random;

    public FourInARow() {
        random = new Random();
        board = new int[ROWS][COLS];
    }

    /**
     * Converts a given location to a point object
     *
     * @param location Location, a number between 0 and ROWS * COLS
     *
     * @return The location represented as a point with the matrix coordinates. Returns null if the location is out of bounds
     */
    private static Point locationToPoint(int location) {
        return inRange(location) ? new Point(location % COLS, location / COLS) : null;
    }

    @Override
    public void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = EMPTY;
            }
        }
    }

    @Override
    public void setMove(int player, int location) {
        if (get(location) == EMPTY) {
            board[location / COLS][location % COLS] = player;
        }
    }

    /**
     * Returns the value on the board at the given location
     *
     * @param location The location value. The top left of the board is location 0, and the location counts up going across the board, and further
     *                 down each row. For example, for a 6x6 board, the top left corner is 0, and the bottom right corner is 35.
     *
     * @return The value found at the specified location. Returns -1 if the location is not within the bounds of the board
     */
    public int get(int location) {
        return inRange(location) ? board[location / COLS][location % COLS] : -1;
    }

    /**
     * Returns whether a given location is within the bounds of the board
     *
     * @param location The location value. The top left of the board is location 0, and the location counts up going across the board, and further
     *                 down each row. For example, for a 6x6 board, the top left corner is 0, and the bottom right corner is 35.
     *
     * @return True if the location is within the bounds of the board. False if the location is outside the bounds of the board
     */
    public static boolean inRange(int location) {
        return location >= 0 && location < ROWS * COLS;
    }

    @Override
    public int getComputerMove() {
        return getComputerMove(ID_COMPUTER, ID_PLAYER);
    }

    @Override
    public int checkForWinner() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                final int val = get(col, row);
                if (val != EMPTY && val != -1) {
                    for (Point d : DIRECTIONAL_POINTS) {
                        for (int i = 1; i < LINE_LENGTH; i++) {
                            if (get(col + d.x * i, row + d.y * i) != val) {
                                break;
                            } else if (i == LINE_LENGTH - 1) {
                                return val == RED ? RED_WON : BLUE_WON;
                            }
                        }
                    }
                }
            }
        }

        for (int l = 0; l < ROWS * COLS; l++) {
            if (get(l) == EMPTY) {
                return PLAYING;
            }
        }

        return TIE;
    }

    /**
     * Returns the value on the board at the given location
     *
     * @param col Column of the point to get
     * @param row Row of the point to get
     *
     * @return Value of the board at the given coordinates. Returns -1 if the point is out of bounds
     */
    public int get(int col, int row) {
        return inRange(col, row) ? board[row][col] : -1;
    }

    /**
     * Checks if a given location is within the bounds of the board
     *
     * @param col The column, or the x value, of the location, counting from left to right
     * @param row The row, or the y value, of the location, counting from top to bottom
     *
     * @return True if the location is within the bounds of the board. False if the location is outside the bounds of the board
     */
    public static boolean inRange(int col, int row) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    /**
     * Calculates the computer's move given the id of the computer and the id of the player
     *
     * @param idComputer token ID of the computer's pieces
     * @param idOpponent token ID of the player's pieces
     *
     * @return Computer Evaluation on the best move to make
     */
    public int getComputerMove(int idComputer, int idOpponent) {
        final List<Integer> bestMoves = new LinkedList<>();
        int currentEval = 0;
        for (int l = 0; l < ROWS * COLS; l++) {
            final int computer_eval = evaluateLocation(l, idComputer, AI.STREAK_COMPUTER) * AI.WEIGHT_COMPUTER_EVAL;
            final int opponent_eval = evaluateLocation(l, idOpponent, AI.STREAK_OPPONENT) * AI.WEIGHT_OPPONENT_EVAL;
            final int eval = computer_eval + opponent_eval;

            if (currentEval < eval) {
                currentEval = eval;
                bestMoves.clear();
            }

            if (currentEval == eval) {
                bestMoves.add(l);
            }
        }

        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    /**
     * Evaluates the given point by the worth of having a piece there in regards to the ability to make a 4-in-a-row
     *
     * @param location         Location (between 0 and 35) of the object to evaluate
     * @param idPlayer         The player to evaluate the location for
     * @param streakMultiplier The coefficient to multiply the resulting eval function by for every item in the line
     *
     * @return Evaluation of the given location for the given player
     */
    private int evaluateLocation(int location, int idPlayer, int streakMultiplier) {
        final Point point = locationToPoint(location);
        if (point == null || get(point) != EMPTY) {
            return -1;
        }

        int evalSum = 0;

        for (Point d : DIRECTIONAL_POINTS) {
            int empty = 0, populated = 0;
            for (int c = -1; c <= 1; c += 2) {
                for (int i = 1; i < LINE_LENGTH; i++) {
                    final int val = get(point.x + d.x * i * c, point.y + d.y * i * c);
                    if (val == EMPTY) {
                        empty++;
                    } else if (val == idPlayer) {
                        populated++;
                    } else {
                        break;
                    }
                }
            }
            if (empty + populated >= LINE_LENGTH - 1) {
                evalSum += (empty * AI.WEIGHT_EMPTY + populated * AI.WEIGHT_POPULATED) * Math.pow(streakMultiplier, populated);
            }
        }

        return evalSum;
    }

    /**
     * Checks if the point is within the range, and returns the value at that location
     *
     * @param point Point containing coordinates to lookup within the board
     *
     * @return Value on the board at the given coordinates. Returns -1 if point is out of bounds
     */
    public int get(Point point) {
        return get(point.x, point.y);
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
                for (int col = 0; col < COLS; col++) {
                    System.out.print("---");
                    if (col != COLS - 1) {
                        System.out.print("-");
                    }
                }
                System.out.println();
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

    /**
     * A collection of variables / configurations used within the AI, allowing for customizing of the AI's behavior
     *
     * @author Thomas Kwashnak
     */
    private static class AI {

        /**
         * Coefficient of how the computer should weigh the worth of a location to an opponent. Higher values in relation to WEIGHT_COMPUTER_EVAL will
         * cause the Computer to focus more on obstructing the opponent's strategies than trying to win itself
         */
        private static final int WEIGHT_OPPONENT_EVAL = 4;
        /**
         * Coefficient of how the computer should weigh the worth of a location to itself. Higher values in relation to WEIGHT_OPPONENT_EVAL will
         * cause
         * the computer to focus more on winning than obstructing the player strategies
         */
        private static final int WEIGHT_COMPUTER_EVAL = 3;
        /**
         * Coefficient of how an evaluation should weigh the worth of a populated space near it (in a valid line).
         */
        private static final int WEIGHT_POPULATED = 5;
        /**
         * Coefficient of how an evaluation should weigh the worth of an empty space near it (in a valid line)
         */
        private static final int WEIGHT_EMPTY = 1;

        /**
         * How much the total evaluation of a line be multiplied by for every streak
         */
        private static final int STREAK_OPPONENT = 3;
        /**
         * How much the total evaluation of a line be multiplied by for every streak
         */
        private static final int STREAK_COMPUTER = STREAK_OPPONENT + 1;
    }
}
