package edu.quinnipiac.ser210.fourinarow.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.quinnipiac.ser210.fourinarow.R;

public class GreeterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeter);
    }

    public void onStart(View view) {

    }
}