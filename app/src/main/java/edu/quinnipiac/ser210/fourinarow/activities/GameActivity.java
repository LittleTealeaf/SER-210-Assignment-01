package edu.quinnipiac.ser210.fourinarow.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.quinnipiac.ser210.fourinarow.R;
import edu.quinnipiac.ser210.fourinarow.elements.GameBoard;
import edu.quinnipiac.ser210.fourinarow.elements.GameListener;
import edu.quinnipiac.ser210.fourinarow.game.FourInARow;
import edu.quinnipiac.ser210.fourinarow.game.IGame;

/**
 * @author Thomas Kwashnak
 */
public class GameActivity extends AppCompatActivity implements GameListener {

    private static final String KEY_GAME_STATE;

    static {
        KEY_GAME_STATE = "GameState";
    }

    private IGame game;
    private GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameBoard = (GameBoard) findViewById(R.id.game_board);

        game = new FourInARow();

        if(savedInstanceState != null) {
            int[] boardState = savedInstanceState.getIntArray(KEY_GAME_STATE);
            for(int i = 0; i < boardState.length; i++) {
                game.setMove(boardState[i],i);
            }
        }
        gameBoard.setListener(this);
        gameBoard.setGame(game);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int[] board = new int[36];
        for(int i = 0; i < board.length; i++) {
            board[i] = game.get(i);
        }
        outState.putIntArray(KEY_GAME_STATE,board);
    }

    @Override
    public void onGameStateUpdate(int result) {

    }
}