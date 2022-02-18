package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

public interface GameSettings extends IDifficulty {
    String ROW_COUNT = "GameSettings:Row Count";
    String COLUMN_COUNT = "GameSettings:Column Count";
    String CONNECT_LENGTH = "GameSettings:Connect Length";


    int getRowCount();
    int getColumnCount();
    int getConnectLength();

    default void toBundle(Bundle bundle) {
        bundle.putInt(ROW_COUNT,getRowCount());
        bundle.putInt(COLUMN_COUNT,getColumnCount());
        bundle.putInt(CONNECT_LENGTH,getConnectLength());

    }
}
