package edu.quinnipiac.ser210.fourinarow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.quinnipiac.ser210.fourinarow.R;

/**
 * @author Thomas Kwashnak
 */
public class GreeterActivity extends AppCompatActivity {

    public static final String KEY_NAME;

    static {
        KEY_NAME = "name";
    }

    private EditText inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeter);

        inputName = (EditText) findViewById(R.id.greeter_input_name);

        if (savedInstanceState != null) {
            inputName.setText(savedInstanceState.getString(KEY_NAME));
        }
    }

    public void onClickBegin(View view) {
        String name = inputName.getText().toString();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(KEY_NAME, name);
        startActivity(intent);
    }

    public void onClickInstructions(View view) {
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_NAME, inputName.getText().toString());
    }
}