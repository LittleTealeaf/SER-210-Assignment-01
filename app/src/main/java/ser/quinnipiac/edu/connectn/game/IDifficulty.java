package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

public interface IDifficulty extends Bundleable{

    String WEIGHT_COMPUTER = "IDifficulty:Weight Computer";
    String WEIGHT_PLAYER = "IDifficulty:Weight Player";
    String WEIGHT_EMPTY = "IDifficulty:Weight Empty";
    String WEIGHT_POPULATED = "IDifficulty:Weight Populated";
    String STREAK_PLAYER = "IDifficulty:Streak Player";
    String STREAK_COMPUTER = "IDifficulty:Streak Computer";

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
        bundle.putInt(STREAK_PLAYER,getStreakPlayer());
    }
}
