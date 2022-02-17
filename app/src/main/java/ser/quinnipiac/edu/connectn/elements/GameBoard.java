package ser.quinnipiac.edu.connectn.elements;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatButton;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.game.GameListener;
import ser.quinnipiac.edu.connectn.game.IGame;

/**
 * @author Thomas Kwashnak
 */
public class GameBoard extends GridLayout implements GameListener {

    private IGame game;
    private Button[] board;

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
        game.addListener(this);
        removeAllViews();

        setRowCount(game.getRowCount());
        setColumnCount(game.getColumnCount());
        board = new AppCompatButton[game.getRowCount() * game.getColumnCount()];

        Spec gridSpec = GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f);
        for(int i = 0; i < board.length; i++) {
            int index = i;
            addView(board[i] = new AppCompatButton(getContext()) {{
                setOnClickListener(view -> GameBoard.this.playerMove(index));
                setLayoutParams(new GridLayout.LayoutParams(gridSpec,gridSpec) {{
                    this.width = 0;
                    this.height = 0;
                }});
            }});
            onBoardChanged(i,game.get(i));
        }
        onStateChanged(game.getGameState(),IGame.PLAYING);
    }

    protected void playerMove(int location) {
        game.setMove(IGame.PLAYER,location);
        if(game.getGameState() == IGame.PLAYING) {
            game.setMove(IGame.COMPUTER,game.getComputerMove());
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int sideLength = Math.min(widthSpec, heightSpec);
        super.onMeasure(sideLength,sideLength);
    }

    @Override
    public void onStateChanged(int newState, int oldState) {
        if(newState == IGame.PLAYING ^ oldState == IGame.PLAYING) {
            boolean gameState = newState == IGame.PLAYING;
            for(Button button : board) {
                button.setClickable(gameState);
            }
        }
    }

    @Override
    public void onBoardChanged(int location, int value) {
        board[location].setBackgroundTintList(getContext().getResources().getColorStateList(getTintId(value), getContext().getTheme()));
    }

    private static int getTintId(int value) {
        switch(value) {
            case IGame.PLAYER:
                return R.color.player;
            case IGame.COMPUTER:
                return R.color.computer;
            default:
                return R.color.empty;
        }
    }
}
