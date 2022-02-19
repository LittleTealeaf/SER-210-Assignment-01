package ser.quinnipiac.edu.connectn.game_old_two;

public class Difficulty implements IDifficulty {


    public static final String DIFFICULTY;

    public static final int EASY, NORMAL, HARD, CUSTOM;

    public static final Difficulty EASY_DIFFICULTY, NORMAL_DIFFICULTY, HARD_DIFFICULTY;

    static {
        DIFFICULTY = "Difficulty:Difficulty";
        CUSTOM = 0;
        EASY = 1;
        NORMAL = 2;
        HARD = 3;
        EASY_DIFFICULTY = null;
        NORMAL_DIFFICULTY = null;
        HARD_DIFFICULTY = null;
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
}
