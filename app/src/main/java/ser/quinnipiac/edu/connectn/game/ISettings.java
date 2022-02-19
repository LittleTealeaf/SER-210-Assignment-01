package ser.quinnipiac.edu.connectn.game;

public interface ISettings {

    String ROW_COUNT = "settings_row_count";
    String COLUMN_COUNT = "settings_column_count";
    String CONNECT_LENGTH = "settings_connect_length";

    int getRowCount();
    int getColumnCount();
    int getConnectLength();
}
