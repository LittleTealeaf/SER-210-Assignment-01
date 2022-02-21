package ser.quinnipiac.edu.connectn.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.game.GameFactory;

/**
 * @author Thomas Kwashnak
 */
public class MainActivity extends AppCompatActivity {

    public static final String NAME;

    static {
        NAME = "name";
    }

    private EditText inputName;
    private GameFactory gameFactory;

    private ActivityResultLauncher<Intent> settingsLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.main_input_name);

        /*
        It's best practice to use this instead of using the XML file, because specifying it in the XML file uses extra memory
         */
        findViewById(R.id.main_button_play).setOnClickListener(this::onPlay);
        findViewById(R.id.main_button_instructions).setOnClickListener(this::onInstructions);
        findViewById(R.id.main_button_settings).setOnClickListener(this::onSettings);


        if (savedInstanceState != null) {
            inputName.setText(savedInstanceState.getString(NAME));
            gameFactory = new GameFactory(savedInstanceState);
        } else {
            gameFactory = new GameFactory();
        }


        settingsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->  {
            if(result.getResultCode() == Activity.RESULT_OK) {
                assert result.getData() != null;
                gameFactory = new GameFactory(result.getData().getExtras());
            }
        });

    }

    public void onSettings(View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        Bundle bundle = new Bundle();
        gameFactory.toBundle(bundle);
        intent.putExtras(bundle);
        settingsLauncher.launch(intent);
    }

    public void onPlay(View view) {
        if(!inputName.getText().toString().equals("")) {
            Intent intent = new Intent(this,GameActivity.class);
            intent.putExtra(NAME,inputName.getText().toString());
            intent.putExtras(gameFactory.createGameBundle());
            startActivity(intent);
        } else {
            Toast.makeText(this,"Please Enter a Name",Toast.LENGTH_LONG).show();
        }
    }

    public void onInstructions(View view) {
        startActivity(new Intent(this, InstructionsActivity.class));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        gameFactory.toBundle(outState);
        outState.putString(NAME, inputName.getText().toString());
    }
}