package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.elements.GameBoard;
import ser.quinnipiac.edu.connectn.game.Game;
import ser.quinnipiac.edu.connectn.game.GameFactory;
import ser.quinnipiac.edu.connectn.game.GameListener;
import ser.quinnipiac.edu.connectn.game.IGame;

public class GameActivity extends AppCompatActivity implements GameListener {

    private IGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(savedInstanceState != null) {
            game = loadFromBundle(savedInstanceState);
        } else {
            game = new Game();
        }

        game.addListener(this);
        ((GameBoard) findViewById(R.id.game_gameboard)).setGame(game);
    }

    @Override
    public void onStateChanged(int newState, int oldState) {

    }

    @Override
    public void onBoardChanged(int location, int value) {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(GameFactory.KEY_ROW_COUNT,game.getRowCount());
        outState.putInt(GameFactory.KEY_COLUMN_COUNT,game.getColumnCount());
        outState.putInt(GameFactory.KEY_CONNECT_LENGTH,game.getConnectLength());
        int[] board = new int[game.getRowCount() * game.getColumnCount()];
        for(int i = 0; i < board.length; i++) {
            board[i] = game.get(i);
        }
        outState.putIntArray(GameFactory.KEY_GAME_BOARD, board);
    }

    private IGame loadFromBundle(Bundle bundle) {
        if(bundle.containsKey(GameFactory.KEY_COLUMN_COUNT) && bundle.containsKey(GameFactory.KEY_ROW_COUNT) && bundle.containsKey(GameFactory.KEY_CONNECT_LENGTH)) {
            int rowCount = bundle.getInt(GameFactory.KEY_ROW_COUNT);
            int columnCount = bundle.getInt(GameFactory.KEY_COLUMN_COUNT);
            int connectLength = bundle.getInt(GameFactory.KEY_CONNECT_LENGTH);

            Game game = new Game(rowCount,columnCount,connectLength);

            if(bundle.containsKey(GameFactory.KEY_GAME_BOARD)) {
                int[] board = bundle.getIntArray(GameFactory.KEY_GAME_BOARD);
                for(int i = 0; i < board.length; i++) {
                    game.set(i%columnCount,i/rowCount,board[i]);
                }
            }
            return game;
        } else {
            return new Game();
        }
    }
}