package hotel.management.system;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public final class UITheme {

    public static final Color PRIMARY = new Color(12, 74, 110);
    public static final Color PRIMARY_DARK = new Color(7, 32, 50);
    public static final Color PRIMARY_LIGHT = new Color(37, 99, 235);
    public static final Color ACCENT = new Color(20, 184, 166);
    public static final Color SUCCESS = new Color(0, 150, 89);
    public static final Color WARNING = new Color(217, 119, 6);
    public static final Color INFO = new Color(2, 132, 199);
    public static final Color UPDATE = new Color(124, 58, 237);
    public static final Color DANGER = new Color(220, 38, 38);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color DARK = new Color(30, 41, 59);
    public static final Color LIGHT_BG = new Color(244, 248, 251);
    public static final Color FIELD_BG = new Color(255, 255, 255);
    public static final Color CARD_BG = new Color(255, 255, 255, 236);
    public static final Color BORDER = new Color(213, 226, 235);
    public static final Color TEXT = new Color(15, 23, 42);
    public static final Color SUB_TEXT = new Color(71, 85, 105);

    private static boolean applied = false;

    private UITheme() {
    }

    public static void apply() {
        if (applied) {
            return;
        }

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            // Keep default look and feel if Nimbus is not available.
        }

        Font baseFont = getBaseFont(14f, Font.PLAIN);
        UIManager.put("Label.font", baseFont);
        UIManager.put("Button.font", getBaseFont(13f, Font.BOLD));
        UIManager.put("TextField.font", baseFont);
        UIManager.put("PasswordField.font", baseFont);
        UIManager.put("ComboBox.font", baseFont);
        UIManager.put("Spinner.font", baseFont);
        UIManager.put("Table.font", baseFont);
        UIManager.put("TableHeader.font", getBaseFont(13f, Font.BOLD));
        UIManager.put("OptionPane.messageFont", baseFont);
        UIManager.put("OptionPane.buttonFont", getBaseFont(13f, Font.BOLD));
        UIManager.put("control", LIGHT_BG);
        UIManager.put("nimbusBase", PRIMARY);
        UIManager.put("nimbusBlueGrey", new Color(207, 220, 229));
        UIManager.put("nimbusFocus", ACCENT);
        UIManager.put("Table.alternateRowColor", new Color(249, 252, 254));
        UIManager.put("Table.gridColor", new Color(229, 236, 242));
        UIManager.put("ComboBox.background", FIELD_BG);
        UIManager.put("ComboBox.foreground", TEXT);
        UIManager.put("Spinner.background", FIELD_BG);
        UIManager.put("Spinner.foreground", TEXT);

        AppPaths.ensureRuntimeFolders();
        applied = true;
    }

    public static Font getBaseFont(float size, int style) {
        return new Font("Segoe UI", style, Math.round(size));
    }

    public static JLabel titleLabel(String text, float size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(getBaseFont(size, Font.BOLD));
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel subtitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(getBaseFont(14f, Font.PLAIN));
        label.setForeground(SUB_TEXT);
        return label;
    }

    public static void stylePrimaryButton(JButton button) {
        styleButton(button, PRIMARY);
    }

    public static void styleSuccessButton(JButton button) {
        styleButton(button, SUCCESS);
    }

    public static void styleWarningButton(JButton button) {
        styleButton(button, WARNING);
    }

    public static void styleDangerButton(JButton button) {
        styleButton(button, DANGER);
    }

    public static void styleInfoButton(JButton button) {
        styleButton(button, INFO);
    }

    public static void styleUpdateButton(JButton button) {
        styleButton(button, UPDATE);
    }

    public static void styleMutedButton(JButton button) {
        styleButton(button, MUTED);
    }

    public static void styleDarkButton(JButton button) {
        styleButton(button, DARK);
    }

    public static void styleButton(JButton button, Color background) {
        if (button == null) {
            return;
        }
        button.putClientProperty("themeBackground", background);
        button.setUI(new RoundedButtonUI());
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(getBaseFont(13f, Font.BOLD));
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(8, 18, 8, 18));
        button.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        button.setPreferredSize(new Dimension(Math.max(128, button.getPreferredSize().width), 42));
    }

    public static void styleIconButton(JButton button) {
        if (button == null) {
            return;
        }
        button.setUI(new BasicButtonUI());
        button.setBackground(FIELD_BG);
        button.setForeground(TEXT);
        button.setFont(getBaseFont(13f, Font.PLAIN));
        button.setFocusPainted(false);
        button.setRolloverEnabled(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new RoundBorder(BORDER, 12, 1));
    }

    public static void styleButtonByText(JButton button) {
        if (button == null) {
            return;
        }
        String text = button.getText() == null ? "" : button.getText().trim().toLowerCase();
        if (text.contains("delete") || text.contains("remove") || text.contains("logout") || text.contains("cancel") || text.contains("exit") || text.equals("check out") || text.equals("checkout")) {
            styleDangerButton(button);
        } else if (text.contains("update") || text.contains("edit") || text.contains("modify")) {
            styleUpdateButton(button);
        } else if (text.contains("search") || text.contains("find") || text.equals("ok")) {
            styleInfoButton(button);
        } else if (text.contains("clear") || text.contains("reset")) {
            styleMutedButton(button);
        } else if (text.contains("back") || text.contains("close")) {
            styleDarkButton(button);
        } else if (text.contains("report") || text.contains("export") || text.contains("print") || text.contains("backup")) {
            styleWarningButton(button);
        } else if (text.contains("add") || text.contains("save") || text.contains("login") || text.contains("enter") || text.contains("check in") || text.equals("checkin")) {
            styleSuccessButton(button);
        } else {
            stylePrimaryButton(button);
        }
    }

    public static void styleTable(JTable table) {
        if (table == null) {
            return;
        }
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(232, 238, 243));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(215, 236, 248));
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);
        table.setFont(getBaseFont(13f, Font.PLAIN));
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setOpaque(false);
            header.setReorderingAllowed(false);
            header.setResizingAllowed(true);
            header.setPreferredSize(new Dimension(header.getPreferredSize().width, 38));
            header.setDefaultRenderer(new CleanTableHeaderRenderer());
            header.setBorder(BorderFactory.createEmptyBorder());
        }

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 251, 253));
                    c.setForeground(TEXT);
                }
                return c;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    public static void styleField(JComponent field) {
        if (field == null) {
            return;
        }
        field.setFont(getBaseFont(13f, Font.PLAIN));
        field.setBorder(new RoundBorder(BORDER, 14, 1));
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT);
    }

    public static void styleComboBox(final JComboBox combo) {
        if (combo == null) {
            return;
        }
        combo.setUI(new CleanComboBoxUI());
        combo.setFont(getBaseFont(13f, Font.PLAIN));
        combo.setForeground(TEXT);
        combo.setBackground(FIELD_BG);
        combo.setOpaque(false);
        combo.setBorder(new RoundBorder(BORDER, 14, 1));
        combo.setFocusable(false);
        combo.setMaximumRowCount(8);
        combo.setPreferredSize(new Dimension(Math.max(90, combo.getPreferredSize().width), 36));
        final ListCellRenderer originalRenderer = combo.getRenderer();
        combo.setRenderer(new ListCellRenderer() {
            public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c;
                if (originalRenderer != null) {
                    c = originalRenderer.getListCellRendererComponent(list, value, index, false, false);
                } else {
                    c = new JLabel(value == null ? "" : value.toString());
                }
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setText(value == null ? "" : value.toString());
                    label.setFont(getBaseFont(13f, Font.PLAIN));
                    label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    label.setOpaque(true);
                    label.setForeground(TEXT);
                    label.setBackground(isSelected && index >= 0 ? new Color(221, 238, 247) : FIELD_BG);
                }
                return c;
            }
        });
    }

    public static void styleSpinner(JSpinner spinner) {
        if (spinner == null) {
            return;
        }
        spinner.setFont(getBaseFont(13f, Font.PLAIN));
        spinner.setBorder(new RoundBorder(BORDER, 12, 1));
        spinner.setBackground(FIELD_BG);
        spinner.setForeground(TEXT);
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setFont(getBaseFont(13f, Font.PLAIN));
            tf.setForeground(TEXT);
            tf.setBackground(FIELD_BG);
            tf.setCaretColor(TEXT);
            tf.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 4));
            tf.setHorizontalAlignment(JTextField.CENTER);
        }
    }

    public static void enhanceWindow(Window window) {
        apply();
        if (window == null) {
            return;
        }
        if (window instanceof JFrame) {
            JFrame frame = (JFrame) window;
            frame.getContentPane().setBackground(LIGHT_BG);
            try {
                ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/hicon.PNG"));
                frame.setIconImage(icon.getImage());
            } catch (Exception ignored) {
            }
        }
        styleChildren(window);
    }

    public static void centerFeatureWindow(JFrame frame, int width, int height) {
        apply();
        if (frame == null) {
            return;
        }
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int safeWidth = Math.min(width, Math.max(900, screen.width - 120));
        int safeHeight = Math.min(height, Math.max(620, screen.height - 120));
        frame.setSize(safeWidth, safeHeight);
        frame.setMinimumSize(new Dimension(Math.min(900, safeWidth), Math.min(600, safeHeight)));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public static void centerWindow(Window window, int width, int height) {
        if (window == null) {
            return;
        }
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
    }

    private static void styleChildren(Container container) {
        if (container == null) {
            return;
        }
        for (Component component : container.getComponents()) {
            styleComponent(component);
            if (component instanceof Container) {
                styleChildren((Container) component);
            }
        }
    }

    private static void styleComponent(Component component) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String text = button.getText() == null ? "" : button.getText().trim();
            if (text.isEmpty() && button.getIcon() != null) {
                styleIconButton(button);
            } else {
                styleButtonByText(button);
            }
        } else if (component instanceof JTable) {
            styleTable((JTable) component);
        } else if (component instanceof JScrollPane) {
            ((JComponent) component).setBorder(new RoundBorder(BORDER, 18, 1));
            ((JComponent) component).setBackground(Color.WHITE);
        } else if (component instanceof JComboBox) {
            styleComboBox((JComboBox) component);
        } else if (component instanceof JSpinner) {
            styleSpinner((JSpinner) component);
        } else if (component instanceof JTextField || component instanceof JPasswordField) {
            styleField((JComponent) component);
        } else if (component instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) component;
            checkBox.setOpaque(false);
            checkBox.setFont(getBaseFont(13f, Font.PLAIN));
            checkBox.setForeground(TEXT);
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            if (label.getFont() != null && label.getFont().getSize() <= 13) {
                label.setFont(getBaseFont(13f, label.getFont().getStyle()));
            }
            if (label.getForeground() == null || label.getForeground().equals(Color.BLACK)) {
                label.setForeground(TEXT);
            }
        }
    }

    public static JPanel createCardPanel() {
        RoundedPanel panel = new RoundedPanel(30, CARD_BG, true);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 36, 30, 36));
        return panel;
    }

    public static JPanel createGlassPanel(int radius) {
        RoundedPanel panel = new RoundedPanel(radius, new Color(255, 255, 255, 226), true);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        return panel;
    }

    public static JPanel createImageBackgroundPanel(final String resourcePath) {
        return createImageBackgroundPanel(resourcePath, new Color(4, 12, 20, 105));
    }

    public static JPanel createImageBackgroundPanel(final String resourcePath, final Color overlay) {
        return new JPanel(new GridBagLayout()) {
            private ImageIcon icon;

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (icon == null) {
                    java.net.URL url = ClassLoader.getSystemResource(resourcePath);
                    if (url != null) {
                        icon = new ImageIcon(url);
                    }
                }
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                if (icon != null) {
                    g2.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setPaint(new Color(25, 66, 92));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.setColor(overlay);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
    }

    public static ImageIcon loadScaledIcon(String resourcePath, int width, int height) {
        try {
            java.net.URL url = ClassLoader.getSystemResource(resourcePath);
            if (url == null) {
                return null;
            }
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }

    public static JPanel horizontalDivider() {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
                g2.dispose();
            }

            public Dimension getPreferredSize() {
                return new Dimension(1, 14);
            }
        };
    }

    public static void showMessageDialog(Component parentComponent, Object message) {
        showMessageDialog(parentComponent, message, "Message", JOptionPane.INFORMATION_MESSAGE, null);
    }

    public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        showMessageDialog(parentComponent, message, title, messageType, null);
    }

    public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType, Icon icon) {
        showRoundedDialog(parentComponent, message, title, messageType, icon, new String[]{"OK"});
    }

    public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        String[] options;
        if (optionType == JOptionPane.YES_NO_CANCEL_OPTION) {
            options = new String[]{"Yes", "No", "Cancel"};
        } else if (optionType == JOptionPane.OK_CANCEL_OPTION) {
            options = new String[]{"OK", "Cancel"};
        } else {
            options = new String[]{"Yes", "No"};
        }
        return showRoundedDialog(parentComponent, message, title, JOptionPane.QUESTION_MESSAGE, null, options);
    }

    private static int showRoundedDialog(Component parentComponent, Object message, String title, int messageType, Icon customIcon, String[] options) {
        apply();
        final int[] result = new int[]{JOptionPane.CLOSED_OPTION};
        Window owner = findOwner(parentComponent);
        final JDialog dialog = owner == null ? new JDialog((JFrame) null, true) : new JDialog(owner, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        RoundedPanel root = new RoundedPanel(30, Color.WHITE, true);
        root.setLayout(new BorderLayout(0, 0));
        root.setBorder(new RoundBorder(new Color(184, 200, 211), 30, 1));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        titleBar.setBorder(BorderFactory.createEmptyBorder(18, 22, 10, 16));

        JLabel titleLabel = new JLabel(title == null || title.trim().isEmpty() ? "Message" : title);
        titleLabel.setFont(getBaseFont(16f, Font.BOLD));
        titleLabel.setForeground(TEXT);
        titleBar.add(titleLabel, BorderLayout.CENTER);

        JButton close = new JButton("×");
        close.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        close.setContentAreaFilled(false);
        close.setOpaque(false);
        close.setFocusPainted(false);
        close.setForeground(MUTED);
        close.setFont(getBaseFont(20f, Font.PLAIN));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addActionListener(e -> {
            result[0] = JOptionPane.CLOSED_OPTION;
            dialog.dispose();
        });
        titleBar.add(close, BorderLayout.EAST);
        root.add(titleBar, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(18, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(10, 26, 22, 26));

        Icon icon = customIcon != null ? customIcon : getMessageIcon(messageType);
        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setVerticalAlignment(SwingConstants.TOP);
            body.add(iconLabel, BorderLayout.WEST);
        }

        JComponent messageView = createMessageView(message);
        body.add(messageView, BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 22, 24, 22));
        for (int i = 0; i < options.length; i++) {
            final int index = i;
            JButton button = new JButton(options[i]);
            if ("No".equalsIgnoreCase(options[i]) || "Cancel".equalsIgnoreCase(options[i])) {
                styleMutedButton(button);
            } else if ("OK".equalsIgnoreCase(options[i])) {
                styleInfoButton(button);
            } else {
                stylePrimaryButton(button);
            }
            button.addActionListener((ActionEvent e) -> {
                result[0] = mapDialogResult(options[index], index);
                dialog.dispose();
            });
            buttons.add(button);
            if (i == 0) {
                dialog.getRootPane().setDefaultButton(button);
            }
        }
        root.add(buttons, BorderLayout.SOUTH);

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        root.getActionMap().put("close", new javax.swing.AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                result[0] = JOptionPane.CLOSED_OPTION;
                dialog.dispose();
            }
        });

        dialog.setContentPane(root);
        dialog.pack();
        Dimension size = dialog.getSize();
        dialog.setSize(Math.max(470, size.width), Math.max(170, size.height));
        dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 30, 30));
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
        return result[0];
    }

    private static int mapDialogResult(String option, int index) {
        if ("Yes".equalsIgnoreCase(option)) {
            return JOptionPane.YES_OPTION;
        }
        if ("No".equalsIgnoreCase(option)) {
            return JOptionPane.NO_OPTION;
        }
        if ("Cancel".equalsIgnoreCase(option)) {
            return JOptionPane.CANCEL_OPTION;
        }
        if ("OK".equalsIgnoreCase(option)) {
            return JOptionPane.OK_OPTION;
        }
        return index;
    }

    private static Window findOwner(Component parentComponent) {
        if (parentComponent != null) {
            Window window = SwingUtilities.getWindowAncestor(parentComponent);
            if (window != null) {
                return window;
            }
            if (parentComponent instanceof Window) {
                return (Window) parentComponent;
            }
        }
        Window active = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        if (active != null) {
            return active;
        }
        Window[] windows = Window.getWindows();
        for (int i = windows.length - 1; i >= 0; i--) {
            if (windows[i].isShowing()) {
                return windows[i];
            }
        }
        return null;
    }

    private static JComponent createMessageView(Object message) {
        if (message instanceof Icon) {
            return new JLabel((Icon) message);
        }
        if (message instanceof Component) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);
            panel.add((Component) message, BorderLayout.CENTER);
            return panel;
        }
        JTextArea area = new JTextArea(message == null ? "" : String.valueOf(message));
        area.setEditable(false);
        area.setFocusable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setFont(getBaseFont(13f, Font.PLAIN));
        area.setForeground(TEXT);
        area.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        area.setColumns(44);
        return area;
    }

    private static Icon getMessageIcon(int type) {
        String key;
        if (type == JOptionPane.ERROR_MESSAGE) {
            key = "OptionPane.errorIcon";
        } else if (type == JOptionPane.WARNING_MESSAGE) {
            key = "OptionPane.warningIcon";
        } else if (type == JOptionPane.QUESTION_MESSAGE) {
            key = "OptionPane.questionIcon";
        } else {
            key = "OptionPane.informationIcon";
        }
        return UIManager.getIcon(key);
    }

    private static class RoundedButtonUI extends BasicButtonUI {
        public void installUI(JComponent c) {
            super.installUI(c);
            c.setOpaque(false);
        }

        public void paint(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color base = button.getBackground();
            Object bg = button.getClientProperty("themeBackground");
            if (bg instanceof Color) {
                base = (Color) bg;
            }
            if (!button.isEnabled()) {
                base = MUTED;
            } else if (model.isPressed()) {
                base = base.darker();
            } else if (model.isRollover()) {
                base = brighten(base, 1.08f);
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int arc = 18;
            int w = c.getWidth();
            int h = c.getHeight();
            g2.setColor(new Color(0, 0, 0, 22));
            g2.fillRoundRect(2, 4, w - 4, h - 5, arc, arc);
            g2.setPaint(new GradientPaint(0, 0, brighten(base, 1.04f), 0, h, base));
            g2.fillRoundRect(0, 0, w - 3, h - 5, arc, arc);
            g2.setColor(new Color(255, 255, 255, 42));
            g2.drawRoundRect(0, 0, w - 4, h - 6, arc, arc);
            g2.dispose();
            super.paint(g, c);
        }
    }

    private static Color brighten(Color color, float factor) {
        int r = Math.min(255, Math.round(color.getRed() * factor));
        int g = Math.min(255, Math.round(color.getGreen() * factor));
        int b = Math.min(255, Math.round(color.getBlue() * factor));
        return new Color(r, g, b);
    }

    private static class CleanComboBoxUI extends BasicComboBoxUI {
        private static final int ARROW_WIDTH = 34;

        protected JButton createArrowButton() {
            JButton button = new JButton("▾");
            button.setFont(getBaseFont(13f, Font.BOLD));
            button.setForeground(SUB_TEXT);
            button.setBackground(FIELD_BG);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            button.setPreferredSize(new Dimension(ARROW_WIDTH, 34));
            return button;
        }

        protected java.awt.LayoutManager createLayoutManager() {
            return new ComboBoxLayoutManager() {
                public void layoutContainer(Container parent) {
                    if (comboBox == null) {
                        return;
                    }
                    int width = comboBox.getWidth();
                    int height = comboBox.getHeight();
                    if (arrowButton != null) {
                        arrowButton.setBounds(width - ARROW_WIDTH - 2, 1, ARROW_WIDTH, Math.max(0, height - 2));
                    }
                    if (editor != null) {
                        editor.setBounds(10, 2, Math.max(0, width - ARROW_WIDTH - 14), Math.max(0, height - 4));
                    }
                }
            };
        }

        protected void installDefaults() {
            super.installDefaults();
            comboBox.setOpaque(false);
            comboBox.setBackground(FIELD_BG);
            comboBox.setForeground(TEXT);
            comboBox.setFocusable(false);
            comboBox.setRequestFocusEnabled(false);
            comboBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, ARROW_WIDTH));
            if (listBox != null) {
                listBox.setSelectionBackground(new Color(235, 245, 251));
                listBox.setSelectionForeground(TEXT);
                listBox.setBackground(FIELD_BG);
                listBox.setForeground(TEXT);
                listBox.setFixedCellHeight(30);
            }
        }

        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = c.getWidth();
            int height = c.getHeight();

            g2.setColor(FIELD_BG);
            g2.fillRoundRect(0, 0, width - 1, height - 1, 14, 14);
            g2.setColor(BORDER);
            g2.drawRoundRect(0, 0, width - 1, height - 1, 14, 14);

            Object value = comboBox == null ? null : comboBox.getSelectedItem();
            String text = value == null ? "" : value.toString();
            g2.setFont(comboBox == null ? getBaseFont(13f, Font.PLAIN) : comboBox.getFont());
            g2.setColor(TEXT);
            java.awt.FontMetrics metrics = g2.getFontMetrics();
            int textY = (height - metrics.getHeight()) / 2 + metrics.getAscent();
            int maxTextWidth = Math.max(0, width - ARROW_WIDTH - 18);
            if (metrics.stringWidth(text) > maxTextWidth) {
                while (text.length() > 0 && metrics.stringWidth(text + "...") > maxTextWidth) {
                    text = text.substring(0, text.length() - 1);
                }
                text = text + "...";
            }
            g2.drawString(text, 10, textY);

            g2.setColor(new Color(226, 236, 243));
            g2.drawLine(width - ARROW_WIDTH - 3, 7, width - ARROW_WIDTH - 3, height - 8);
            g2.dispose();
        }

        public void paintCurrentValueBackground(Graphics g, java.awt.Rectangle bounds, boolean hasFocus) {
            // The full combo box is painted manually in paint() to prevent the Nimbus blue selection strip.
        }
    }

    private static class CleanTableHeaderRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
        CleanTableHeaderRenderer() {
            setHorizontalAlignment(SwingConstants.LEFT);
            setOpaque(true);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(35, 111, 148)),
                    BorderFactory.createEmptyBorder(0, 9, 0, 9)));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setText(value == null ? "" : value.toString());
            label.setFont(getBaseFont(13f, Font.BOLD));
            label.setForeground(Color.WHITE);
            label.setBackground(PRIMARY);
            label.setOpaque(true);
            return label;
        }
    }

    public static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color background;
        private final boolean shadow;

        public RoundedPanel(int radius, Color background) {
            this(radius, background, false);
        }

        public RoundedPanel(int radius, Color background, boolean shadow) {
            this.radius = radius;
            this.background = background;
            this.shadow = shadow;
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (shadow) {
                g2.setColor(new Color(0, 0, 0, 25));
                g2.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 10, radius, radius);
            }
            g2.setColor(background);
            g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 6, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class RoundBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        private final int thickness;

        public RoundBorder(Color color, int radius, int thickness) {
            this.color = color;
            this.radius = radius;
            this.thickness = thickness;
        }

        public java.awt.Insets getBorderInsets(Component c) {
            return new java.awt.Insets(8, 12, 8, 12);
        }

        public java.awt.Insets getBorderInsets(Component c, java.awt.Insets insets) {
            insets.left = insets.right = 12;
            insets.top = insets.bottom = 8;
            return insets;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            for (int i = 0; i < thickness; i++) {
                g2.drawRoundRect(x + i, y + i, width - (i * 2) - 1, height - (i * 2) - 1, radius, radius);
            }
            g2.dispose();
        }
    }
}
