package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

public interface IDifficulty extends Bundleable {
    String WEIGHT_COMPUTER = "difficulty_weight_computer";
    String WEIGHT_PLAYER = "difficulty_weight_player";
    String WEIGHT_EMPTY = "difficulty_weight_empty";
    String WEIGHT_POPULATED = "difficulty_weight_populated";
    String STREAK_COMPUTER = "difficulty_streak_computer";
    String STREAK_PLAYER = "difficulty_streak_player";

    int getWeightComputer();
    int getWeightPlayer();
    int getWeightEmpty();
    int getWeightPopulated();
    int getStreakComputer();
    int getStreakPlayer();

    @Override
    default void toBundle(Bundle bundle) {
        bundle.putInt(WEIGHT_COMPUTER,getWeightComputer());
        bundle.putInt(WEIGHT_PLAYER,getWeightPlayer());
        bundle.putInt(WEIGHT_EMPTY,getWeightEmpty());
        bundle.putInt(WEIGHT_POPULATED,getWeightPopulated());
        bundle.putInt(STREAK_COMPUTER,getStreakComputer());
    }
}
