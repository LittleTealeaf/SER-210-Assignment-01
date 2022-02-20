package ser.quinnipiac.edu.connectn.game;

public enum Difficulty implements IDifficulty {
    EASY(1,1,2,3,2,2),
    NORMAL(1,2,1,3,2,3),
    HARD(3,4,1,5,5,6);

    private final int weightComputer;
    private final int weightPlayer;
    private final int weightEmpty;
    private final int weightPopulated;
    private final int streakPlayer;
    private final int streakComputer;

    Difficulty(int weightComputer, int weightPlayer, int weightEmpty, int weightPopulated, int streakPlayer, int streakComputer) {
        this.weightComputer = weightComputer;
        this.weightPlayer = weightPlayer;
        this.weightEmpty = weightEmpty;
        this.weightPopulated = weightPopulated;
        this.streakPlayer = streakPlayer;
        this.streakComputer = streakComputer;
    }

    @Override
    public int getWeightComputer() {
        return weightComputer;
    }

    @Override
    public int getWeightPlayer() {
        return weightPlayer;
    }

    @Override
    public int getWeightEmpty() {
        return weightEmpty;
    }

    @Override
    public int getWeightPopulated() {
        return weightPopulated;
    }

    @Override
    public int getStreakComputer() {
        return streakComputer;
    }

    @Override
    public int getStreakPlayer() {
        return streakPlayer;
    }
}
