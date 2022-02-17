package ser.quinnipiac.edu.connectn.game;

/**
 * @author Thomas Kwashnak
 */
public interface GameListener {
    void onStateChanged(int newState, int oldState);
    void onBoardChanged(int location, int value);
}
