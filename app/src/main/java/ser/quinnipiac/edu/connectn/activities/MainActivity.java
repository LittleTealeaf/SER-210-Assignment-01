package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ser.quinnipiac.edu.connectn.R;

/**
 * @author Thomas Kwashnak
 */
public class MainActivity extends AppCompatActivity {

    public static final String KEY_NAME;

    static {
        KEY_NAME = "name";
    }

    private EditText inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.main_input_name);

        /*
        These are less shaky than defining the method in the XML.
         */
        findViewById(R.id.main_button_play).setOnClickListener(view -> onPlay());
        findViewById(R.id.main_button_instructions).setOnClickListener(view -> onInstructions());
        findViewById(R.id.main_button_settings).setOnClickListener(view -> onSettings());

        if (savedInstanceState != null) {
            inputName.setText(savedInstanceState.getString(KEY_NAME));
        }


    }

    public void onSettings() {
        startActivity(new Intent(this,SettingsActivity.class));
    }

    public void onPlay() {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    public void onInstructions() {
        startActivity(new Intent(this, InstructionsActivity.class));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME,inputName.getText().toString());
    }
}