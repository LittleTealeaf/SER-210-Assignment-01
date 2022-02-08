package edu.quinnipiac.ser210.fourinarow.elements;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatButton;

import edu.quinnipiac.ser210.fourinarow.R;
import edu.quinnipiac.ser210.fourinarow.game.DisplayGame;
import edu.quinnipiac.ser210.fourinarow.game.FourInARow;
import edu.quinnipiac.ser210.fourinarow.game.IGame;

public class GameBoard extends GridLayout implements View.OnClickListener {

    private static final String TAG = "GameBoard";

    private DisplayGame game;
    private AppCompatButton board[];

    public GameBoard(Context context) {
        super(context);
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setGame(DisplayGame game) {
        this.game = game;
        removeAllViews();
        setRowCount(6);
        setColumnCount(6);
        board = new AppCompatButton[6 * 6];
        for(int i = 0; i < board.length; i++) {
            board[i] = new AppCompatButton(getContext());
            int index = i;
            board[i].setOnClickListener(view -> onBoardClick(index));
            board[i].setLayoutParams(new GridLayout.LayoutParams() {{
                this.height = GameBoard.this.getLayoutParams().height / 6;
                this.width = GameBoard.this.getLayoutParams().width / 6;
            }});

            addView(board[i]);
        }
        updateGame();
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int sideLength = Math.min(widthSpec,heightSpec);
        super.onMeasure(sideLength,sideLength);
    }

    public void onBoardClick(int index) {
        game.setMove(FourInARow.ID_PLAYER,index);
        game.setMove(FourInARow.ID_COMPUTER,game.getComputerMove());
        Log.d(TAG,"Clicked: " + index);
        updateGame();
    }

    public void updateGame() {
        for(int i = 0; i < board.length; i++) {
            switch(game.get(i)) {
                case DisplayGame.EMPTY:
                    board[i].setBackgroundTintList(getContext().getResources().getColorStateList(R.color.button_empty,getContext().getTheme()));
                    break;
                case DisplayGame.BLUE:
                    board[i].setBackgroundTintList(getContext().getResources().getColorStateList(R.color.button_computer,getContext().getTheme()));
                    break;
                case DisplayGame.RED:
                    board[i].setBackgroundTintList(getContext().getResources().getColorStateList(R.color.button_player,getContext().getTheme()));
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
