package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Arrays;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.game.Difficulty;
import ser.quinnipiac.edu.connectn.game.GameFactory;

/**
 * @author Thomas Kwashnak
 */
public class SettingsActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener, AdapterView.OnItemSelectedListener {

    private GameFactory gameFactory;
    private NumberPicker connectPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Difficulty[] difficulties = Difficulty.values();

        findViewById(R.id.settings_button_back).setOnClickListener(this::onSaveAndClose);

        NumberPicker rowPicker = findViewById(R.id.settings_number_rows);
        NumberPicker columnPicker = findViewById(R.id.settings_number_columns);
        connectPicker = findViewById(R.id.settings_number_connect);

        rowPicker.setOnValueChangedListener(this);
        columnPicker.setOnValueChangedListener(this);
        connectPicker.setOnValueChangedListener(this);

        Spinner spinner = findViewById(R.id.settings_spinner_difficulty);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficulties));
        spinner.setOnItemSelectedListener(this);

        if(savedInstanceState != null) {
            gameFactory = new GameFactory(savedInstanceState);
        } else {
            gameFactory = new GameFactory(getIntent().getExtras());
        }

        for(int i = 0; i < difficulties.length; i++) {
            if(difficulties[i] == gameFactory.getDifficulty()) {
                spinner.setSelection(i);
                break;
            }
        }

        int min = 3, max = 20;
        rowPicker.setMinValue(min);
        rowPicker.setMaxValue(max);
        columnPicker.setMinValue(min);
        columnPicker.setMaxValue(max);
        updateConnectRange();

        int rows = gameFactory.getRowCount();
        int cols = gameFactory.getColumnCount();
        int connect = gameFactory.getConnectLength();

        connectPicker.setValue(connect);
        rowPicker.setValue(rows);
        columnPicker.setValue(cols);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        gameFactory.toBundle(outState);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
        if(numberPicker.getId() == R.id.settings_number_connect) {
            gameFactory.setConnectLength(newValue);
        } else {
            if(numberPicker.getId() == R.id.settings_number_columns) {
                gameFactory.setColumnCount(newValue);
            } else if(numberPicker.getId() == R.id.settings_number_rows){
                gameFactory.setRowCount(newValue);
            }
            updateConnectRange();
        }
    }

    protected void updateConnectRange() {
        int max = Math.min(gameFactory.getRowCount(),gameFactory.getColumnCount());
        int suggested = Math.min(max * 3 / 4,3);

        connectPicker.setMinValue(3);
        connectPicker.setMaxValue(max);

        if(gameFactory.getConnectLength() > max) {
            connectPicker.setValue(suggested);
        }
    }

    public void onSaveAndClose(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        gameFactory.toBundle(bundle);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gameFactory.setDifficulty(Difficulty.values()[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}