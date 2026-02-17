package flip_n_match.game;

import lombok.Data;
import lombok.Getter;

public abstract class Tile {
    @Getter
    private int row;
    @Getter
    private int col;
    @Getter
    private String textData;

    public abstract void reveal();
}
