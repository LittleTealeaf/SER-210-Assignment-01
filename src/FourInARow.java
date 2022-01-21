import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * TicTacToe class implements the interface
 *
 * The Computer can be beat.
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

    /**
     * Coefficient of how the computer should weigh the worth of a location to a player. Higher values in relation to COEFFICIENT_COMPUTER will
     * cause the Computer to focus more on obstructing the player strategies than trying to win itself
     */
    private static final int WEIGHT_PLAYER_EVAL = 4;
    /**
     * Coefficient of how the computer should weigh the worth of a location to itself. Higher values in relation to COEFFICIENT_PLAYER will cause
     * the computer to focus more on winning than obstructing the player strategies
     */
    private static final int WEIGHT_COMPUTER_EVAL = 3;
    /**
     * Coefficient of how an evaluation should weigh the worth of a populated space near it (in a valid line).
     */
    private static final int WEIGHT_POPULATED = 3;
    /**
     * Coefficient of how an evaluation should weigh the worth of an empty space near it (in a valid line)
     */
    private static final int WEIGHT_EMPTY = 1;

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
     * Converts a given point to a location
     *
     * @param point Point containing coordinates on the board
     *
     * @return Location representing the given coordinates. Returns -1 if coordinates are out of range
     */
    public static int pointToLocation(Point point) {
        return inRange(point) ? point.y * COLS + point.x : -1;
    }

    @Override
    public void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = EMPTY;
            }
        }
    }

    /**
     * Checks if a given point is within the range of the board
     *
     * @param point Coordinates to check
     *
     * @return True if the point can be found on the board, False if the point can not be found on the board
     */
    public static boolean inRange(Point point) {
        return point.x >= 0 && point.x < COLS && point.y >= 0 && point.y < ROWS;
    }

    /**
     * Gets the board value at a given location
     *
     * @param location Location (between 0 and ROWS * COLS) to get the value of
     *
     * @return The value at the given location. Returns -1 if the location is not within range
     */
    public int get(int location) {
        return inRange(location) ? board[location / COLS][location % COLS] : -1;
    }

    @Override
    public void setMove(int player, int location) {
        if (get(location) == EMPTY) {
            board[location / COLS][location % COLS] = player;
        }
    }

    @Override
    public int getComputerMove() {
        //Keeps a list of best moves (in the instance that there is more than one)
        List<Integer> bestMoves = new LinkedList<>();
        int currentEval = 0; //Current highest evaluation, set to 0 to ignore negative values
        for (int l = 0; l < ROWS * COLS; l++) {
            //Calculates net eval based on the evaluation function for each player and the weight
            int eval = evaluateLocation(l, ID_COMPUTER, 3) * WEIGHT_COMPUTER_EVAL + evaluateLocation(l, ID_PLAYER, 2) * WEIGHT_PLAYER_EVAL;
            //If the eval is higher than what the current list is, clear the list
            if (currentEval < eval) {
                currentEval = eval;
                bestMoves.clear();
            }
            //Add to the list if it is of equal evaluation
            if (currentEval == eval) {
                bestMoves.add(l);
            }
        }
        //Choose a random element from the list
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    @Override
    public int checkForWinner() {
        for (int l = 0; l < ROWS * COLS; l++) {
            Point p = locationToPoint(l);
            int val = get(p);
            if (val != EMPTY) {
                for (Point d : DIRECTIONAL_POINTS) {
                    for (int i = 0; i < LINE_LENGTH; i++) {
                        if (get(new Point(p.x + d.x * i, p.y + d.y * i)) != val) {
                            break;
                        } else if (i == LINE_LENGTH - 1) {
                            return val == BLUE ? BLUE_WON : RED_WON;
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
     * Evaluates the given point by the worth of having a piece there in regards to the ability to make a 4-in-a-row
     *
     * @param location           Location (between 0 and 35) of the object to evaluate
     * @param player             The player to evaluate the location for
     * @param oneMoveCoefficient The coefficient to apply to the total evaluation if a given line is one move from completion
     *
     * @return Evaluation of the given location for the given player
     */
    private int evaluateLocation(int location, int player, int oneMoveCoefficient) {
        Point point = locationToPoint(location);
        if (point == null || get(point) != EMPTY) {
            return -1;
        }

        int evalSum = 0;

        for (Point d : DIRECTIONAL_POINTS) {
            int distance = 0, dirEval = 0;
            for (int c = -1; c <= 1; c += 2) {
                for (int i = 1; i < LINE_LENGTH; i++) {
                    int val = get(new Point(point.x + d.x * i * c, point.y + d.y * i * c));
                    if (val == EMPTY || val == player) {
                        distance++;
                        dirEval += val == EMPTY ? WEIGHT_EMPTY : WEIGHT_POPULATED;
                    } else {
                        break;
                    }
                }
            }
            if (dirEval >= (LINE_LENGTH - 1) * WEIGHT_POPULATED + (distance - LINE_LENGTH + 1) * WEIGHT_EMPTY) {
                dirEval *= oneMoveCoefficient;
            }
            if (distance >= LINE_LENGTH - 1) {
                evalSum += dirEval;
            }
        }

        return evalSum;
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

    /**
     * Checks if the point is within the range, and returns the value at that location
     *
     * @param point Point containing coordinates to lookup within the board
     *
     * @return Value on the board at the given coordinates. Returns -1 if point is out of bounds
     */
    public int get(Point point) {
        return inRange(point) ? board[point.y][point.x] : -1;
    }

    /**
     * Checks if a given location can be found on the board
     *
     * @param location Location to check on the board
     *
     * @return True if the location is between 0 and ROWS * COLS, False otherwise
     */
    public static boolean inRange(int location) {
        return location >= 0 && location < ROWS * COLS;
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
}
