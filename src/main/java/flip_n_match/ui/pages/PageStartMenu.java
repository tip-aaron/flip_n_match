package flip_n_match.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatClientProperties;

import flip_n_match.App;
import flip_n_match.constants.Metadata;
import flip_n_match.ui.icons.SVGIconUIColor;
import flip_n_match.ui.system.Navigator;
import flip_n_match.ui.system.Page;
import net.miginfocom.swing.MigLayout;

public class PageStartMenu extends Page {
    private JPanel headerContainer;
    private JLabel title;
    private JLabel description;

    private JPanel buttonsContainer;
    private JButton playButton;
    private JButton settingsButton;
    private JButton exitButton;

    private ActionListener playActionListener;
    private ActionListener settingsActionListener;
    private ActionListener exitActionListener;

    @Override
    public void init() {
        setLayout(new MigLayout("flowx, wrap, gapy 64, insets 0, al center center", "[grow, fill]"));

        headerContainer = new JPanel(new MigLayout("flowy, gapy 8, insets 0, al center center", "[grow, fill]"));
        title = new JLabel(Metadata.APP_TITLE.toUpperCase(Locale.ENGLISH));
        description = new JLabel("<html>Flip the cards and match the numbers... or go <b>BOOM</b>!</html>");

        title.putClientProperty(FlatClientProperties.STYLE_CLASS, "h00");
        title.setHorizontalAlignment(JLabel.CENTER);
        description.putClientProperty(FlatClientProperties.STYLE_CLASS, "muted");
        description.setHorizontalAlignment(JLabel.CENTER);

        headerContainer.add(title);
        headerContainer.add(description);

        buttonsContainer = new JPanel(
                new MigLayout("flowx, wrap, insets 0, gap 16, al center center", "[grow, fill, ::320px]"));
        playButton = new JButton("Play", new SVGIconUIColor("play.svg", 1, "foreground.background"));
        settingsButton = new JButton("Settings", new SVGIconUIColor("settings.svg", 1, "foreground.background"));
        exitButton = new JButton("Quit", new SVGIconUIColor("logout.svg", 1, "foreground.background"));

        playButton.setHorizontalAlignment(JButton.CENTER);
        settingsButton.setHorizontalAlignment(JButton.CENTER);
        exitButton.setHorizontalAlignment(JButton.CENTER);

        buttonsContainer.add(playButton);
        buttonsContainer.add(settingsButton);
        buttonsContainer.add(exitButton);

        add(headerContainer, "grow");
        add(buttonsContainer, "grow");

        playActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Navigator.navigate(PageGame.class);
            }
        };

        settingsActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Navigator.navigate(PageSettings.class);
            }
        };

        exitActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int res = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(headerContainer),
                        "Are you sure you want to exit the game?");

                if (res == 0) {
                    App.close();
                }
            }
        };
    }

    @Override
    public void open() {
        playButton.addActionListener(playActionListener);
        settingsButton.addActionListener(settingsActionListener);
        exitButton.addActionListener(exitActionListener);
    }

    @Override
    public void close() {
        playButton.removeActionListener(playActionListener);
        settingsButton.removeActionListener(settingsActionListener);
        exitButton.removeActionListener(exitActionListener);
    }
}
