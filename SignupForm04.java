import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class SignupForm04 extends JFrame {
    JTextField nameField;
     JComboBox<String> educationComboBox;
     JComboBox<String> ageComboBox;
     JRadioButton maleRadioButton;
    JRadioButton femaleRadioButton;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton submitButton;
    JButton loginButton;
    Color color;
    Font font;

    public SignupForm04() 
    {
        setTitle("Signup Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        dispose();
        
        Color darkBlue = new Color(0, 0, 139); // RGB for dark blue
        getContentPane().setBackground(darkBlue);

        
        // Title
        JLabel l3 = new JLabel("Signup Form");
        l3.setFont(new Font("Arial", Font.BOLD, 24));
        l3.setForeground(color.WHITE);
        l3.setBounds(120, 20, 200, 30);
        add(l3);

        // Underline
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 60, 300, 10);
        add(separator);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(color.WHITE);
        nameLabel.setBounds(50, 80, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 80, 200, 30);
        add(nameField);

        // Age
       JLabel ageLabel = new JLabel("Age:");
       ageLabel.setForeground(color.WHITE);
        ageLabel.setBounds(50, 140, 100, 30);
        add(ageLabel);

        String[] ages = {"18", "19", "20"};
        ageComboBox = new JComboBox<>(ages);
        ageComboBox.setBounds(150, 140, 200, 30);
        add(ageComboBox);

        // Education
        JLabel educationLabel = new JLabel("Education:");
        educationLabel.setForeground(color.WHITE);
        educationLabel.setBounds(50, 240, 100, 30);
        add(educationLabel);

        String[] educationLevels = {"High School", "Associate's Degree", "Bachelor's Degree", "Master's Degree", "PhD"};
        educationComboBox = new JComboBox<>(educationLevels);
        educationComboBox.setBounds(150, 240, 200, 30);
        add(educationComboBox);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 280, 100, 30);
        genderLabel.setForeground(color.WHITE);
        add(genderLabel);

        JPanel genderPanel = new JPanel();
        genderPanel.setBounds(150, 280, 200, 30);
        genderPanel.setLayout(new FlowLayout());

        ButtonGroup genderGroup = new ButtonGroup();
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");

        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);

        add(genderPanel);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 320, 100, 30);
        usernameLabel.setForeground(color.WHITE);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 320, 200, 30);

        add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(color.WHITE);
        passwordLabel.setBounds(50, 360, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 360, 200, 30);
        add(passwordField);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(50, 420, 150, 30);
        add(submitButton);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(220, 420, 150, 30);
        add(loginButton);

        // Action Listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String age = (String) ageComboBox.getSelectedItem();
                String education = (String) educationComboBox.getSelectedItem();
                String gender = maleRadioButton.isSelected() ? "Male" : "Female";
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("azan") && password.equals("123")) {
                    JOptionPane.showMessageDialog(null, "Username and Password will Successfully");
                } else {
                    if (saveToDatabase(name, age, education, gender, username, password)) {
                        JOptionPane.showMessageDialog(null, "Signup not  successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Signup failed.");
                    }
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open login form or action to be defined
                new LoginForm04();
                dispose();
                
                JOptionPane.showMessageDialog(null, "Login Form is Successfully.");
            }
        });
    }

    private boolean saveToDatabase(String name, String age, String education, String gender, String username, String password) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your10";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "INSERT INTO your11 (name, age, education, gender, username, password) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, age);
            preparedStatement.setString(3, education);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, username);
            preparedStatement.setString(6, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignupForm04().setVisible(true));
    }
}
