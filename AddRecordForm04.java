import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class AddRecordForm04 extends JFrame {
    JTable attendanceTable;
    DefaultTableModel tableModel;
    JTextField idField;
    JTextField nameField;
    JTextField courseField;
    JButton addButton;
    JButton searchButton;
    JButton backButton;
    JButton updateButton;
    JButton deleteButton;
    Color color;

    public AddRecordForm04() {
        Color darkBlue = new Color(0, 0, 139); // RGB for dark blue
        getContentPane().setBackground(darkBlue);

        // Font font=new Font("Serif", Font.BOLD,20);

        setTitle("Attendance Records");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel l1=new JLabel("Add Record Form", JLabel.CENTER);
        l1.setBounds(300,10,250,30);
        l1.setFont(new Font("Serif", Font.BOLD,30));

        l1.setForeground(color.WHITE);
        add(l1);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 60, 900, 20);
        add(separator);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(300, 80, 100, 30);
        idLabel.setForeground(color.WHITE);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(360, 80, 280, 30);
        add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(color.WHITE);
        nameLabel.setBounds(300, 120, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(360, 120, 280, 30);
        add(nameField);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setForeground(color.WHITE);
        courseLabel.setBounds(300, 160, 100, 30);
        add(courseLabel);

        courseField = new JTextField();
        courseField.setBounds(360, 160, 280, 30);
        add(courseField);

        addButton = new JButton("Add Record");
        addButton.setBounds(130, 300, 150, 30);
        add(addButton);

        searchButton = new JButton("Search");
        searchButton.setBounds(300, 300, 100, 30);
        add(searchButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(420, 300, 100, 30);
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(540, 300, 100, 30);
        add(deleteButton);

        backButton = new JButton("Exit");
        backButton.setBounds(660, 300, 100, 30);
        add(backButton);

        String[] columnNames = {"ID", "Name", "Course"};
        tableModel = new DefaultTableModel(columnNames, 0);
        attendanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        scrollPane.setBounds(50, 350, 800, 250);
        add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String course = courseField.getText();
                if (addRecordToDatabase(id, name, course)) {
                    addRecord(id, name, course);
                    idField.setText("");
                    nameField.setText("");
                    courseField.setText("");
                    JOptionPane.showMessageDialog(null, "Record added successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add record.");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                searchAttendance(id);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String course = courseField.getText();
                if (updateRecordInDatabase(id, name, course)) {
                    searchAttendance(id); // Refresh the record display
                    JOptionPane.showMessageDialog(null, "Record updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update record.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                if (deleteRecordFromDatabase(id)) {
                    searchAttendance(id); // Refresh the record display
                    idField.setText("");
                    nameField.setText("");
                    courseField.setText("");
                    JOptionPane.showMessageDialog(null, "Record deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete record.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        loadAllRecords();
    }

    public void addRecord(String id, String name, String course) {
        tableModel.addRow(new Object[]{id, name, course});
    }

    private boolean addRecordToDatabase(String id, String name, String course) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your8";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "INSERT INTO your9 (id, name, course) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, course);

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

    private boolean updateRecordInDatabase(String id, String name, String course) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your8";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "UPDATE your9 SET name = ?, course = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, course);
            preparedStatement.setString(3, id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "JDBC Driver not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean deleteRecordFromDatabase(String id) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your8";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "DELETE FROM your9 WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "JDBC Driver not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void searchAttendance(String id) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your8";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "SELECT id, name, course FROM your9 WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String fetchedId = resultSet.getString("id");
                String fetchedName = resultSet.getString("name");
                String fetchedCourse = resultSet.getString("course");
                tableModel.addRow(new Object[]{fetchedId, fetchedName, fetchedCourse});
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No record found with the given ID.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadAllRecords() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your8";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "SELECT id, name, course FROM your9";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");
                tableModel.addRow(new Object[]{id, name, course});
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddRecordForm04().setVisible(true);
            }
        });
    }
}
