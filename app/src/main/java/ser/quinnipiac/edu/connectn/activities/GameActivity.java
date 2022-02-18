package ser.quinnipiac.edu.connectn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.connectn.R;
import ser.quinnipiac.edu.connectn.elements.GameBoard;
import ser.quinnipiac.edu.connectn.game_old.Game;
import ser.quinnipiac.edu.connectn.game.GameListener;
import ser.quinnipiac.edu.connectn.game_old.IGame;

/**
 * @author Thomas Kwashnak
 */
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
        game.saveToBundle(outState);
//        outState.putInt(GameFactory.ROWS, game.getRowCount());
//        outState.putInt(GameFactory.COLUMNS, game.getColumnCount());
//        outState.putInt(GameFactory.CONNECT_LENGTH, game.getConnectLength());
//        int[] board = new int[game.getRowCount() * game.getColumnCount()];
//        for(int i = 0; i < board.length; i++) {
//            board[i] = game.get(i);
//        }
//        outState.putIntArray(IGame.BOARD, board);
    }

    private IGame loadFromBundle(Bundle bundle) {
        return null;
//        if(bundle.containsKey(GameFactory.COLUMNS) && bundle.containsKey(GameFactory.ROWS) && bundle.containsKey(GameFactory.CONNECT_LENGTH)) {
//            int rowCount = bundle.getInt(GameFactory.ROWS);
//            int columnCount = bundle.getInt(GameFactory.COLUMNS);
//            int connectLength = bundle.getInt(GameFactory.CONNECT_LENGTH);
//
//            Game game = new Game(rowCount,columnCount,connectLength);
//
//            if(bundle.containsKey(IGame.BOARD)) {
//                int[] board = bundle.getIntArray(IGame.BOARD);
//                for(int i = 0; i < board.length; i++) {
//                    game.set(i%columnCount,i/rowCount,board[i]);
//                }
//            }
//            return game;
//        } else {
//            return new Game();
//        }
    }
}