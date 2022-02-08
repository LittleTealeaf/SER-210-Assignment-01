package edu.quinnipiac.ser210.fourinarow.activities;

import android.os.Bundle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import edu.quinnipiac.ser210.fourinarow.R;
import edu.quinnipiac.ser210.fourinarow.elements.GameBoard;
import edu.quinnipiac.ser210.fourinarow.game.DisplayGame;
import edu.quinnipiac.ser210.fourinarow.game.FourInARow;
import edu.quinnipiac.ser210.fourinarow.game.IGame;

/**
 * @author Thomas Kwashnak
 */
public class GameActivity extends AppCompatActivity {

    private IGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(savedInstanceState != null) {

        } else {
            game = new FourInARow();
        }

        GameBoard gameBoard = (GameBoard) findViewById(R.id.game_board);

        if(game instanceof DisplayGame) {
            gameBoard.setGame((DisplayGame) game);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}