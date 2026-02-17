package flip_n_match.ui.pages;

import flip_n_match.lib.Stopwatch;
import flip_n_match.ui.icons.SVGIconUIColor;
import flip_n_match.ui.system.Page;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.function.Consumer;

public class PageGame extends Page {
    private JPanel headerPanel;
    private TimerPanel timerPanel;

    public PageGame() {
        headerPanel = new JPanel(new MigLayout("flowx"));
        timerPanel = new TimerPanel();

        setLayout(new MigLayout(""));

        headerPanel.add(timerPanel);
        add(headerPanel);
    }

    @Override
    public void open() {
        timerPanel.open();
    }

    @Override
    public void close() {
        timerPanel.close();
    }

    @Override
    public void destroy() {
        timerPanel.destroy();
    }

    private static class TimerPanel extends JPanel {
        private JLabel timerLabel;
        private Stopwatch stopwatch;

        private Consumer<String> onStopwatchTick = new Consumer<String>() {
            @Override
            public void accept(String s) {
                SwingUtilities.invokeLater(() -> {
                    timerLabel.setText(s);
                });
            }
        };

        TimerPanel() {
            timerLabel = new JLabel("00:00.000", new SVGIconUIColor("timer.svg", 1, "foreground.background"), JLabel.LEFT);
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
}
