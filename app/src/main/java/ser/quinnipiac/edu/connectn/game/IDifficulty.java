package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

/**
 * @author Thomas Kwashnak
 */
public interface IDifficulty extends Bundleable {
    String WEIGHT_COMPUTER = "IDifficulty:WEIGHT_COMPUTER";
    String WEIGHT_PLAYER = "IDifficulty:WEIGHT_PLAYER";
    String WEIGHT_EMPTY = "IDifficulty:WEIGHT_EMPTY";
    String WEIGHT_POPULATED = "IDifficulty:WEIGHT_POPULATED";
    String STREAK_COMPUTER = "IDifficulty:STREAK_COMPUTER";
    String STREAK_PLAYER = "IDifficulty:STREAK_PLAYER";

    /**
     *
     * @return The amount that the computer weighs its own value of a given location
     */
    int getWeightComputer();

    /**
     *
     * @return The amount that the computer weighs the opponent's value of a given location
     */
    int getWeightPlayer();

    /**
     *
     * @return The innate value an empty spot has
     */
    int getWeightEmpty();

    /**
     *
     * @return The innate value a populated spot has
     */
    int getWeightPopulated();

    /**
     *
     * @return The multiplier to multiply an evaluation for each piece a computer has in the row
     */
    int getStreakComputer();

    /**
     *
     * @return The multiplier to multiply an evaluation for each piece a player has in the row
     */
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
