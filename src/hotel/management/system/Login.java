package hotel.management.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login extends JFrame implements ActionListener {

    JTextField nic;
    JPasswordField password;
    JButton login, cancel;
    JCheckBox showPassword;

    Login() {
        UITheme.apply();
        setTitle("Kingfisher Resort - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel background = UITheme.createImageBackgroundPanel("icons/hotel2blur.jpg", new Color(3, 12, 20, 118));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(28, 42, 10, 42));
        background.add(topBar, BorderLayout.NORTH);

        JLabel brand = new JLabel("Kingfisher Resort Management System");
        brand.setForeground(Color.WHITE);
        brand.setFont(UITheme.getBaseFont(28f, Font.BOLD));
        topBar.add(brand, BorderLayout.WEST);

        JButton close = new JButton("Exit");
        close.setPreferredSize(new Dimension(95, 38));
        UITheme.styleDarkButton(close);
        close.addActionListener(e -> confirmExit());
        topBar.add(close, BorderLayout.EAST);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        background.add(center, BorderLayout.CENTER);

        JPanel loginCard = UITheme.createGlassPanel(34);
        loginCard.setLayout(new BorderLayout(0, 24));
        loginCard.setPreferredSize(new Dimension(520, 560));
        center.add(loginCard, new GridBagConstraints());

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel();
        javax.swing.ImageIcon scaledLogo = UITheme.loadScaledIcon("icons/hiconRB.png", 115, 115);
        if (scaledLogo != null) {
            logo.setIcon(scaledLogo);
        }
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(logo);

        JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(UITheme.getBaseFont(31f, Font.BOLD));
        title.setForeground(UITheme.TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        header.add(title);

        JLabel subtitle = new JLabel("Login to continue to your hotel dashboard", SwingConstants.CENTER);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(UITheme.getBaseFont(14f, Font.PLAIN));
        subtitle.setForeground(UITheme.SUB_TEXT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        header.add(subtitle);
        loginCard.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        loginCard.add(form, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 0, 9, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel nicLabel = fieldLabel("NIC Number");
        gbc.gridy = 0;
        form.add(nicLabel, gbc);

        nic = new JTextField();
        nic.setPreferredSize(new Dimension(360, 44));
        nic.putClientProperty("JTextField.placeholderText", "Enter NIC number");
        gbc.gridy = 1;
        form.add(nic, gbc);

        JLabel passLabel = fieldLabel("Password");
        gbc.gridy = 2;
        gbc.insets = new Insets(18, 0, 9, 0);
        form.add(passLabel, gbc);

        password = new JPasswordField();
        password.setPreferredSize(new Dimension(360, 44));
        gbc.gridy = 3;
        gbc.insets = new Insets(9, 0, 9, 0);
        form.add(password, gbc);

        showPassword = new JCheckBox("Show password");
        showPassword.setOpaque(false);
        showPassword.setForeground(UITheme.SUB_TEXT);
        showPassword.setFont(UITheme.getBaseFont(13f, Font.PLAIN));
        showPassword.addActionListener(e -> password.setEchoChar(showPassword.isSelected() ? (char) 0 : '•'));
        gbc.gridy = 4;
        gbc.insets = new Insets(2, 0, 12, 0);
        form.add(showPassword, gbc);

        JPanel buttonRow = new JPanel(new GridBagLayout());
        buttonRow.setOpaque(false);
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(0, 0, 0, 10);
        b.fill = GridBagConstraints.HORIZONTAL;
        b.weightx = 1;

        login = new JButton("Login");
        login.setPreferredSize(new Dimension(170, 44));
        UITheme.styleSuccessButton(login);
        login.addActionListener(this);
        buttonRow.add(login, b);

        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(170, 44));
        UITheme.styleDangerButton(cancel);
        cancel.addActionListener(this);
        b.gridx = 1;
        b.insets = new Insets(0, 10, 0, 0);
        buttonRow.add(cancel, b);

        gbc.gridy = 5;
        gbc.insets = new Insets(14, 0, 6, 0);
        form.add(buttonRow, gbc);

        // Demo credentials removed from the login page for a cleaner production UI.

        KeyAdapter enterListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    confirmExit();
                }
            }
        };
        nic.addKeyListener(enterListener);
        password.addKeyListener(enterListener);

        UITheme.enhanceWindow(this);
        setVisible(true);
    }

    private JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.getBaseFont(14f, Font.BOLD));
        label.setForeground(UITheme.TEXT);
        return label;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            attemptLogin();
        } else if (ae.getSource() == cancel) {
            confirmExit();
        }
    }

    private void attemptLogin() {
        String unic = nic.getText().trim();
        String upass = new String(password.getPassword()).trim();

        if (!isValidNIC(unic)) {
            UITheme.showMessageDialog(this, "Invalid NIC. Use a 10-character NIC such as 871234567V or a 12-digit NIC.", "Invalid NIC", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (upass.isEmpty()) {
            UITheme.showMessageDialog(this, "Password cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Conn c = new Conn();
            String query = "SELECT name, job FROM employee WHERE nic = ? AND password = ?";
            PreparedStatement stmt = c.c.prepareStatement(query);
            stmt.setString(1, unic);
            stmt.setString(2, upass);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("name") == null ? "User" : rs.getString("name");
                String jobRole = rs.getString("job") == null ? "" : rs.getString("job");

                if (!jobRole.equalsIgnoreCase("Admin") && !jobRole.equalsIgnoreCase("Receptionist")) {
                    UITheme.showMessageDialog(this, "Only Admin and Receptionist users can access the system.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Session.loggedNIC = unic;
                Session.loggedName = userName;
                Session.loggedRole = jobRole;
                ActivityLogger.log(unic, userName, jobRole, "Login", "Success");

                String finalUserName = userName;
                String finalJobRole = jobRole;
                new WelcomeScreen(finalUserName, () -> {
                    if (finalJobRole.equalsIgnoreCase("Admin")) {
                        new AdminDashboard();
                    } else {
                        new ReceptionDashboard();
                    }
                    setVisible(false);
                    dispose();
                });
            } else {
                UITheme.showMessageDialog(this, "Invalid NIC or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                ActivityLogger.log(unic, "Unknown", "Unknown", "Login", "Failed - Invalid credentials");
            }
            c.close();
        } catch (Exception e) {
            UITheme.showMessageDialog(this, "Unable to login. Please check the database connection and credentials.", "Database Error", JOptionPane.ERROR_MESSAGE);
            ActivityLogger.log(unic, "Unknown", "Unknown", "Login", "Failed - Database error");
            e.printStackTrace();
        }
    }

    private void confirmExit() {
        int response = UITheme.showConfirmDialog(this, "Are you sure you want to exit the system?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    private boolean isValidNIC(String nicText) {
        if (nicText == null) {
            return false;
        }
        if (nicText.length() == 10) {
            return nicText.matches("\\d{9}[vVxX]");
        } else if (nicText.length() == 12) {
            return nicText.matches("\\d{12}");
        }
        return false;
    }

    public static void main(String[] args) {
        new Login();
    }
}
