import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class LoginForm04 extends JFrame 
{
    // Declare Swing components
     JTextField usernameField;
     JPasswordField passwordField;
     JButton loginButton;

     Font font;

    public LoginForm04()
     {
        // Set up the JFrame
        setTitle("Login Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);

        Color color = new Color(0, 0, 139); // RGB for dark blue
        getContentPane().setBackground(color);


        // Initialize and add Swing components
        JLabel l1 = new JLabel("Username:");
        l1.setBounds(50, 50, 100, 30);
        l1.setForeground(color.WHITE);
        add(l1);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 150, 30);
        add(usernameField);

        JLabel l2 = new JLabel("Password:");
        l2.setForeground(color.WHITE);
        l2.setBounds(50, 100, 100, 30);
        add(l2);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 30);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);

        add(loginButton);

        // Add ActionListener to the login button
        loginButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
               if (username.equals("azan") || password.equals("123"))
              {
                System.out.println("correct");
                 new Attendance05().setVisible(true);
                 dispose();
               
                JOptionPane.showMessageDialog(null, "Login successful!");
                    // Proceed to the next screen or functionality
              } else 
                {
                   
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });
    }


    // Method to authenticate user with the database
    private boolean authenticateUser(String username, String password) 
    {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your4";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Debugging: Print connection status
            System.out.println("Connection established.");

            // Create the SQL query
            String sql = "SELECT * FROM your5 WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Debugging: Print query statement
            System.out.println("Executing query: " + preparedStatement.toString());

            // Execute the query and get the result set
            resultSet = preparedStatement.executeQuery();

            // Check if user exists in the database
            boolean userExists = resultSet.next();
            
            // Debugging: Print result status
            System.out.println("User exists: " + userExists);

            return userExists;

        }
         catch (ClassNotFoundException | SQLException e) 
         {
            e.printStackTrace();
            return false;
        } 
        finally 
        {
            // Close the resources
            try 
            {
                if (resultSet != null) 
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                 {
                    preparedStatement.close();
                }
                if (connection != null) 
                {
                    connection.close();
                }
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) 
    {

        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
           
            public void run()
             {
                new LoginForm04();
            }
        });
    }
}
