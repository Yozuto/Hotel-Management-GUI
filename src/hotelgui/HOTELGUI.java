package hotelgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class HOTELGUI {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private JFrame loginFrame;

    public HOTELGUI() {
        initLoginScreen();
    }

    private void initLoginScreen() {
        loginFrame = new JFrame("Hotel Management System - Login");
        loginFrame.setSize(800, 600);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        // Create a background panel with image
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setOpaque(false);
        loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        // Title Panel
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Set anti-aliasing for smoother edges
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw a rounded white rectangle
                g2d.setColor(Color.PINK);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        titlePanel.setOpaque(false); // Ensure background is transparent except for the rectangle
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBorder(new EmptyBorder(8, 8, 8, 8)); // Padding inside the panel

        // Title Label
        JLabel titleLabel = new JLabel("Hotel Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLACK); // Text color stands out on white
        titlePanel.add(titleLabel); // Add label to the panel

        // Add title panel to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titlePanel, gbc);


        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        JTextField usernameField = new JTextField(20);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = new JPasswordField(20);

        gbc.gridy = 2;
        gbc.gridx = 0;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Error Label
        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        loginPanel.add(errorLabel, gbc);

        // Login Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
                    // Successfully logged in
                    openMainHotelSystem();
                } else {
                    errorLabel.setText("Invalid username or password");
                }
            }
        });

        // Add login panel to background
        backgroundPanel.add(loginPanel, gbc);

        // Set content pane
        loginFrame.setContentPane(backgroundPanel);

        // Make the frame visible
        loginFrame.setVisible(true);
    }

    private void openMainHotelSystem() {
        // Close login frame
        loginFrame.dispose();

        // Create and display the hotel management system
        HotelManagementSystem hotelSystem = new HotelManagementSystem();
        hotelSystem.setVisible(true);
    }

    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Set a cross-platform look and feel
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Create and display the login screen
            new HOTELGUI();
        });
    }

    // Custom Background Panel
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                // Load background image
                backgroundImage = new ImageIcon(getClass().getResource(
                        "hotelbg.jpg"
                )).getImage();
            } catch (Exception e) {
                // Fallback to a default color if image can't be loaded
                setBackground(new Color(70, 130, 180));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Scale image to fill the entire panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Mock HotelManagementSystem class
   
}
