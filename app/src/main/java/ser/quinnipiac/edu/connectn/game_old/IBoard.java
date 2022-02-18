package ser.quinnipiac.edu.connectn.game_old;

import android.os.Bundle;

public interface IBoard {

    String COLUMN_COUNT = "Board:Column_Count";
    String ROW_COUNT = "Board:Row_Count";
    String CONNECT_LENGTH = "Board:Connect_Length";

    int getColumnCount();

    int getRowCount();

    int getConnectLength();

    default void saveToBundle(Bundle bundle) {
        bundle.putInt(COLUMN_COUNT,getColumnCount());
        bundle.putInt(ROW_COUNT,getRowCount());
        bundle.putInt(CONNECT_LENGTH,getConnectLength());
    }
}
