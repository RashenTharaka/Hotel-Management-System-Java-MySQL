package hotel.management.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class AdminDashboard extends JFrame implements ActionListener {

    JButton btnNewCustomer, btnLogout, btnbackup, btnempMan, btninventory, btnEventManage, btncheckin, btncheckout, btnAddRoom, btnOrder, btnactlog;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    AdminDashboard() {
        UITheme.apply();
        setTitle("Kingfisher Resort - Admin Dashboard");
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
        title.setFont(UITheme.getBaseFont(30f, Font.BOLD));
        topBar.add(title, BorderLayout.WEST);

        JLabel userInfo = new JLabel("Admin: " + safe(Session.loggedName), SwingConstants.RIGHT);
        userInfo.setForeground(Color.WHITE);
        userInfo.setFont(UITheme.getBaseFont(15f, Font.BOLD));
        topBar.add(userInfo, BorderLayout.EAST);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        background.add(centerWrapper, BorderLayout.CENTER);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BorderLayout(0, 22));
        card.setPreferredSize(new Dimension(760, 620));
        centerWrapper.add(card, new GridBagConstraints());

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel logo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/hiconRB.png"));
            logo.setIcon(new ImageIcon(icon.getImage().getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH)));
        } catch (Exception ignored) {
        }
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(logo, BorderLayout.NORTH);

        JLabel subtitle = UITheme.titleLabel("Admin Dashboard", 24f);
        subtitle.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        header.add(subtitle, BorderLayout.CENTER);
        card.add(header, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(5, 2, 18, 16));
        menu.setOpaque(false);
        card.add(menu, BorderLayout.CENTER);

        btnempMan = createButton("Employee Management");
        btnNewCustomer = createButton("Customer Management");
        btnEventManage = createButton("Event Management");
        btnAddRoom = createButton("Room Management");
        btnOrder = createButton("Order Management");
        btninventory = createButton("Inventory Management");
        btncheckin = createButton("Check In");
        btncheckout = createButton("Check Out");
        btnactlog = createButton("Activity Log");
        btnbackup = createButton("Backup Database");

        menu.add(btnempMan);
        menu.add(btnNewCustomer);
        menu.add(btnEventManage);
        menu.add(btnAddRoom);
        menu.add(btnOrder);
        menu.add(btninventory);
        menu.add(btncheckin);
        menu.add(btncheckout);
        menu.add(btnactlog);
        menu.add(btnbackup);

        JPanel footer = new JPanel(new BorderLayout(14, 0));
        footer.setOpaque(false);
        card.add(footer, BorderLayout.SOUTH);

        JLabel clock = new JLabel("", SwingConstants.LEFT);
        clock.setFont(UITheme.getBaseFont(14f, Font.BOLD));
        clock.setForeground(UITheme.PRIMARY_DARK);
        footer.add(clock, BorderLayout.CENTER);

        btnLogout = new JButton("Logout");
        UITheme.styleDangerButton(btnLogout);
        btnLogout.setPreferredSize(new Dimension(170, 42));
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

        scheduler.scheduleAtFixedRate(() -> performAutoBackup(), 10, 1440, TimeUnit.MINUTES);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> scheduler.shutdown()));
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

    private void performAutoBackup() {
        boolean sqlSuccess = BackupDB.backupSQL();
        boolean csvSuccess = BackupDB.backupCSV();

        if (sqlSuccess && csvSuccess) {
            System.out.println("Auto-backup completed successfully.");
            ActivityLogger.log(Session.loggedNIC, Session.loggedName, Session.loggedRole, "Auto-backup database", "Success");
        } else {
            System.out.println("Auto-backup failed. Check logs for details.");
            ActivityLogger.log(Session.loggedNIC, Session.loggedName, Session.loggedRole, "Auto-backup database", "Failed");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnLogout) {
            int response = UITheme.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                ActivityLogger.log(Session.loggedNIC, Session.loggedName, Session.loggedRole, "Logged out", "Success");
                Session.clear();
                scheduler.shutdownNow();
                setVisible(false);
                dispose();
                new Login();
            }
        } else if (ae.getSource() == btnempMan) {
            new AddEmployee();
        } else if (ae.getSource() == btninventory) {
            new ManageInventory();
        } else if (ae.getSource() == btnNewCustomer) {
            new ManageCustomer();
        } else if (ae.getSource() == btnEventManage) {
            new AddEvent();
        } else if (ae.getSource() == btnactlog) {
            new activityLog();
        } else if (ae.getSource() == btnOrder) {
            new OrderInventory();
        } else if (ae.getSource() == btnbackup) {
            boolean sqlSuccess = BackupDB.backupSQL();
            boolean csvSuccess = BackupDB.backupCSV();
            if (sqlSuccess && csvSuccess) {
                UITheme.showMessageDialog(this, "Backup completed successfully.");
                ActivityLogger.log(Session.loggedNIC, Session.loggedName, Session.loggedRole, "Attempted to backup database", "Success");
            } else {
                UITheme.showMessageDialog(this, "Backup failed. Please check console for errors.", "Backup Failed", JOptionPane.ERROR_MESSAGE);
                ActivityLogger.log(Session.loggedNIC, Session.loggedName, Session.loggedRole, "Attempted to backup database", "Failed");
            }
        } else if (ae.getSource() == btncheckin) {
            new checkIn().setVisible(true);
        } else if (ae.getSource() == btncheckout) {
            new checkOut().setVisible(true);
        } else if (ae.getSource() == btnAddRoom) {
            new SearchRoom();
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
