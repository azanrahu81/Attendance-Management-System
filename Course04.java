import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class Course04 extends JFrame {

    // Declare Swing components for Course form
    JTextField idField;
    JTextField nameField;
    JTextField courseField;
    JButton searchButton;
    JButton backButton;
    JButton nextButton; // Declare the Next button
    Color color;
    Font font;

    public Course04() {


        // Set up the JFrame
        setTitle("Course Form");
        // getContentPane().setBackground(color.DAR);
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        Color darkBlue = new Color(0, 0, 139); // RGB for dark blue
        getContentPane().setBackground(darkBlue);



        JLabel l1=new JLabel("Add Course Form");
        l1.setBounds(300,10,250,30);
        l1.setFont(new Font("Serif", Font.BOLD,30));

        l1.setForeground(color.WHITE);
        add(l1);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 60, 700, 20);
        add(separator);


        // Initialize and add Swing components
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 70, 100, 30);
        idLabel.setForeground(color.WHITE);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 70, 240, 30);
        add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 120, 100, 30);
        nameLabel.setForeground(color.WHITE);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 120, 330, 30);
        nameField.setEditable(false); // Make Name field non-editable
        add(nameField);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(50, 170, 100, 30);
        courseLabel.setForeground(color.WHITE);
        add(courseLabel);

        courseField = new JTextField();
        courseField.setBounds(150, 170, 330, 30);
        courseField.setEditable(false); // Make Course field non-editable
        add(courseField);

        searchButton = new JButton("Search");
        searchButton.setBounds(400, 70, 80, 30);
        add(searchButton);

        backButton = new JButton("Back");
        backButton.setBounds(150, 250, 100, 30);
        add(backButton);

        nextButton = new JButton("Next"); // Initialize the Next button
        nextButton.setBounds(290, 250, 100, 30);
        add(nextButton);

        // Add ActionListener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                fetchAttendance(id);
            }
        });

        // Add ActionListener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Course form and go back
            }
        });

        // Add ActionListener to the next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the AddRecordForm04 form
                new AddRecordForm04().setVisible(true);
                dispose(); // Close the current form
            }
        });
    }

    // Method to fetch attendance data from the database
    private void fetchAttendance(String id) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your6";
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

            // Create the SQL query
            String sql = "SELECT name, course FROM your7 WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");

                // Set fetched data to the text fields
                nameField.setText(name);
                courseField.setText(course);

                JOptionPane.showMessageDialog(null, "Data fetched successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No record found with the given ID.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Course04().setVisible(true);
               

            }
        });
    }
}
