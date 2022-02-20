package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

import java.util.Collection;

public interface IGame extends ISettings, IDifficulty, GameListener {
    int NONE = -1;
    int EMPTY = 0;
    int PLAYER = 1;
    int COMPUTER = 2;
    int PLAYING = 3;
    int TIE = 4;

    String BOARD = "IGame_board";

    void clearBoard();
    void setMove(int player, int location);
    int getComputerMove();
    int getGameState();
    int get(int location);
    boolean addListener(GameListener listener);
    boolean removeListener(GameListener listener);
    Collection<GameListener> getListeners();

    int[] getBoard();

    default int getStreak(int player) {
        switch(player) {
            case COMPUTER:
                return getStreakComputer();
            case PLAYER:
                return getStreakPlayer();
            default:
                return 0;
        }
    }

    @Override
    default void toBundle(Bundle bundle) {
        ISettings.super.toBundle(bundle);
        IDifficulty.super.toBundle(bundle);
        bundle.putIntArray(BOARD,getBoard());
    }
}
