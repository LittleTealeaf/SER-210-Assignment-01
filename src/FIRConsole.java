import java.util.Scanner;

/**
 * Four in a row: Two-player console, non-graphics
 *
 * @author relkharboutly
 * @date 1/22/2020
 */
public class FIRConsole {

    public static Scanner in = new Scanner(System.in); // the input Scanner

    public static FourInARow FIRboard = new FourInARow();

    /** The entry main method (the program starts here) */
    public static void main(String[] args) {

        int currentState;
        String userInput;
        //game loop
        do {
            FIRboard.printBoard();
            /** TODO implement the game loop
             * 	1- accept user move
             *     2- call getComputerMove
             *     3- Check for winner
             *     4- Print game end messages in case of Win , Lose or Tie !
             * */

            userInput = in.nextLine();

            try {
                FIRboard.setMove(IGame.RED, Integer.parseInt(userInput));
                if (FIRboard.checkForWinner() == IGame.PLAYING) {
                    FIRboard.setMove(IGame.BLUE, FIRboard.getComputerMove());
                }
            } catch (Exception ignored) {
            }

            currentState = FIRboard.checkForWinner();
        } while ((currentState == IGame.PLAYING) && (!userInput.equals("q"))); // repeat if not game-over
        FIRboard.printBoard();
        switch (currentState) {
            case IGame.RED_WON -> System.out.println("YOU WON!");
            case IGame.BLUE_WON -> System.out.println("YOU LOST!");
            default -> System.out.println("TIE!");
        }
    }
}