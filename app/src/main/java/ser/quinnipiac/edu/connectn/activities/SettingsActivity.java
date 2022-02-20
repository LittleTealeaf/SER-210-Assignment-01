package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.game.GameFactory;

/**
 * @author Thomas Kwashnak
 */
public class SettingsActivity extends AppCompatActivity {

    private GameFactory gameFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(savedInstanceState != null) {
            gameFactory = new GameFactory(savedInstanceState);
        } else {
            gameFactory = new GameFactory(getIntent().getExtras());
        }

        System.out.println(gameFactory.getColumnCount());
        gameFactory.setColumnCount(10);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        gameFactory.toBundle(outState);
    }

}