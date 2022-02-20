package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.elements.GameBoard;
import ser.quinnipiac.edu.connectn.game.Game;
import ser.quinnipiac.edu.connectn.game.GameListener;
import ser.quinnipiac.edu.connectn.game.IGame;

/**
 * @author Thomas Kwashnak
 */
public class GameActivity extends AppCompatActivity implements GameListener {

    private IGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(savedInstanceState != null) {
            game = new Game(savedInstanceState);
        } else {
            game = new Game(getIntent().getExtras());
        }

        game.addListener(this);
        ((GameBoard) findViewById(R.id.game_gameboard)).setGame(game);
    }

    @Override
    public void onStateChanged(int newState, int oldState) {

    }

    @Override
    public void onBoardChanged(int location, int value) {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        game.toBundle(outState);
    }

}