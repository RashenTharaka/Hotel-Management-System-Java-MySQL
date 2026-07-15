package hotel.management.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HotelManagementSystem extends JFrame implements ActionListener {

    private JButton enterBtn;
    private JButton exitBtn;

    HotelManagementSystem() {
        UITheme.apply();
        setTitle("Kingfisher Resort Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel background = UITheme.createImageBackgroundPanel("icons/hotel2.jpg", new Color(2, 8, 18, 90));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(28, 42, 16, 42));
        background.add(topBar, BorderLayout.NORTH);

        JLabel topBrand = new JLabel("Kingfisher Resort");
        topBrand.setForeground(Color.WHITE);
        topBrand.setFont(UITheme.getBaseFont(24f, Font.BOLD));
        topBar.add(topBrand, BorderLayout.WEST);

        exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(new Dimension(90, 38));
        UITheme.styleDarkButton(exitBtn);
        exitBtn.addActionListener(e -> System.exit(0));
        topBar.add(exitBtn, BorderLayout.EAST);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        background.add(center, BorderLayout.CENTER);

        JPanel hero = UITheme.createGlassPanel(36);
        hero.setPreferredSize(new Dimension(760, 500));
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        center.add(hero, new GridBagConstraints());

        JLabel logo = new JLabel();
        javax.swing.ImageIcon logoIcon = UITheme.loadScaledIcon("icons/hiconRB.png", 130, 130);
        if (logoIcon != null) {
            logo.setIcon(logoIcon);
        }
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        hero.add(logo);
        hero.add(Box.createVerticalStrut(8));

        JLabel title = new JLabel("KINGFISHER RESORT", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(UITheme.TEXT);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        hero.add(title);

        JLabel subtitle = new JLabel("Hotel & Resort Management System", SwingConstants.CENTER);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(UITheme.PRIMARY);
        subtitle.setFont(UITheme.getBaseFont(22f, Font.BOLD));
        subtitle.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        hero.add(subtitle);

        JLabel description = new JLabel("Manage customers, rooms, events, inventory and daily operations in one place.", SwingConstants.CENTER);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setForeground(UITheme.SUB_TEXT);
        description.setFont(UITheme.getBaseFont(15f, Font.PLAIN));
        description.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        hero.add(description);

        JPanel stats = new JPanel(new GridBagLayout());
        stats.setOpaque(false);
        stats.setBorder(BorderFactory.createEmptyBorder(34, 0, 26, 0));
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(0, 10, 0, 10);
        stats.add(smallFeature("Rooms"), s);
        s.gridx = 1;
        stats.add(smallFeature("Customers"), s);
        s.gridx = 2;
        stats.add(smallFeature("Inventory"), s);
        hero.add(stats);

        enterBtn = new JButton("Enter to System");
        enterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterBtn.setPreferredSize(new Dimension(260, 50));
        enterBtn.setMaximumSize(new Dimension(260, 50));
        UITheme.styleSuccessButton(enterBtn);
        enterBtn.addActionListener(this);
        hero.add(enterBtn);

        UITheme.enhanceWindow(this);
        setVisible(true);
    }

    private JPanel smallFeature(String text) {
        JPanel panel = new UITheme.RoundedPanel(18, new Color(241, 248, 252, 235), false);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(UITheme.PRIMARY_DARK);
        label.setFont(UITheme.getBaseFont(13f, Font.BOLD));
        panel.add(label);
        return panel;
    }

    public void actionPerformed(ActionEvent ae) {
        new Login();
        setVisible(false);
        dispose();
    }

    public static void main(String[] args) {
        new HotelManagementSystem();
    }
}
