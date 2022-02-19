package ser.quinnipiac.edu.connectn.game;

import java.util.Collection;

public class Game implements IGame {



    public Game(ISettings settings, IDifficulty difficulty) {

    }

    @Override
    public void onStateChanged(int newState, int oldState) {

    }

    @Override
    public void onBoardChanged(int location, int value) {

    }

    @Override
    public int getWeightComputer() {
        return 0;
    }

    @Override
    public int getWeightPlayer() {
        return 0;
    }

    @Override
    public int getWeightEmpty() {
        return 0;
    }

    @Override
    public int getWeightPopulated() {
        return 0;
    }

    @Override
    public int getStreakComputer() {
        return 0;
    }

    @Override
    public int getStreakPlayer() {
        return 0;
    }

    @Override
    public void clearBoard() {

    }

    @Override
    public void setMove(int player, int location) {

    }

    @Override
    public int getComputerMove() {
        return 0;
    }

    @Override
    public int getGameState() {
        return 0;
    }

    @Override
    public int get(int location) {
        return 0;
    }

    @Override
    public boolean addListener(GameListener listener) {
        return false;
    }

    @Override
    public boolean removeListener(GameListener listener) {
        return false;
    }

    @Override
    public Collection<GameListener> getListeners() {
        return null;
    }

    @Override
    public int[] getBoard() {
        return new int[0];
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public int getConnectLength() {
        return 0;
    }
}
