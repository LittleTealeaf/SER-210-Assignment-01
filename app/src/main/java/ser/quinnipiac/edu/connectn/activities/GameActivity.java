package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.elements.GameBoard;
import ser.quinnipiac.edu.connectn.game.Game;
import ser.quinnipiac.edu.connectn.game.GameListener;
import ser.quinnipiac.edu.connectn.game.IGame;

/**
 * Activity that displays the game, winner, and the current player's turn
 * @author Thomas Kwashnak
 */
public class GameActivity extends AppCompatActivity implements GameListener {

    private IGame game;
    private String name;
    private TextView textWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textWinner = findViewById(R.id.game_text_winner);

        if(savedInstanceState != null) {
            game = new Game(savedInstanceState);
            name = savedInstanceState.getString(MainActivity.NAME);
        } else {
            game = new Game(getIntent().getExtras());
            name = getIntent().getExtras().getString(MainActivity.NAME);
        }

        game.addListener(this);
        ((GameBoard) findViewById(R.id.game_gameboard)).setGame(game);
        findViewById(R.id.game_button_reset).setOnClickListener(this::onReset);
    }

    @Override
    public void onStateChanged(int newState, int oldState) {
        findViewById(R.id.game_button_reset).setVisibility(newState == IGame.PLAYING ? View.INVISIBLE : View.VISIBLE);
        textWinner.setVisibility(newState == IGame.PLAYING ? View.INVISIBLE : View.VISIBLE);


        if(newState != IGame.PLAYING) {
            String message = "";
            switch(newState) {
                case IGame.COMPUTER:
                    message = "Computer Won!";
                    break;
                case IGame.PLAYER:
                    message = name + " Won!";
                    break;
                case IGame.TIE:
                    message = name + " and Computer Tied!";
            }
            textWinner.setText(message);
        }
    }

    @Override
    public void onBoardChanged(int location, int value) {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        game.toBundle(outState);
    }

    public void onReset(View view) {
        game.clearBoard();
    }

}