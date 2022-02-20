package ser.quinnipiac.edu.connectn.game;

import android.graphics.Point;
import android.os.Bundle;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * TODO: FIX THIS BECAUSE IT'S MESSING UP SOMEHOW
 */
public class Game implements IGame {

    private static final Point[] DIRECTIONS;

    static {
        DIRECTIONS = new Point[]{
                new Point(1, 0), new Point(-1, 1), new Point(0, 1), new Point(1, 1)
        };
    }

    private final int[][] board;
    private final int rowCount;
    private final int columnCount;
    private final int connectLength;
    private final int weightComputer;
    private final int weightPlayer;
    private final int weightEmpty;
    private final int weightPopulated;
    private final int streakPlayer;
    private final int streakComputer;
    private final Collection<GameListener> listeners;
    private final Random random;
    private int previousState;

    public Game(Bundle bundle) {
        rowCount = bundle.getInt(ROW_COUNT);
        columnCount = bundle.getInt(COLUMN_COUNT);
        connectLength = bundle.getInt(CONNECT_LENGTH);
        weightComputer = bundle.getInt(WEIGHT_COMPUTER);
        weightPlayer = bundle.getInt(WEIGHT_PLAYER);
        weightEmpty = bundle.getInt(WEIGHT_EMPTY);
        weightPopulated = bundle.getInt(WEIGHT_POPULATED);
        streakPlayer = bundle.getInt(STREAK_PLAYER);
        streakComputer = bundle.getInt(STREAK_COMPUTER);

        listeners = new LinkedList<>();
        random = new Random();
        board = new int[rowCount][columnCount];

        if (bundle.containsKey(BOARD)) {
            int[] bundleBoard = bundle.getIntArray(BOARD);
            for (int i = 0; i < bundleBoard.length; i++) {
                set(i, bundleBoard[i]);
            }
        }
    }

    protected void set(int location, int value) {
        set(location / columnCount, location % columnCount, value);
    }

    protected void set(int row, int col, int value) {
        board[row][col] = value;
        onBoardChanged(row * columnCount + col,value);
    }

    @Override
    public void clearBoard() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                set(row, col, EMPTY);
            }
        }
    }

    @Override
    public void setMove(int player, int location) {
        if (player == PLAYER || player == COMPUTER) {
            if (get(location) == EMPTY) {
                set(location, player);
            }
        }
    }

    @Override
    public int getComputerMove() {
        final List<Integer> bestMoves = new LinkedList<>();
        int maxEval = 0;
        for (int l = 0; l < rowCount * columnCount; l++) {
            final int eval_computer = evaluateLocation(l, COMPUTER) * getWeightComputer();
            final int eval_player = evaluateLocation(l, PLAYER) * getWeightPlayer();
            final int eval = eval_computer + eval_player;

            if (maxEval < eval) {
                maxEval = eval;
                bestMoves.clear();
                bestMoves.add(l);
            } else if (maxEval == eval) {
                bestMoves.add(l);
            }
        }

        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    protected int evaluateLocation(int location, int player) {
        int row = location / columnCount, col = location % columnCount;
        int locationVal = get(col, row);

        if (locationVal == EMPTY) {
            int eval = 0;

            for (Point d : DIRECTIONS) {
                int countEmpty = 0, countPopulated = 0;
                for (int c = -1; c <= 1; c++) {
                    for (int i = 0; i < connectLength; i++) {
                        final int val = get(col + d.x * i * c, row + d.y * i * c);
                        if (val == EMPTY) {
                            countEmpty++;
                        } else if (val == player) {
                            countPopulated++;
                        } else {
                            break;
                        }
                    }
                }
                if (countEmpty + countPopulated >= connectLength - 1) {
                    eval += (countEmpty + getWeightEmpty() + countPopulated * getWeightPopulated()) * Math.pow(getStreak(player), countPopulated);
                }
            }

            return eval;
        } else {
            return -1;
        }
    }

    @Override
    public void onStateChanged(int newState, int oldState) {
        for (GameListener listener : listeners) {
            listener.onStateChanged(newState, oldState);
        }
    }

    @Override
    public int getWeightComputer() {
        return weightComputer;
    }

    @Override
    public int getWeightPlayer() {
        return weightPlayer;
    }

    @Override
    public int getWeightEmpty() {
        return weightEmpty;
    }

    @Override
    public int getWeightPopulated() {
        return weightPopulated;
    }

    @Override
    public void onBoardChanged(int location, int value) {
        for (GameListener listener : listeners) {
            listener.onBoardChanged(location, value);
        }
        int state = getGameState();
        if (state != previousState) {
            onStateChanged(state, previousState);
            previousState = state;
        }
    }

    @Override
    public int getStreakComputer() {
        return streakComputer;
    }

    @Override
    public int getStreakPlayer() {
        return streakPlayer;
    }

    public int get(int col, int row) {
        if (col >= 0 && col < columnCount && row >= 0 && row < rowCount) {
            return board[row][col];
        } else {
            return OUT_OF_BOUNDS;
        }
    }

    @Override
    public int getGameState() {
        boolean stillPlayable = false;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                final int val = board[row][col];
                if (val != EMPTY) {
                    for (Point d : DIRECTIONS) {
                        for (int i = 1; i < connectLength; i++) {
                            if (get(col + d.x * i, row + d.y * i) != val) {
                                break;
                            } else if (i == connectLength - 1) {
                                return val;
                            }
                        }
                    }
                } else {
                    stillPlayable = true;
                }
            }
        }
        return stillPlayable ? PLAYING : TIE;
    }

    @Override
    public int get(int location) {
        if (location > -1 && location < columnCount * rowCount) {
            return board[location / columnCount][location % columnCount];
        } else {
            return OUT_OF_BOUNDS;
        }
    }

    @Override
    public boolean addListener(GameListener listener) {
        return listener != this && listeners.add(listener);
    }

    @Override
    public boolean removeListener(GameListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public Collection<GameListener> getListeners() {
        return listeners;
    }

    @Override
    public int[] getBoard() {
        int[] gameBoard = new int[rowCount * columnCount];
        for (int i = 0; i < gameBoard.length; i++) {
            gameBoard[i] = get(i);
        }
        return gameBoard;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public int getConnectLength() {
        return connectLength;
    }




}
