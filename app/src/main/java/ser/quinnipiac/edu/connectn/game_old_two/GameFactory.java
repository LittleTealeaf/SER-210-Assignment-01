package ser.quinnipiac.edu.connectn.game_old_two;

import android.os.Bundle;

public class GameFactory implements GameSettings {


    private int rowCount = 6;
    private int columnCount = 6;
    private int connectLength = 4;


    public GameFactory() {

    }

    public GameFactory(Bundle bundle) {
        rowCount = bundle.getInt(ROW_COUNT);
        columnCount = bundle.getInt(COLUMN_COUNT);
        connectLength = bundle.getInt(CONNECT_LENGTH);
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
        return 0;
    }

    @Override
    public int getWeightPlayer() {
        return 0;
    }

    @Override
    public int getWeightEmpty() {
        return 0;
    }

    @Override
    public int getWeightPopulated() {
        return 0;
    }

    @Override
    public int getStreakComputer() {
        return 0;
    }

    @Override
    public int getStreakPlayer() {
        return 0;
    }
}
