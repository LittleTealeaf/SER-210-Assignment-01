package ser.quinnipiac.edu.connectn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.game_old.ComputerFactory;
import ser.quinnipiac.edu.connectn.game_old.GameFactory;

/**
 * @author Thomas Kwashnak
 */
public class SettingsActivity extends AppCompatActivity {

    private GameFactory gameFactory;
    private ComputerFactory computerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gameFactory = GameFactory.fromBundle(getIntent().getExtras());
        computerFactory = ComputerFactory.fromBundle(getIntent().getExtras());


    }
}