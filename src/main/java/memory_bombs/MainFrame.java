package memory_bombs;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame {
    public MainFrame() {
        setLayout(new MigLayout("al center center, fill"));
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        setTitle("Memory Bombs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1280, 720));
    }
}
