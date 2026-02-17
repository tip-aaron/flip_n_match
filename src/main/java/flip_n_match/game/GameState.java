package flip_n_match.game;

import java.util.function.Consumer;

import flip_n_match.lib.Stopwatch;

public class GameState {
    private final Stopwatch stopwatch;

    private int[][] board;

    public GameState(Consumer<String> stopwatchListener) {
        this.stopwatch = new Stopwatch(stopwatchListener);
    }

}
