package memory_bombs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class GameState {
    int lives;

    public GameState(int initialLives) {
        this.lives = initialLives;
    }

    public void setLife(int newLives) {
        this.lives = newLives;
        notifyListeners();
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
            notifyListeners();
        } else {
            // Handle game over scenario

            JOptionPane.showMessageDialog(null, "Game Over! You've run out of lives.", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        }
    }

    public int getLives() {
        return lives;
    }

    private List<Runnable> listeners = new ArrayList<>();

    public void listen(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    public void unlisten(Runnable listener) {
        listeners.remove(listener);
    }
}
