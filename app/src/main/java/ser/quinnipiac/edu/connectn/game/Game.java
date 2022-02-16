package ser.quinnipiac.edu.connectn.game;

import android.graphics.Point;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game implements IGame {


    private static final Point[] DIRECTIONS;

    static {
        DIRECTIONS = new Point[] {
                new Point(1,0),
                new Point(-1,1),
                new Point(0,1),
                new Point(1,1)
        };
    }

    private final int columns, rows, connectLength;
    private int gameState;
    private final int[][] board;
    private final AIConstants ai;
    private final Random random;
    private final Collection<GameListener> listeners;


    public Game() {
        this(6,6,4,AIConstants.HARD);
    }

    public Game(int columns, int rows, int connectLength) {
        this(columns,rows,connectLength,AIConstants.HARD);
    }

    public Game(int columns, int rows, int connectLength, AIConstants ai) {
        this.ai = ai;
        this.columns = columns;
        this.rows = rows;
        this.connectLength = connectLength;
        this.board = new int[rows][columns];
        random = new Random();
        listeners = new LinkedList<>();
    }

    @Override
    public int getColumnCount() {
        return columns;
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getConnectLength() {
        return connectLength;
    }

    @Override
    public void clearBoard() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                set(col,row,EMPTY);
            }
        }
    }


    @Override
    public void setMove(int player, int location) {
        if(player == PLAYER || player == COMPUTER) {
            int row = location / columns, col = location % columns;
            if(get(col,row) == EMPTY) {
                set(col,row,player);
            }

        }
    }

    public void set(int col, int row, int value) {
        board[row][col] = value;
        onBoardChanged(row * columns + col,value);
    }

    @Override
    public int getComputerMove() {

        final List<Integer> bestMoves = new LinkedList<>();
        int maxEval = 0;
        for(int l = 0; l < rows * columns; l++) {
            final int eval_computer = evaluateLocation(l,COMPUTER) * ai.getComputer();
            final int eval_player = evaluateLocation(l,PLAYER) * ai.getPlayer();
            final int eval = eval_computer + eval_player;

            if(maxEval < eval) {
                maxEval = eval;
                bestMoves.clear();
                bestMoves.add(l);
            } else if(maxEval == eval) {
                bestMoves.add(l);
            }
        }

        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    @Override
    public int getGameState() {
        boolean stillPlayable = false;
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                final int val = board[row][col];
                if(val != EMPTY) {
                    for(Point d : DIRECTIONS) {
                        for(int i = 1; i < connectLength; i++) {
                            if(get(col + d.x *  i, row + d.y * i) != val) {
                                break;
                            } else if(i == connectLength - 1) {
                                return val;
                            }
                        }
                    }
                } else {
                    stillPlayable = true;
                }
            }
        }
        return stillPlayable ? PLAYING : TIE;
    }

    private int evaluateLocation(int location, int player) {
        int row = location / columns, col = location % columns;
        int locationVal = get(col,row);

        if(locationVal == EMPTY) {
            int eval = 0;

            for(Point d : DIRECTIONS) {
                int countEmpty = 0, countPopulated = 0;
                for(int c = -1; c <= 1; c++) {
                    for(int i = 0; i < connectLength; i++) {
                        final int val = get(col + d.x * i * c, row + d.y * i * c);
                        if(val == EMPTY) {
                            countEmpty++;
                        } else if(val == player) {
                            countPopulated++;
                        } else {
                            break;
                        }
                    }
                }
                if(countEmpty + countPopulated >= connectLength - 1) {
                    eval += (countEmpty + ai.getEmpty() + countPopulated * ai.getPopulated()) * Math.pow(ai.getStreak(player), countPopulated);
                }
            }


            return eval;

        } else {
            return -1;
        }
    }

    @Override
    public int get(int location) {
        if(location > -1 && location < rows * columns) {
            return board[location/columns][location%columns];
        } else {
            return NONE;
        }
    }

    @Override
    public boolean addListener(GameListener listener) {
        return listener != this && listeners.add(listener);
    }

    @Override
    public boolean removeListener(GameListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public Collection<GameListener> getListeners() {
        return listeners;
    }

    public int get(int col, int row) {
        if(col > -1 && col < columns && row > -1 && row < rows) {
            return board[row][col];
        } else {
            return NONE;
        }
    }


    protected void updateState() {
        int gameState = getGameState();
        if(this.gameState != gameState) {
            onStateChanged(gameState,this.gameState);
            this.gameState = gameState;
        }
    }

    @Override
    public void onStateChanged(int newState, int oldState) {
        for(GameListener listener : listeners) {
            listener.onStateChanged(newState,oldState);
        }
    }

    @Override
    public void onBoardChanged(int location, int value) {
        for(GameListener listener : listeners) {
            listener.onBoardChanged(location,value);
        }
        updateState();
    }
}
