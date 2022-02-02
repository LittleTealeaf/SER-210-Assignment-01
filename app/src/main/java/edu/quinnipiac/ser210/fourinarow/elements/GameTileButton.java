package edu.quinnipiac.ser210.fourinarow.elements;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class GameTileButton extends AppCompatButton {

    public GameTileButton(@NonNull Context context) {
        super(context);
    }

    public GameTileButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameTileButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setState(State state) {

    }


    enum State {
        EMPTY,COMPUTER,PLAYER;
    }
}
