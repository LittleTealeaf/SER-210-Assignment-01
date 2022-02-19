package ser.quinnipiac.edu.connectn.game_old_two;

import android.graphics.Point;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ser.quinnipiac.edu.connectn.game.GameListener;

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

    public Game(GameSettings gameSettings) {
        this.rowCount = gameSettings.getRowCount();
        this.columnCount = gameSettings.getColumnCount();
        this.connectLength = gameSettings.getConnectLength();
        this.weightComputer = gameSettings.getWeightComputer();
        this.weightEmpty = gameSettings.getWeightEmpty();
        this.weightPlayer = gameSettings.getWeightPlayer();
        this.streakPlayer = gameSettings.getStreakPlayer();
        this.streakComputer = gameSettings.getStreakComputer();
        this.weightPopulated = gameSettings.getWeightPopulated();

        this.board = new int[rowCount][columnCount];
        this.listeners = new LinkedList<>();
        this.random = new Random();
    }



    @Override
    public void clearBoard() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                set(row, col, EMPTY);
            }
        }
    }

    protected void set(int row, int col, int val) {
        if (inBounds(row, col)) {
            board[row][col] = val;
            onBoardChanged(row * columnCount + col, val);
        }
    }

    protected boolean inBounds(int row, int col) {
        return row >= 0 && row < rowCount && col >= 0 && col < columnCount;
    }

    @Override
    public void onStateChanged(int newState, int oldState) {
        for (GameListener listener : listeners) {
            listener.onStateChanged(newState, oldState);
        }
        previousState = newState;
    }

    @Override
    public void onBoardChanged(int location, int value) {
        for (GameListener listener : listeners) {
            listener.onBoardChanged(location, value);
        }

        int gameState = getGameState();
        if (gameState != previousState) {
            onStateChanged(gameState, previousState);
        }
    }

    public int get(int row, int col) {
        return inBounds(row, col) ? board[row][col] : NONE;
    }

    @Override
    public void setMove(int player, int location) {
        if (player == PLAYER || player == COMPUTER) {
            int row = location / columnCount, col = location % rowCount;
            if (get(col, row) == EMPTY) {
                set(col, row, player);
            }
        }
    }

    @Override
    public int getComputerMove() {
        final List<Integer> bestMoves = new LinkedList<>();
        int maxEval = 0;
        for (int l = 0; l < rowCount * columnCount; l++) {
            final int eval_computer = evaluateLocation(l, COMPUTER) * weightComputer;
            final int eval_player = evaluateLocation(l, PLAYER) * weightPlayer;
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
        return get(location/columnCount,location%rowCount);
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
        int[] board = new int[rowCount * columnCount];
        for (int i = 0; i < board.length; i++) {
            board[i] = this.board[i / columnCount][i % columnCount];
        }
        return board;
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
    public int getStreakComputer() {
        return streakComputer;
    }

    @Override
    public int getStreakPlayer() {
        return streakPlayer;
    }

    private int evaluateLocation(int location, int player) {
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
                    eval += (countEmpty + weightEmpty + countPopulated * weightPopulated) * Math.pow(getStreak(player), countPopulated);
                }
            }

            return eval;
        } else {
            return -1;
        }
    }
}
