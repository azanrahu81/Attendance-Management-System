import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class Attendance04 extends JFrame {
    // Declare Swing components
    JTextField idField;
    JTextField nameField;
    JTextField courseField;
    JButton submitButton,clear1, exit;

    Color color;
    Font font;

    public Attendance04() {
        // Set up the JFrame

        Color darkBlue = new Color(0, 0, 139); // RGB for dark blue
        getContentPane().setBackground(darkBlue);

         
        setTitle("Attendance Form");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
      

        JLabel l1=new JLabel("Add Attendance Form");
        l1.setBounds(200,10,300,30);
        l1.setFont(new Font("Serif", Font.BOLD,25));

        l1.setForeground(color.WHITE);
        add(l1);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 60, 700, 30);
        add(separator);


        // Initialize and add Swing components
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(130, 90, 100, 30);
        idLabel.setForeground(color.WHITE);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(190, 90, 300, 30);
        idLabel.setForeground(color.WHITE);
        add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(130, 140, 100, 30);
        nameLabel.setForeground(color.WHITE);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(190, 140, 300, 30);
        add(nameField);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(130, 190, 100, 30);
        courseLabel.setForeground(color.WHITE);
        add(courseLabel);

        courseField = new JTextField();
        courseField.setBounds(190, 190, 300, 30);
        add(courseField);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 300, 100, 40);
        add(submitButton);

        clear1=new JButton("Clear");
        clear1.setBounds(320,300,100,40);
        add(clear1);

        exit=new JButton("Exit");
        exit.setBounds(480,300,100,40);
        add(exit);

        exit.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 

            {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    System.exit(0);
                }
            }
        });

        clear1.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
             {
                // Clear the fields
                idField.setText("");
                nameField.setText("");
                courseField.setText("");
                JOptionPane.showMessageDialog(null, "Fields cleared.");
            }
        });

        // Add ActionListener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String course = courseField.getText();
                if (submitAttendance(id, name, course)) {

                    new Course04().setVisible(true);
                    dispose();
                    JOptionPane.showMessageDialog(null, "Attendance recorded!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to record attendance.");
                }
            }
        });
    }

    // Method to submit attendance to the database
    boolean submitAttendance(String id, String name, String course) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your6";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Create the SQL query
            String sql = "INSERT INTO your7 (id, name, course) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, course);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new Attendance04().setVisible(true);
            }
        });
    }
}
