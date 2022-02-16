package ser.quinnipiac.edu.connectn.game;

public class GameFactory {
    public static final String KEY_COLUMN_COUNT, KEY_ROW_COUNT, KEY_CONNECT_LENGTH, KEY_GAME_BOARD;

    static {
        KEY_COLUMN_COUNT = "column_count";
        KEY_ROW_COUNT = "row_count";
        KEY_CONNECT_LENGTH = "connect_length";
        KEY_GAME_BOARD = "board";
    }

    private int rows;
    private int columns;
    private int connect_length;
}
