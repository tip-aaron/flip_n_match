package memory_bombs;

import java.awt.BorderLayout;

import com.formdev.flatlaf.FlatDarculaLaf;

public class App {
    static GameState gameState = new GameState(3);

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        MainFrame mainFrame = new MainFrame();

        HeaderPanel header = new HeaderPanel(gameState);
        TileHolder tileHolder = new TileHolder(gameState, 10);
        gameState.setLife(tileHolder.bombsAmount);
        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(tileHolder, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
