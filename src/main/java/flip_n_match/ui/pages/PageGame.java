package flip_n_match.ui.pages;

import com.formdev.flatlaf.FlatClientProperties;
import flip_n_match.config.GameDifficulty;
import flip_n_match.config.UserSettings;
import flip_n_match.constants.Metadata;
import flip_n_match.lib.Stopwatch;
import flip_n_match.ui.icons.SVGIconUIColor;
import flip_n_match.ui.system.Page;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class PageGame extends Page {
    private final JPanel headerPanel;
    private final TimerPanel timerPanel;
    private final DifficultyPanel difficultyPanel;
    private final BombsPanel bombsPanel;
    private final JButton settingsBtn;

    public PageGame() {
        headerPanel = new JPanel(new MigLayout("insets 0, flowx, al center center"));
        JLabel titleText = new JLabel(Metadata.APP_TITLE);
        timerPanel = new TimerPanel();
        difficultyPanel = new DifficultyPanel();
        bombsPanel = new BombsPanel();
        settingsBtn = new JButton("Settings",new SVGIconUIColor("settings.svg", 0.75f,  "foreground.background"));

        titleText.putClientProperty(FlatClientProperties.STYLE_CLASS, "h4");

        settingsBtn.putClientProperty(FlatClientProperties.STYLE_CLASS, "ghost");

        setLayout(new MigLayout(""));

        headerPanel.add(titleText, "gapright 24px");
        headerPanel.add(difficultyPanel);
        headerPanel.add(timerPanel);
        headerPanel.add(bombsPanel);
        headerPanel.add(settingsBtn);
        add(headerPanel);
    }

    @Override
    public void open() {
        timerPanel.open();
        difficultyPanel.open();
    }

    @Override
    public void close() {
        timerPanel.close();
        difficultyPanel.close();
    }

    @Override
    public void destroy() {
        timerPanel.destroy();
        difficultyPanel.destroy();
    }

    private static class TimerPanel extends JPanel {
        private final JLabel timerLabel;
        private final Stopwatch stopwatch;

        TimerPanel() {
            setLayout(new MigLayout("al center center, insets 0"));
            timerLabel = new JLabel("00:00.000", new SVGIconUIColor("timer.svg", 0.75f, "foreground.background"), JLabel.LEFT);
            Consumer<String> onStopwatchTick = new Consumer<String>() {
                @Override
                public void accept(String s) {
                    SwingUtilities.invokeLater(() -> {
                        timerLabel.setText(s);
                    });
                }
            };
            stopwatch = new Stopwatch(onStopwatchTick);

            add(timerLabel);
        }

        void open() {
            stopwatch.start();
        }

        void close() {
            stopwatch.stop();
        }

        void destroy() {
            stopwatch.stop();
            stopwatch.reset();
        }
    }

    private static class DifficultyPanel extends JPanel {
        final JLabel textLabel;
        final PropertyChangeListener changeListener;

        DifficultyPanel() {
            setLayout(new MigLayout("al center center, insets 0"));
            GameDifficulty gameDifficulty = UserSettings.getInstance().getCurrentDifficulty();
            textLabel = new JLabel(gameDifficulty.name(), new SVGIconUIColor(String.format("%s.svg", gameDifficulty.name().toLowerCase()), 0.75f, "foreground.background"), JLabel.LEFT);
            changeListener = new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent pc) {
                    if (!pc.getPropertyName().equals("difficulty")) {
                        return;
                    }

                    GameDifficulty gameDifficulty = (GameDifficulty) pc.getNewValue();

                    textLabel.setText(gameDifficulty.name());
                    textLabel.setIcon(new SVGIconUIColor(String.format("%s.svg", gameDifficulty.name().toLowerCase()), 0.75f, "foreground.background"));
                }
            };


            add(textLabel);
        }

        void open() {
            GameDifficulty gameDifficulty = UserSettings.getInstance().getCurrentDifficulty();
            textLabel.setText(gameDifficulty.name());
            textLabel.setIcon(new SVGIconUIColor(String.format("%s.svg", gameDifficulty.name().toLowerCase()), 0.75f, "foreground.background"));
            UserSettings.getInstance().addPropertyChangeListener(changeListener);
        }

        void close() {
            UserSettings.getInstance().removePropertyChangeListener(changeListener);
        }

        void destroy() {
            UserSettings.getInstance().removePropertyChangeListener(changeListener);
        }
    }

    private static class BombsPanel extends JPanel {
        BombsPanel() {
            setLayout(new MigLayout("al center center, insets 0"));

        }
    }
}
