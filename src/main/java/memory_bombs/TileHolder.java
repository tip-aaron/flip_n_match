package memory_bombs;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import memory_bombs.Tile.TileType;
import net.miginfocom.swing.MigLayout;

public class TileHolder extends JPanel {
    private int pairsAmount;
    int bombsAmount;
    private int powerUpsAmount;
    private int gridSize;
    private List<Tile> tiles;
    private JButton[] buttons;

    private Tile firstPick = null;
    private JButton firstButton = null;
    private boolean lock = false;
    private GameState gameState;

    public TileHolder(GameState gameState, int pairsAmount) {
        assert pairsAmount > 0 : "Pairs amount must be greater than zero.";

        this.gameState = gameState;
        this.pairsAmount = pairsAmount;
        this.bombsAmount = Math.max(1, pairsAmount / 5); // At least one bomb, or 20% of pairs

        int baseTiles = (pairsAmount * 2) + bombsAmount;

        // Find smallest square grid
        this.gridSize = (int) Math.ceil(Math.sqrt(baseTiles));
        int targetTiles = gridSize * gridSize;

        // Fill remaining slots with power-ups
        this.powerUpsAmount = targetTiles - baseTiles;

        setLayout(new MigLayout(String.format("al center center, wrap %d", gridSize)));

        tiles = new ArrayList<>();

        for (int i = 0; i < pairsAmount * 2; i++) {
            tiles.add(new Tile(Tile.TileType.NORMAL, i));
        }
        for (int i = 0; i < bombsAmount; i++) {
            tiles.add(new Tile(Tile.TileType.BOMB));
        }
        for (int i = 0; i < powerUpsAmount; i++) {
            tiles.add(new Tile(Tile.TileType.POWER_UP));
        }

        shuffleTiles();

        buttons = new JButton[tiles.size()];

        for (int i = 0; i < tiles.size(); ++i) {
            JButton btn = new JButton("?");
            final int index = i;
            btn.setFocusable(false);
            btn.setFont(btn.getFont().deriveFont(Font.BOLD, 24f));
            btn.setPreferredSize(new Dimension(96, 96));

            btn.addActionListener(e -> handleClick(index));
            buttons[i] = btn;
            add(btn);
        }
    }

    private void handleClick(int index) {
        if (lock) {
            return;
        }

        Tile tile = tiles.get(index);
        JButton btn = buttons[index];

        if (tile.revealed)
            return;

        reveal(tile, btn);

        if (firstPick != null && tile.type != TileType.NORMAL) {
            hide(firstPick, firstButton);
            firstPick = null;
            firstButton = null;
        }

        if (tile.type == TileType.BOMB) {
            gameState.loseLife();
            JOptionPane.showMessageDialog(this,
                    "💣 Bomb! " + gameState.getLives() + " lives left.");

            if (gameState.getLives() <= 0) {
                gameOver();
            }
            return;
        }

        if (tile.type == TileType.POWER_UP) {
            JOptionPane.showMessageDialog(this,
                    "⚡ Power-up collected!");
            return;
        }

        if (firstPick == null) {
            firstPick = tile;
            firstButton = btn;
        } else {
            checkMatch(tile, btn);
        }
    }

    private void reveal(Tile tile, JButton btn) {
        tile.revealed = true;

        switch (tile.type) {
            case NORMAL -> btn.setText(String.valueOf(tile.id));
            case BOMB -> btn.setText("💣");
            case POWER_UP -> btn.setText("⚡");
        }

        btn.setEnabled(false);
    }

    private void hide(Tile tile, JButton btn) {
        tile.revealed = false;
        btn.setText("?");
        btn.setEnabled(true);
    }

    private void checkMatch(Tile second, JButton secondBtn) {
        lock = true;

        Timer timer = new Timer(500, e -> {
            if (firstPick.id == second.id) {
                // Match found
            } else {
                hide(firstPick, firstButton);
                hide(second, secondBtn);
            }

            firstPick = null;
            firstButton = null;
            lock = false;
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "☠ Game Over!");
        System.exit(0);
    }

    private void shuffleTiles() {
        Collections.shuffle(tiles);
    }
}
