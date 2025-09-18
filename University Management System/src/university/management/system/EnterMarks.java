package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class EnterMarks extends JFrame implements ActionListener {

    Choice crollno;
    JComboBox<String> cbsemester;
    JTextField tfsub1, tfsub2, tfsub3, tfsub4, tfsub5;
    JTextField tfmarks1, tfmarks2, tfmarks3, tfmarks4, tfmarks5;
    JButton cancel, submit, deleteAll;

    EnterMarks() {
        setSize(1000, 500);
        setLocation(300, 150);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/exam.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(500, 40, 400, 300);
        add(image);

        JLabel heading = new JLabel("Enter Marks of Student");
        heading.setBounds(50, 0, 500, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel lblrollnumber = new JLabel("Select Roll Number");
        lblrollnumber.setBounds(50, 70, 150, 20);
        add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(200, 70, 150, 20);
        add(crollno);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblsemester = new JLabel("Select Semester");
        lblsemester.setBounds(50, 110, 150, 20);
        add(lblsemester);

        String semester[] = { "1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester",
                "7th Semester", "8th Semester" };
        cbsemester = new JComboBox<>(semester);
        cbsemester.setBounds(200, 110, 150, 20);
        cbsemester.setBackground(Color.WHITE);
        add(cbsemester);

        JLabel lblentersubject = new JLabel("Enter Subject");
        lblentersubject.setBounds(100, 150, 200, 40);
        add(lblentersubject);

        JLabel lblentermarks = new JLabel("Enter Marks");
        lblentermarks.setBounds(320, 150, 200, 40);
        add(lblentermarks);

        tfsub1 = new JTextField();
        tfsub1.setBounds(50, 200, 200, 20);
        add(tfsub1);

        tfsub2 = new JTextField();
        tfsub2.setBounds(50, 230, 200, 20);
        add(tfsub2);

        tfsub3 = new JTextField();
        tfsub3.setBounds(50, 260, 200, 20);
        add(tfsub3);

        tfsub4 = new JTextField();
        tfsub4.setBounds(50, 290, 200, 20);
        add(tfsub4);

        tfsub5 = new JTextField();
        tfsub5.setBounds(50, 320, 200, 20);
        add(tfsub5);

        tfmarks1 = new JTextField();
        tfmarks1.setBounds(250, 200, 200, 20);
        add(tfmarks1);

        tfmarks2 = new JTextField();
        tfmarks2.setBounds(250, 230, 200, 20);
        add(tfmarks2);

        tfmarks3 = new JTextField();
        tfmarks3.setBounds(250, 260, 200, 20);
        add(tfmarks3);

        tfmarks4 = new JTextField();
        tfmarks4.setBounds(250, 290, 200, 20);
        add(tfmarks4);

        tfmarks5 = new JTextField();
        tfmarks5.setBounds(250, 320, 200, 20);
        add(tfmarks5);

        submit = new JButton("Submit");
        submit.setBounds(70, 360, 150, 25);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(submit);

        cancel = new JButton("Back");
        cancel.setBounds(280, 360, 150, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        // New Delete All Marks button
        deleteAll = new JButton("Delete All Marks");
        deleteAll.setBounds(490, 360, 150, 25);
        deleteAll.setBackground(Color.RED);
        deleteAll.setForeground(Color.WHITE);
        deleteAll.setFont(new Font("Tahoma", Font.BOLD, 15));
        deleteAll.addActionListener(e -> {
            String rollno = crollno.getSelectedItem();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete ALL marks and subjects for roll number " + rollno + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteAllMarksForStudent(rollno);
            }
        });
        add(deleteAll);

        setVisible(true);
    }

    // Delete all marks and subjects for a roll number (all semesters)
    public void deleteAllMarksForStudent(String rollno) {
        try {
            Conn c = new Conn();

            String deleteSubjects = "DELETE FROM subject WHERE rollno = '" + rollno + "'";
            String deleteMarks = "DELETE FROM marks WHERE rollno = '" + rollno + "'";

            c.s.executeUpdate(deleteSubjects);
            c.s.executeUpdate(deleteMarks);

            JOptionPane.showMessageDialog(null, "All marks and subjects deleted for roll number: " + rollno);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting marks: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            try {
                Conn c = new Conn();

                // Prepare data for subjects and marks, empty strings or 0 for missing
                String rollno = crollno.getSelectedItem();
                String semester = (String) cbsemester.getSelectedItem();

                String sub1 = tfsub1.getText().trim();
                String sub2 = tfsub2.getText().trim();
                String sub3 = tfsub3.getText().trim();
                String sub4 = tfsub4.getText().trim();
                String sub5 = tfsub5.getText().trim();

                String marks1 = tfmarks1.getText().trim();
                String marks2 = tfmarks2.getText().trim();
                String marks3 = tfmarks3.getText().trim();
                String marks4 = tfmarks4.getText().trim();
                String marks5 = tfmarks5.getText().trim();

                // Insert or update the subject record for the student & semester
                String checkSubject = "SELECT * FROM subject WHERE rollno = '" + rollno + "' AND semester = '" + semester
                        + "'";
                ResultSet rsSub = c.s.executeQuery(checkSubject);

                if (rsSub.next()) {
                    // Update existing subject
                    String updateSubject = "UPDATE subject SET subject1=?, subject2=?, subject3=?, subject4=?, subject5=? WHERE rollno=? AND semester=?";
                    PreparedStatement psSub = c.c.prepareStatement(updateSubject);
                    psSub.setString(1, sub1);
                    psSub.setString(2, sub2);
                    psSub.setString(3, sub3);
                    psSub.setString(4, sub4);
                    psSub.setString(5, sub5);
                    psSub.setString(6, rollno);
                    psSub.setString(7, semester);
                    psSub.executeUpdate();
                } else {
                    // Insert new subject
                    String insertSubject = "INSERT INTO subject VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement psSub = c.c.prepareStatement(insertSubject);
                    psSub.setString(1, rollno);
                    psSub.setString(2, semester);
                    psSub.setString(3, sub1);
                    psSub.setString(4, sub2);
                    psSub.setString(5, sub3);
                    psSub.setString(6, sub4);
                    psSub.setString(7, sub5);
                    psSub.executeUpdate();
                }

                // Insert or update marks record similarly
                String checkMarks = "SELECT * FROM marks WHERE rollno = '" + rollno + "' AND semester = '" + semester + "'";
                ResultSet rsMarks = c.s.executeQuery(checkMarks);

                int m1 = marks1.isEmpty() ? 0 : Integer.parseInt(marks1);
                int m2 = marks2.isEmpty() ? 0 : Integer.parseInt(marks2);
                int m3 = marks3.isEmpty() ? 0 : Integer.parseInt(marks3);
                int m4 = marks4.isEmpty() ? 0 : Integer.parseInt(marks4);
                int m5 = marks5.isEmpty() ? 0 : Integer.parseInt(marks5);

                if (rsMarks.next()) {
                    // Update existing marks
                    String updateMarks = "UPDATE marks SET marks1=?, marks2=?, marks3=?, marks4=?, marks5=? WHERE rollno=? AND semester=?";
                    PreparedStatement psMarks = c.c.prepareStatement(updateMarks);
                    psMarks.setInt(1, m1);
                    psMarks.setInt(2, m2);
                    psMarks.setInt(3, m3);
                    psMarks.setInt(4, m4);
                    psMarks.setInt(5, m5);
                    psMarks.setString(6, rollno);
                    psMarks.setString(7, semester);
                    psMarks.executeUpdate();
                } else {
                    // Insert new marks
                    String insertMarks = "INSERT INTO marks VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement psMarks = c.c.prepareStatement(insertMarks);
                    psMarks.setString(1, rollno);
                    psMarks.setString(2, semester);
                    psMarks.setInt(3, m1);
                    psMarks.setInt(4, m2);
                    psMarks.setInt(5, m3);
                    psMarks.setInt(6, m4);
                    psMarks.setInt(7, m5);
                    psMarks.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Marks inserted/updated successfully");
                setVisible(false);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter valid integer marks or leave blank.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EnterMarks();
    }
}
