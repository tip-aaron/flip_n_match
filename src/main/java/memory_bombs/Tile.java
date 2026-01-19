package memory_bombs;

public class Tile {
    public static enum TileType {
        NORMAL,
        BOMB,
        POWER_UP
    }

    TileType type;
    int id;
    boolean revealed;

    public Tile(TileType type, int id) {
        this.type = type;
        this.id = id;
    }

    public Tile(TileType type) {
        this(type, -1);
    }

    public TileType getType() {
        return type;
    }
}
