package ser.quinnipiac.edu.connectn.game;

import android.os.Bundle;

import java.util.Collection;

/**
 * @author Thomas Kwashnak
 */
public interface IGame extends ISettings, IDifficulty, GameListener {

    int OUT_OF_BOUNDS = -1;
    int EMPTY = 0;
    int PLAYER = 1;
    int COMPUTER = 2;
    int PLAYING = 3;
    int TIE = 4;

    String BOARD = "IGame:BOARD";

    /**
     * Resets the board to a new game
     */
    void clearBoard();

    /**
     * Makes a given move for a specified player. Will not execute the move if the player is incorrect or the location is not empty
     *
     * @param player   {@link #PLAYER} or {@link #COMPUTER}
     * @param location The location to make the move at
     */
    void setMove(int player, int location);

    /**
     * @return The current best evaluated move that the computer should make. Returns the move as a location integer between 0 and Rows * Columns
     */
    int getComputerMove();

    /**
     * Calculates the current Game State of the board
     *
     * @return Value based on the current state of the game
     * <ul><li>{@link #PLAYING}: Game is still being played</li><li>{@link #PLAYER}: The player has won the game by creating a line
     * of the specified length</li><li>{@link #COMPUTER}: The computer has won the game by creating a line of the specified
     * length</li><li>{@link #TIE}: Neither player has won and there are no more moves possible</li></ul>
     */
    int getGameState();

    /**
     * Returns the value at a given location
     * @param location Locational spot to get the value of
     * @return The value currently stored at that location
     */
    int get(int location);

    /**
     * Registers a listener to the game, allowing the game to notify of any board or state changes
     * @param listener The GameListener to add. Cannot be itself
     * @return {@code true} if the value was successfully added, {@code false} otherwise
     * @see Collection#add(Object)
     */
    boolean addListener(GameListener listener);

    /**
     * Unregisters a listener from the game, preventing future notifications until the listener has been re-added to the game
     * @param listener The GameListener to remove.
     * @return {@code true} if the value has been successfully removed, {@code false} otherwise
     * @see Collection#remove(Object)
     */
    boolean removeListener(GameListener listener);

    /**
     * Returns the stored list of listeners that the game will notify of any board or state changes
     * @return Collection of registered listeners
     */
    Collection<GameListener> getListeners();

    /**
     * Compiles the board into a single array and returns that array
     * @return Board stored as a single array.
     */
    int[] getBoard();

    /**
     * Gets the streak of a certain player.
     * @param player Player to get the streak of, can be either {@link #COMPUTER} or {@link #PLAYER}
     * @return The streak value for the specified player. Returns {@code -1} if an invalid player value is passed
     */
    default int getStreak(int player) {
        switch (player) {
            case COMPUTER:
                return getStreakComputer();
            case PLAYER:
                return getStreakPlayer();
            default:
                return -1;
        }
    }

    @Override
    default void toBundle(Bundle bundle) {
        ISettings.super.toBundle(bundle);
        IDifficulty.super.toBundle(bundle);
        bundle.putIntArray(BOARD, getBoard());
    }
}
