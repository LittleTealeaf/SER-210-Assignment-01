package ser.quinnipiac.edu.connectn.game_old;

/**
 * @author Thomas Kwashnak
 */
public class Computer implements IComputer {

    public static final Computer EASY, NORMAL, HARD;

    static {
        EASY = null;
        NORMAL = null;
        HARD = new Computer(3,4,5,1,5,6);
    }

    private final int computer, player, populated, empty, streakPlayer, streakComputer;

    public Computer(int computer, int player, int populated, int empty, int streakPlayer, int streakComputer) {
        this.computer = computer;
        this.player = player;
        this.populated = populated;
        this.empty = empty;
        this.streakPlayer = streakPlayer;
        this.streakComputer = streakComputer;
    }

    public int getComputer() {
        return computer;
    }

    public int getEmpty() {
        return empty;
    }

    public int getPlayer() {
        return player;
    }

    public int getPopulated() {
        return populated;
    }

    public int getStreakPlayer() {
        return streakPlayer;
    }

    public int getStreakComputer() {
        return streakComputer;
    }
}
