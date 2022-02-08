package edu.quinnipiac.ser210.fourinarow.game;

/**
 * IGame interface.
 *
 * @author relkharboutly
 * @author Thomas Kwashnak
 * @date 1/12/2022
 */
public interface IGame {

    // Name-constants to represent the seeds and cell contents
    int EMPTY = 0;
    int BLUE = 1;
    int RED = 2;

    // Name-constants to represent the various states of the game
    int PLAYING = 0;
    int TIE = 1;
    int RED_WON = 2;
    int BLUE_WON = 3;

    /**
     * clear the board of all discs by setting all spots to EMPTY
     */
    void clearBoard();

    /**
     * Sets the given player at the given location on the game board.
     * The location must be available, or the board will not be changed
     *
     * @param player   - HUMAN_PLAYER or COMPUTER_PLAYER
     * @param location - The location (0-35) to place the move
     */
    void setMove(int player, int location);

    /**
     * Returns the best move for the computer to make. You must call setMove()
     * to actually make the computer move to the location
     *
     * @return the best move for the computer to make (0-35)
     */
    int getComputerMove();

    /**
     * Check for a winner and return a status value indicating who has won.
     *
     * @return PLAYING if still playing, TIE if its a tie, BLUE_WON if X won, or RED_WON if 0 won
     */
    int checkForWinner();

    /**
     * Gets the color indicated at the provided location
     * @param location Location to get the value of
     * @return Value of the location
     */
    int get(int location);


}
