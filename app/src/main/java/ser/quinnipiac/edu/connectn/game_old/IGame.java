package ser.quinnipiac.edu.connectn.game_old;

import java.util.Collection;

import ser.quinnipiac.edu.connectn.game.GameListener;

/**
 * @author Thomas Kwashnak
 */
public interface IGame extends GameListener, IBoard {

    int NONE = -1;
    int EMPTY = 0;
    int PLAYER = 1;
    int COMPUTER = 2;
    int PLAYING = 3;
    int TIE = 4;
    String BOARD = "board";

    void clearBoard();
    /**
     *
     * @param player {@link #PLAYER} or {@link #COMPUTER}
     * @param location
     */
    void setMove(int player, int location);

    int getComputerMove();

    int getGameState();

    int get(int location);

    boolean addListener(GameListener listener);

    boolean removeListener(GameListener listener);

    Collection<GameListener> getListeners();
}
