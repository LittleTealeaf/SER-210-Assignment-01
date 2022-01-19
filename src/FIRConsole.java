import java.util.Scanner;
/**
 * Four in a row: Two-player console, non-graphics
 * @author relkharboutly
 * @date 1/22/2020
 */
public class FIRConsole  {
                                                     
   public static Scanner in = new Scanner(System.in); // the input Scanner
 
   public static FourInARow FIRboard = new FourInARow();
  
   
   /** The entry main method (the program starts here) */
   public static void main(String[] args) {
      
	   int currentState = FourInARow.PLAYING;
	   String userInput ="";
	   //game loop
	   do {
		   FIRboard.printBoard();
         /** TODO implement the game loop 
          * 	1- accept user move
          *     2- call getComputerMove
          *     3- Check for winner
          *     4- Print game end messages in case of Win , Lose or Tie !
          * */
         
      } while ((currentState == IGame.PLAYING) && (!userInput.equals("q"))); // repeat if not game-over
   }
 
     
}