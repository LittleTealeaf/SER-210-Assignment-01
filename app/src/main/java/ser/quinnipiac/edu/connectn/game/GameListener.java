package ser.quinnipiac.edu.connectn.game;

/**
 * @author Thomas Kwashnak
 */
public interface GameListener {

    /**
     * Indicates that the game state has been changed
     * @param newState The new game state
     * @param oldState The original game state
     */
    void onStateChanged(int newState, int oldState);

    /**
     * Indicates that the board has been changed at a given location
     * @param location The location that has been modified
     * @param value The new value at the given location
     */
    void onBoardChanged(int location, int value);
}
