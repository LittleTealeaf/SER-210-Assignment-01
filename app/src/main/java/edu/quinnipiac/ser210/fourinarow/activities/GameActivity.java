package edu.quinnipiac.ser210.fourinarow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import edu.quinnipiac.ser210.fourinarow.R;
import edu.quinnipiac.ser210.fourinarow.elements.GameBoard;
import edu.quinnipiac.ser210.fourinarow.elements.GameListener;
import edu.quinnipiac.ser210.fourinarow.game.FourInARow;
import edu.quinnipiac.ser210.fourinarow.game.IGame;

/**
 * @author Thomas Kwashnak
 */
public class GameActivity extends AppCompatActivity implements GameListener {

    private static final String KEY_GAME_STATE, TAG;

    static {
        KEY_GAME_STATE = "GameState";
        TAG = "GameActivity";
    }

    private IGame game;
    private TextView gameState;
    private GameBoard gameBoard;
    private Map<Integer, String> gameStateMap;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameState = (TextView) findViewById(R.id.game_state);
        resetButton = (Button) findViewById(R.id.button_game_reset);
        gameBoard = (GameBoard) findViewById(R.id.game_board);

        String name = getIntent().getStringExtra(GreeterActivity.KEY_NAME);

        gameStateMap = new HashMap<Integer, String>() {{
            put(0, name + "'s Turn");
            put(1, "Tie");
            put(2, name + " Won");
            put(3, "Computer Won");
        }};

        game = new FourInARow();

        if (savedInstanceState != null) {
            int[] boardState = savedInstanceState.getIntArray(KEY_GAME_STATE);
            for (int i = 0; i < boardState.length; i++) {
                game.setMove(boardState[i], i);
            }
        }
        gameBoard.setListener(this);
        gameBoard.setGame(game);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int[] board = new int[36];
        for (int i = 0; i < board.length; i++) {
            board[i] = game.get(i);
        }
        outState.putIntArray(KEY_GAME_STATE, board);
    }

    @Override
    public void onGameStateUpdate(int result) {
        gameState.setText(gameStateMap.getOrDefault(result, ""));
        resetButton.setVisibility(result == IGame.PLAYING ? View.INVISIBLE : View.VISIBLE);
    }

    public void onClickReset(View view) {
        gameBoard.clearBoard();
    }
}