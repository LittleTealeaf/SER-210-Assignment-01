package ser.quinnipiac.edu.connectn.game;

public interface IDifficulty {
    String WEIGHT_COMPUTER = "difficulty_weight_computer";
    String WEIGHT_PLAYER = "difficulty_weight_player";
    String WEIGHT_EMPTY = "difficulty_weight_empty";
    String WEIGHT_POPULATED = "difficulty_weight_populated";
    String STREAK_COMPUTER = "difficulty_streak_computer";
    String STREAK_PLAYER = "difficulty_streak_player";

    int getWeightComputer();
    int getWeightPlayer();
    int getWeightEmpty();
    int getWeightPopulated();
    int getStreakComputer();
    int getStreakPlayer();
}
