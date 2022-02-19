package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.game_old_two.GameFactory;

/**
 * @author Thomas Kwashnak
 */
public class SettingsActivity extends AppCompatActivity {

    private GameFactory gameFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gameFactory = new GameFactory(savedInstanceState != null ? savedInstanceState : getIntent().getExtras());

        System.out.println(gameFactory.getColumnCount());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        gameFactory.toBundle(outState);
    }

}