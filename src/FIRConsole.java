import java.util.Scanner;

/**
 * Four in a row: Two-player console, non-graphics
 *
 * @author relkharboutly
 * @author Thomas Kwashnak
 * @date 1/22/2020
 */
public class FIRConsole {

    public static Scanner in = new Scanner(System.in); // the input Scanner

    public static FourInARow game = new FourInARow();

    /** The entry main method (the program starts here) */
    public static void main(String[] args) {

        int currentState;
        String userInput;
        //game loop
        do {
            game.printBoard();
            userInput = in.nextLine();

            try {
                game.setMove(IGame.RED,Integer.parseInt(userInput));
            } catch(NumberFormatException exception) {
                continue;
            }

            if(game.checkForWinner() == IGame.PLAYING) {
                game.setMove(IGame.BLUE,game.getComputerMove());
            }

        } while (((currentState = game.checkForWinner()) == IGame.PLAYING) && (!userInput.equals("q"))); // repeat if not game-over

        game.printBoard();

        switch (currentState) {
            case IGame.RED_WON:
                System.out.println("YOU WON!");
                break;
            case IGame.BLUE_WON:
                System.out.println("YOU LOST!");
                break;
            default:
                System.out.println("TIE!");
        }
    }
}