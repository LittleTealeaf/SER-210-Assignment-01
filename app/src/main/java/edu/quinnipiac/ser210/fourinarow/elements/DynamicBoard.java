package edu.quinnipiac.ser210.fourinarow.elements;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

public class DynamicBoard extends GridLayout {

    public DynamicBoard(Context context) {
        super(context);
    }

    public DynamicBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DynamicBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int sideLength = Math.min(widthSpec,heightSpec);
        super.onMeasure(sideLength,sideLength);
    }
}
