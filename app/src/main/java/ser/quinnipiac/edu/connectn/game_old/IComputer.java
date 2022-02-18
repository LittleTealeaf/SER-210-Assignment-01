package ser.quinnipiac.edu.connectn.game_old;

import android.os.Bundle;

public interface IComputer {
    String COMPUTER = "Computer:Computer";
    String PLAYER = "Computer:Player";
    String POPULATED = "Computer:Populated";
    String EMPTY = "Computer:Empty";
    String STREAK_PLAYER = "Computer:Streak_Player";
    String STREAK_COMPUTER = "Computer:Streak_Computer";

    int getComputer();
    int getPlayer();
    int getEmpty();
    int getPopulated();
    int getStreakPlayer();
    int getStreakComputer();

    default int getStreak(int player) {
        switch (player) {
            case IGame.COMPUTER:
                return getStreakComputer();
            case IGame.PLAYER:
                return getStreakPlayer();
            default:
                return 0;
        }
    }

    default void saveToBundle(Bundle bundle) {
        bundle.putInt(COMPUTER,getComputer());
        bundle.putInt(PLAYER,getPlayer());
        bundle.putInt(EMPTY,getEmpty());
        bundle.putInt(POPULATED,getPopulated());
        bundle.putInt(STREAK_COMPUTER,getStreakComputer());
        bundle.putInt(STREAK_PLAYER,getStreakPlayer());
    }
}
