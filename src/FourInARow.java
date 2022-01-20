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

    private final Random random;

    /**
     * clear board and set current player (???)
     */
    public FourInARow() {
        clearBoard();
        random = new Random();
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
        for (Integer move : validMoves) {
            if (checkMoveResult(COLOR_PLAYER, move) != PLAYING) {
                return move;
            }
        }

        List<Integer> bestMoves = new ArrayList<>();
        int bestEval = 0;

        for (Integer move : validMoves) {
            int eval = evaluatePosition(move, COLOR_COMPUTER);
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

    public int getLocation(int location) {
        return inBounds(location) ? board[location / COLS][location % COLS] : -1;
    }

    private int evaluatePosition(int location, int player) {

        if (getLocation(location) != EMPTY) {
            return -1;
        }

        int eval = 0;

        for (int d : new int[]{1, COLS, COLS + 1}) {
            //Check the range
            int length = 0, count = 0;
            boolean goPos = true, goNeg = true;
            for (int i = 0; i < 4; i++) {
                int probePos = getLocation(location + d * i);
                int probeNeg = getLocation(location - d * i);

                if (goPos && (probePos == player || probePos == 0)) {
                    length++;
                    if (probePos == player) {
                        count++;
                    }
                } else {
                    goPos = false;
                }

                if (goNeg && (probeNeg == player || probeNeg == 0)) {
                    length++;
                    if (probeNeg == player) {
                        count++;
                    }
                } else {
                    goNeg = false;
                }
            }
            if (length >= 3) {
                eval += count;
            }
        }

        return eval;
    }

    private boolean inBounds(int location) {
        return location >= 0 && location < ROWS * COLS;
    }

    public void setLocation(int location, int value) {
        if (inBounds(location)) {
            board[location / COLS][location % COLS] = value;
        }
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
