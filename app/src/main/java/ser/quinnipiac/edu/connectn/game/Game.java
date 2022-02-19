package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

public class Game implements IGame {


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


        if(bundle.containsKey(BOARD)) {
            int[] bundleBoard = bundle.getIntArray(BOARD);
            for(int i = 0; i < bundleBoard.length; i++) {
                set(i,bundleBoard[i]);
            }
        }
    }

    protected void set(int location, int value) {
        set(location/columnCount, location%columnCount, value);
    }

    protected void set(int row, int col, int value) {
        board[row][col] = value;
    }

    @Override
    public void onStateChanged(int newState, int oldState) {

    }

    @Override
    public void onBoardChanged(int location, int value) {

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

    @Override
    public void clearBoard() {

    }

    @Override
    public void setMove(int player, int location) {

    }

    @Override
    public int getComputerMove() {
        return 0;
    }

    @Override
    public int getGameState() {
        return 0;
    }

    @Override
    public int get(int location) {
        return 0;
    }

    @Override
    public boolean addListener(GameListener listener) {
        return false;
    }

    @Override
    public boolean removeListener(GameListener listener) {
        return false;
    }

    @Override
    public Collection<GameListener> getListeners() {
        return null;
    }

    @Override
    public int[] getBoard() {
        return new int[0];
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public int getConnectLength() {
        return 0;
    }
}
