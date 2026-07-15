package hotel.management.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class ReceptionDashboard extends JFrame implements ActionListener {

    JButton btnNewCustomer, btnLogout, btnEventManage, btncheckin, btncheckout, btnAddRoom;

    ReceptionDashboard() {
        UITheme.apply();
        setTitle("Kingfisher Resort - Reception Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel background = UITheme.createImageBackgroundPanel("icons/hotel2blur.jpg");
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(24, 42, 12, 42));
        background.add(topBar, BorderLayout.NORTH);

        JLabel title = new JLabel("Kingfisher Resort Management System");
        title.setForeground(Color.WHITE);
        title.setFont(UITheme.getBaseFont(30f, java.awt.Font.BOLD));
        topBar.add(title, BorderLayout.WEST);

        JLabel userInfo = new JLabel("Reception: " + safe(Session.loggedName), SwingConstants.RIGHT);
        userInfo.setForeground(Color.WHITE);
        userInfo.setFont(UITheme.getBaseFont(15f, java.awt.Font.BOLD));
        topBar.add(userInfo, BorderLayout.EAST);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        background.add(centerWrapper, BorderLayout.CENTER);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BorderLayout(0, 22));
        card.setPreferredSize(new Dimension(520, 610));
        centerWrapper.add(card, new GridBagConstraints());

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel logo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/hiconRB.png"));
            logo.setIcon(new ImageIcon(icon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH)));
        } catch (Exception ignored) {
        }
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(logo, BorderLayout.NORTH);

        JLabel subtitle = UITheme.titleLabel("Reception Dashboard", 24f);
        subtitle.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        header.add(subtitle, BorderLayout.CENTER);
        card.add(header, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(5, 1, 0, 16));
        menu.setOpaque(false);
        card.add(menu, BorderLayout.CENTER);

        btnEventManage = createButton("Event Management");
        btnNewCustomer = createButton("Customer Management");
        btnAddRoom = createButton("Room Management");
        btncheckin = createButton("Check In");
        btncheckout = createButton("Check Out");

        menu.add(btnEventManage);
        menu.add(btnNewCustomer);
        menu.add(btnAddRoom);
        menu.add(btncheckin);
        menu.add(btncheckout);

        JPanel footer = new JPanel(new BorderLayout(14, 0));
        footer.setOpaque(false);
        card.add(footer, BorderLayout.SOUTH);

        JLabel clock = new JLabel("", SwingConstants.LEFT);
        clock.setFont(UITheme.getBaseFont(14f, java.awt.Font.BOLD));
        clock.setForeground(UITheme.PRIMARY_DARK);
        footer.add(clock, BorderLayout.CENTER);

        btnLogout = new JButton("Logout");
        UITheme.styleDangerButton(btnLogout);
        btnLogout.setPreferredSize(new Dimension(150, 42));
        btnLogout.addActionListener(this);
        footer.add(btnLogout, BorderLayout.EAST);

        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            clock.setText(now.format(DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd   HH:mm:ss")));
        });
        timer.setInitialDelay(0);
        timer.start();

        UITheme.enhanceWindow(this);
        setVisible(true);
        showTodayReminder();
    }

    private String safe(String text) {
        return text == null || text.trim().isEmpty() ? "User" : text;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 48));
        UITheme.stylePrimaryButton(button);
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnLogout) {
            int response = UITheme.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                ActivityLogger.log(Session.loggedNIC, Session.loggedName, Session.loggedRole, "Logged out", "Success");
                Session.clear();
                setVisible(false);
                dispose();
                new Login();
            }
        } else if (ae.getSource() == btnNewCustomer) {
            new ManageCustomer();
        } else if (ae.getSource() == btnEventManage) {
            new AddEvent();
        } else if (ae.getSource() == btncheckin) {
            new checkIn().setVisible(true);
        } else if (ae.getSource() == btncheckout) {
            new checkOut().setVisible(true);
        } else if (ae.getSource() == btnAddRoom) {
            new SearchRoom();
        }
    }

    private void showTodayReminder() {
        try {
            Conn conn = new Conn();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today = dateFormat.format(new java.util.Date());

            String query = "SELECT * FROM event WHERE date = ?";
            PreparedStatement stmt = conn.c.prepareStatement(query);
            stmt.setString(1, today);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String eventNum = rs.getString("eventnumber");
                String desc = rs.getString("description");
                String eventTimeStr = rs.getString("time");
                String duration = rs.getString("duration");

                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                LocalTime eventTime = LocalTime.parse(eventTimeStr, timeFormatter);
                LocalTime currentTime = LocalTime.now();

                if (currentTime.isBefore(eventTime)) {
                    UITheme.showMessageDialog(this,
                            "You have an event today!\n\nEvent No: " + eventNum
                            + "\nDescription: " + desc
                            + "\nTime: " + eventTimeStr
                            + "\nDuration: " + duration,
                            "Today's Event Reminder",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ReceptionDashboard();
    }
}
