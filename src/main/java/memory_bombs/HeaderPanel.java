package memory_bombs;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel {
    private JLabel livesLabel;

    public HeaderPanel(GameState gameState) {
        livesLabel = new JLabel("Lives: " + gameState.getLives());
        add(livesLabel);

        gameState.listen(() -> {
            livesLabel.setText("Lives: " + gameState.getLives());
        });
    }
}