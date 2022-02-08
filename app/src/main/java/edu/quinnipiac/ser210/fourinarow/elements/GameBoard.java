package edu.quinnipiac.ser210.fourinarow.elements;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatButton;

import edu.quinnipiac.ser210.fourinarow.R;
import edu.quinnipiac.ser210.fourinarow.game.IGame;

public class GameBoard extends GridLayout {

    private static final String TAG = "GameBoard";

    private IGame game;
    private AppCompatButton[] board;
    private int lastGameState;
    private GameListener listener;

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

    public void setGame(IGame game) {
        this.game = game;
        removeAllViews();
        setRowCount(6);
        setColumnCount(6);
        board = new AppCompatButton[6 * 6];
        for(int i = 0; i < board.length; i++) {
            board[i] = new AppCompatButton(getContext());
            int index = i;
            board[i].setOnClickListener(view -> move(index));
            board[i].setLayoutParams(new GridLayout.LayoutParams() {{
                this.height = GameBoard.this.getLayoutParams().height / 6;
                this.width = GameBoard.this.getLayoutParams().width / 6;
            }});
            addView(board[i]);
        }
        updateBoard();
    }

    public void setListener(GameListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int sideLength = Math.min(widthSpec,heightSpec);
        super.onMeasure(sideLength,sideLength);
    }

    public void updateBoard() {
        //Update all buttons
        for(int i = 0; i < board.length; i++) {
            updateButton(i);
        }

        int gameState = game.checkForWinner();
        if(gameState != lastGameState) {
            if(listener != null) {
                listener.onGameStateUpdate(gameState);
            }
            lastGameState = gameState;
        }

        for(AppCompatButton button : board) {
            button.setClickable(gameState == IGame.PLAYING);
        }
    }


    private void updateButton(int index) {
        switch(game.get(index)) {
            case IGame.EMPTY:
                board[index].setBackgroundTintList(getContext().getResources().getColorStateList(R.color.button_empty,getContext().getTheme()));
                break;
            case IGame.RED:
                board[index].setBackgroundTintList(getContext().getResources().getColorStateList(R.color.button_computer,getContext().getTheme()));
                break;
            case IGame.BLUE:
                board[index].setBackgroundTintList(getContext().getResources().getColorStateList(R.color.button_player,getContext().getTheme()));
        }
    }

    protected void move(int location) {
        game.setMove(IGame.BLUE,location);
        updateBoard();
        if(lastGameState == IGame.PLAYING) {
            game.setMove(IGame.RED,game.getComputerMove());
            updateBoard();
        }
    }
}
