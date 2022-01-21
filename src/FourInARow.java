import java.awt.Point;
import java.util.LinkedList;
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

    public static final int ID_PLAYER, ID_COMPUTER;
    private static final int ROWS, COLS;
    /**
     * Directional points used for checks
     */
    private static final Point[] DIRECTIONAL_POINTS;

        /**
     * Coefficient of how the computer should weigh the worth of a location to a player
     */
    private static final int COEFFICIENT_PLAYER = 1;
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



    static {
        DIRECTIONAL_POINTS = new Point[]{
                new Point(1, 0), new Point(-1, 1), new Point(0, 1), new Point(1, 1)
        };
        ROWS = COLS = 6;
        ID_PLAYER = RED;
        ID_COMPUTER = BLUE;
    }

    private final int[][] board;

    private final Random random;

    public FourInARow() {
        random = new Random();
        board = new int[ROWS][COLS];
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
            set(location, player);
        }
    }

    public int get(int location) {
        return inRange(location) ? board[location / COLS][location % COLS] : -1;
    }

    public void set(int location, int value) {
        if (inRange(location)) {
            board[location / COLS][location % COLS] = value;
        }
    }

    public boolean inRange(int location) {
        return location >= 0 && location < ROWS * COLS;
    }

    @Override
    public int getComputerMove() {
        List<Integer> bestMoves = new LinkedList<>();
        int currentEval = 0;
        for(int l = 0; l < ROWS * COLS; l++) {
            int eval = evaluateLocation(l,ID_COMPUTER) * COEFFICIENT_COMPUTER + evaluateLocation(l,ID_PLAYER) * COEFFICIENT_PLAYER;
            System.out.printf("(%d,%d), ",l,eval);
            if(currentEval < eval) {
                currentEval = eval;
                bestMoves.clear();
            }
            if(currentEval == eval) {
                bestMoves.add(l);
            }
        }

        System.out.println();

        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    @Override
    public int checkForWinner() {
        //For each location
        for (int l = 0; l < ROWS * COLS; l++) {
            Point p = locationToPoint(l);
            int val = get(p);
            if (val != EMPTY) {
                for (Point d : DIRECTIONAL_POINTS) {
                    for (int i = 0; i < 4; i++) {
                        if (get(new Point(p.x + d.x * i, p.y + d.y * i)) != val) {
                            break;
                        } else if (i == 3) {
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

    private Point locationToPoint(int location) {
        return new Point(location % COLS, location / COLS);
    }

    public int get(Point point) {
        return inRange(point) ? board[point.y][point.x] : -1;
    }

    private int pointToLocation(Point point) {
        return point.y * COLS + point.x;
    }

    public void set(Point point, int value) {
        if (inRange(point)) {
            board[point.y][point.x] = value;
        }
    }

    public boolean inRange(Point point) {
        return point.x >= 0 && point.x < COLS && point.y >= 0 && point.y < ROWS;
    }

    private int evaluateLocation(int location, int player) {
        return evaluateLocation(locationToPoint(location),player);
    }

    private int evaluateLocation(Point point, int player) {
        if(get(point) != EMPTY) {
            return -1;
        }

        int evalSum = 0;

        for(Point d : DIRECTIONAL_POINTS) {
            int distance = 0, dirEval = 0;
            for(int c = -1; c <= 1; c += 2) {
                for(int i = 1; i < 4; i++) {
                    int val = get(new Point(point.x + d.x * i * c, point.y + d.y * i * c));
                    if(val == EMPTY || val == player) {
                        distance++;
                        dirEval += val == EMPTY ? COEFFICIENT_EMPTY : COEFFICIENT_POPULATED;
                    } else {
                        break;
                    }
                }
            }
            if(dirEval >= 3 * COEFFICIENT_POPULATED + (distance - 3) * COEFFICIENT_EMPTY) {
                dirEval *= 2;
            }
            if(distance >= 3) {
                evalSum += dirEval;
            }
        }

        return evalSum;
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
