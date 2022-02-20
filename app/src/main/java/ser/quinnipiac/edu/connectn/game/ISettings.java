package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

public interface ISettings extends Bundleable {

    String ROW_COUNT = "settings_row_count";
    String COLUMN_COUNT = "settings_column_count";
    String CONNECT_LENGTH = "settings_connect_length";

    int getRowCount();
    int getColumnCount();
    int getConnectLength();

    @Override
    default void toBundle(Bundle bundle) {
        bundle.putInt(ROW_COUNT,getRowCount());
        bundle.putInt(COLUMN_COUNT,getColumnCount());
        bundle.putInt(CONNECT_LENGTH,getConnectLength());
    }
}
