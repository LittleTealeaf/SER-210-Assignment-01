package ser.quinnipiac.edu.connectn.game_old;

import android.os.Bundle;

/**
 * @author Thomas Kwashnak
 */
public class GameFactory implements IBoard {

    private int rows, columnCount, connectLength;

    public GameFactory() {
        rows = 6;
        columnCount = 6;
        connectLength = 4;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getConnectLength() {
        return connectLength;
    }

    public int getRowCount() {
        return rows;
    }

    public GameFactory setColumns(int columns) {
        this.columnCount = columns;
        return this;
    }

    public GameFactory setConnectLength(int connectLength) {
        this.connectLength = connectLength;
        return this;
    }

    public GameFactory setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public IGame create(IComputer computer) {
        return new Game(columnCount, rows, connectLength, computer);
    }

    public static GameFactory fromBundle(Bundle bundle) {
        return new GameFactory().setColumns(bundle.getInt(COLUMN_COUNT)).setRows(bundle.getInt(ROW_COUNT)).setConnectLength(bundle.getInt(CONNECT_LENGTH));
    }

    /**
     * Immediately creates an IGame instance from a bundle. Populates the board if the bundle contains the {@link Game#BOARD} key.
     * <br>
     * Creating directly from a bundle avoids needing to temporarily store the variables in a GameFactory, and instead pulls the values directly
     * from the bundle
     * @param bundle Bundle containing the values
     * @return new Game object
     */
    public static IGame createFromBundle(Bundle bundle) {
        IGame game = new Game(bundle.getInt(COLUMN_COUNT),bundle.getInt(ROW_COUNT),bundle.getInt(CONNECT_LENGTH),
                              ComputerFactory.createFromBundle(bundle));
        if(bundle.containsKey(Game.BOARD)) {
            int[] board = bundle.getIntArray(Game.BOARD);
            for(int i = 0; i <board.length; i++) {
                game.setMove(board[i],i);
            }
        }
        return game;
    }
}
