package ser.quinnipiac.edu.connectn.game;

/**
 * @author Thomas Kwashnak
 */
public class AIConstants {

    public static final AIConstants HARD;

    static {
        HARD = new AIConstants() {{
            setComputer(3);
            setPlayer(4);
            setPopulated(5);
            setEmpty(1);
            setStreakPlayer(5);
            setStreakComputer(getStreakPlayer() + 1);
        }};
    }

    private int computer, player, populated, empty, streakPlayer, streakComputer;

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }

    public int getEmpty() {
        return empty;
    }

    public void setEmpty(int empty) {
        this.empty = empty;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPopulated() {
        return populated;
    }

    public void setPopulated(int populated) {
        this.populated = populated;
    }

    public int getStreakPlayer() {
        return streakPlayer;
    }

    public int getStreakComputer() {
        return streakComputer;
    }

    public void setStreakComputer(int streakComputer) {
        this.streakComputer = streakComputer;
    }

    public void setStreakPlayer(int streakPlayer) {
        this.streakPlayer = streakPlayer;
    }

    public int getStreak(int player) {
        switch (player) {
            case IGame.COMPUTER:
                return streakComputer;
            case IGame.PLAYER:
                return streakPlayer;
            default:
                return 0;
        }
    }
}
