package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

public class GameFactory implements ISettings {

    public static final String DIFFICULTY = "GameFactory_difficulty";

    private int rowCount;
    private int columnCount;
    private int connectLength;
    private Difficulty difficulty;

    public GameFactory() {
        rowCount = columnCount = 6;
        connectLength = 4;
        difficulty = Difficulty.HARD;
    }

    public GameFactory(Bundle bundle) {
        rowCount = bundle.getInt(ROW_COUNT);
        columnCount = bundle.getInt(COLUMN_COUNT);
        connectLength = bundle.getInt(CONNECT_LENGTH);
        difficulty = Difficulty.valueOf(bundle.getString(DIFFICULTY));
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameFactory setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    public GameFactory setConnectLength(int connectLength) {
        this.connectLength = connectLength;
        return this;
    }

    public GameFactory setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        return this;
    }

    public GameFactory setRowCount(int rowCount) {
        this.rowCount = rowCount;
        return this;
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
