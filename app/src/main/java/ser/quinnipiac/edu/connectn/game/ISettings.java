package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

/**
 * @author Thomas Kwashnak
 */
public interface ISettings extends Bundleable {

    String ROW_COUNT = "ISettings:ROW_COUNT";
    String COLUMN_COUNT = "ISettings:COLUMN_COUNT";
    String CONNECT_LENGTH = "ISettings:CONNECT_LENGTH";

    /**
     * @return Number of rows the game has
     */
    int getRowCount();

    /**
     * @return Number of columns the game has
     */
    int getColumnCount();

    /**
     * @return How many pieces a player needs to get in a row to win
     */
    int getConnectLength();

    @Override
    default void toBundle(Bundle bundle) {
        bundle.putInt(ROW_COUNT, getRowCount());
        bundle.putInt(COLUMN_COUNT, getColumnCount());
        bundle.putInt(CONNECT_LENGTH, getConnectLength());
    }
}
