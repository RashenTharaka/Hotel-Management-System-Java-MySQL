package hotel.management.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen(String username, Runnable onComplete) {
        UITheme.apply();
        setUndecorated(true);
        setSize(640, 470);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));

        JPanel root = new UITheme.RoundedPanel(34, Color.WHITE, true);
        root.setLayout(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(34, 42, 34, 42));
        setContentPane(root);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        root.add(content, BorderLayout.CENTER);

        JLabel logo = new JLabel();
        javax.swing.ImageIcon icon = UITheme.loadScaledIcon("icons/hiconRB.png", 120, 120);
        if (icon != null) {
            logo.setIcon(icon);
        }
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(logo);
        content.add(Box.createVerticalStrut(12));

        JLabel resortLabel = new JLabel("KINGFISHER RESORT", SwingConstants.CENTER);
        resortLabel.setFont(new Font("Serif", Font.BOLD, 29));
        resortLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resortLabel.setForeground(UITheme.PRIMARY_DARK);
        content.add(resortLabel);
        content.add(Box.createVerticalStrut(26));

        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(UITheme.getBaseFont(22f, Font.BOLD));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(UITheme.ACCENT.darker());
        content.add(welcomeLabel);
        content.add(Box.createVerticalStrut(18));

        JLabel greetingLabel = new JLabel(getGreeting(), SwingConstants.CENTER);
        greetingLabel.setFont(UITheme.getBaseFont(30f, Font.BOLD));
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetingLabel.setForeground(UITheme.INFO);
        content.add(greetingLabel);
        content.add(Box.createVerticalStrut(28));

        JProgressBar progress = new JProgressBar();
        progress.setIndeterminate(true);
        progress.setPreferredSize(new Dimension(360, 12));
        progress.setMaximumSize(new Dimension(360, 12));
        progress.setBorder(BorderFactory.createEmptyBorder());
        progress.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(progress);
        content.add(Box.createVerticalStrut(18));

        JLabel loadingLabel = new JLabel("Preparing your workspace...", SwingConstants.CENTER);
        loadingLabel.setFont(UITheme.getBaseFont(15f, Font.ITALIC));
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadingLabel.setForeground(UITheme.SUB_TEXT);
        content.add(loadingLabel);

        UITheme.enhanceWindow(this);
        setVisible(true);

        new javax.swing.Timer(2600, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            dispose();
            onComplete.run();
        }).start();
    }

    private String getGreeting() {
        int hour = java.time.LocalTime.now().getHour();
        if (hour >= 5 && hour < 12) return "Good Morning";
        else if (hour >= 12 && hour < 17) return "Good Afternoon";
        else if (hour >= 17 && hour < 21) return "Good Evening";
        else return "Good Night";
    }
}
