package com.ecommerce;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private List<Classroom> classrooms = new ArrayList<>();
    private JComboBox<Classroom> classComboBox;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JLabel statsLabel;

    public Main() {
        setTitle("CodeAlpha - Student Grade Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center of screen

        // add a base class
        Classroom defaultClass = new Classroom("Base Class");
        classrooms.add(defaultClass);

        initUI();
        updateTableAndStats();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // 1. Select and edit the class
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(new Color(240, 243, 246));

        topPanel.add(new JLabel("The open class:"));
        classComboBox = new JComboBox<>(classrooms.toArray(new Classroom[0]));
        classComboBox.addActionListener(e -> updateTableAndStats());
        topPanel.add(classComboBox);

        JButton btnAddClass = new JButton("+ Add new class");
        btnAddClass.addActionListener(e -> addNewClass());
        topPanel.add(btnAddClass);

        add(topPanel, BorderLayout.NORTH);

        // 2. Student table
        String[] columnNames = {"#", "Student Name", "Grade", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tablo hücreleri çift tıklamayla değil, butonla güncellensin
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(26);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // 3. Buttons (add, edit, delete)
        JPanel actionPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAddStudent = new JButton("Add Student");
        JButton btnEditStudent = new JButton("Update Student");
        JButton btnDeleteStudent = new JButton("Delete Student");

        btnAddStudent.addActionListener(e -> addStudent());
        btnEditStudent.addActionListener(e -> editStudent());
        btnDeleteStudent.addActionListener(e -> deleteStudent());

        actionPanel.add(btnAddStudent);
        actionPanel.add(btnEditStudent);
        actionPanel.add(btnDeleteStudent);

        add(actionPanel, BorderLayout.EAST);

        // 4. statistics report (Average, Maximum, Minimum)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        bottomPanel.setBackground(new Color(230, 235, 240));

        statsLabel = new JLabel("Statistics are being calculated...");
        statsLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        bottomPanel.add(statsLabel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private Classroom getSelectedClass() {
        return (Classroom) classComboBox.getSelectedItem();
    }

    private void addNewClass() {
        String className = JOptionPane.showInputDialog(this, "Enter new class name (Ex: 11-B):", "New Class", JOptionPane.PLAIN_MESSAGE);
        if (className != null && !className.trim().isEmpty()) {
            Classroom newClass = new Classroom(className.trim());
            classrooms.add(newClass);
            classComboBox.addItem(newClass);
            classComboBox.setSelectedItem(newClass);
        }
    }

    private void addStudent() {
        Classroom currentClass = getSelectedClass();
        if (currentClass == null) return;

        JTextField nameField = new JTextField();
        JTextField gradeField = new JTextField();
        Object[] message = {
                "Student Name-Surname:", nameField,
                "Grade (0 - 100):", gradeField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                double grade = Double.parseDouble(gradeField.getText().trim());

                if (name.isEmpty() || grade < 0 || grade > 100) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid name and a score between 0 and 100!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                currentClass.addStudent(new Student(name, grade));
                updateTableAndStats();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "The grade must be a numerical value!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editStudent() {
        Classroom currentClass = getSelectedClass();
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select the student you wish to update from the table.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Student student = currentClass.getStudents().get(selectedRow);

        JTextField nameField = new JTextField(student.getName());
        JTextField gradeField = new JTextField(String.valueOf(student.getGrade()));
        Object[] message = {
                "Student Name-Surname:", nameField,
                "Grade (0 - 100):", gradeField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String newName = nameField.getText().trim();
                double newGrade = Double.parseDouble(gradeField.getText().trim());

                if (newName.isEmpty() || newGrade < 0 || newGrade > 100) {
                    JOptionPane.showMessageDialog(this, "Invalid value entered!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                student.setName(newName);
                student.setGrade(newGrade);
                updateTableAndStats();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "The grade must be numeric!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteStudent() {
        Classroom currentClass = getSelectedClass();
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select the student you want to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Approval", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            currentClass.removeStudent(selectedRow);
            updateTableAndStats();
        }
    }

    private void updateTableAndStats() {
        tableModel.setRowCount(0);
        Classroom currentClass = getSelectedClass();
        if (currentClass == null) return;

        List<Student> list = currentClass.getStudents();
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            String status = s.getGrade() >= 50 ? "Passed" : "Failed";
            tableModel.addRow(new Object[]{i + 1, s.getName(), s.getGrade(), status});
        }

        if (list.isEmpty()) {
            statsLabel.setText("There are no students registered in this class yet.");
        } else {
            Student high = currentClass.getHighestStudent();
            Student low = currentClass.getLowestStudent();
            statsLabel.setText(String.format("Total: %d Student  |  Average: %.2f  |  Highest grade: %.2f (%s)  |  Lowest grade: %.2f (%s)",
                    list.size(),
                    currentClass.getAverage(),
                    high.getGrade(), high.getName(),
                    low.getGrade(), low.getName()));
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}