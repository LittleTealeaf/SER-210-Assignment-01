package ser.quinnipiac.edu.connectn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.connectn.R;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        findViewById(R.id.instructions_button_back).setOnClickListener(view -> finish());
    }
}