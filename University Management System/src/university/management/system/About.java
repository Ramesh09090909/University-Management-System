package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class About extends JFrame {

    About() {
        setTitle("About - University Management System");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            // Replace with your actual website
            URL url = new URL("https://cutm.ac.in/");

            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setPage(url);

            JScrollPane scrollPane = new JScrollPane(editorPane);
            getContentPane().add(scrollPane, BorderLayout.CENTER);

        } catch (IOException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Unable to load website.", JLabel.CENTER);
            add(errorLabel);
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new About();
    }
}
