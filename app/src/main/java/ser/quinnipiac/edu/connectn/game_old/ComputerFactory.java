package ser.quinnipiac.edu.connectn.game_old;

import android.os.Bundle;

public class ComputerFactory implements IComputer {

    private int computer, player, populated, empty, streakPlayer, streakComputer;

    public ComputerFactory() {
        computer = 3;
        player = 4;
        populated = 5;
        empty = 1;
        streakPlayer = 5;
        streakComputer = 6;
    }

    public ComputerFactory(IComputer instance) {
        computer = instance.getComputer();
        player = instance.getPlayer();
        populated = instance.getPopulated();
        empty = instance.getEmpty();
        streakPlayer = instance.getStreakPlayer();
        streakComputer = instance.getStreakComputer();
    }

    public ComputerFactory setStreakPlayer(int streakPlayer) {
        this.streakPlayer = streakPlayer;
        return this;
    }

    public ComputerFactory setStreakComputer(int streakComputer) {
        this.streakComputer = streakComputer;
        return this;
    }

    public int getStreakPlayer() {
        return streakPlayer;
    }

    public int getStreakComputer() {
        return streakComputer;
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

    public ComputerFactory setComputer(int computer) {
        this.computer = computer;
        return this;
    }

    public int getPopulated() {
        return populated;
    }

    public ComputerFactory setEmpty(int empty) {
        this.empty = empty;
        return this;
    }

    public ComputerFactory setPlayer(int player) {
        this.player = player;
        return this;
    }

    public ComputerFactory setPopulated(int populated) {
        this.populated = populated;
        return this;
    }

    public Computer create() {
        return new Computer(computer, player, populated, empty, streakPlayer, streakComputer);
    }

    public static ComputerFactory fromBundle(Bundle bundle) {
        return new ComputerFactory()
                .setComputer(bundle.getInt(COMPUTER))
                .setPlayer(bundle.getInt(PLAYER))
                .setEmpty(bundle.getInt(EMPTY))
                .setPopulated(bundle.getInt(POPULATED))
                .setStreakComputer(bundle.getInt(STREAK_COMPUTER))
                .setStreakPlayer(bundle.getInt(STREAK_PLAYER));
    }

    public static IComputer createFromBundle(Bundle bundle) {
        return new Computer(bundle.getInt(COMPUTER),bundle.getInt(PLAYER),bundle.getInt(EMPTY),bundle.getInt(POPULATED),
                            bundle.getInt(STREAK_COMPUTER),bundle.getInt(STREAK_PLAYER));
    }
}
