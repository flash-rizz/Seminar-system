import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RegistrationForm extends JFrame {
    private JTextField titleField;
    private JTextArea abstractArea;
    private JTextField supervisorField;
    private JComboBox<String> typeBox;
    private JLabel fileLabel;
    private String selectedFilePath = null;
    private Student student;

    public RegistrationForm(Student student) {
        this.student = student;

        setTitle("Presentation Registration");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        titleField = new JTextField(20);
        abstractArea = new JTextArea(5, 20);
        supervisorField = new JTextField(20);
        typeBox = new JComboBox<>(new String[]{"Oral", "Poster"});

        JButton uploadBtn = new JButton("Upload File");
        JButton submitBtn = new JButton("Submit");
        fileLabel = new JLabel("No file selected");

        uploadBtn.addActionListener(e -> uploadFile());
        submitBtn.addActionListener(e -> submitForm());

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2));
        fieldsPanel.add(new JLabel("Title:")); fieldsPanel.add(titleField);
        fieldsPanel.add(new JLabel("Abstract:")); fieldsPanel.add(new JScrollPane(abstractArea));
        fieldsPanel.add(new JLabel("Supervisor:")); fieldsPanel.add(supervisorField);
        fieldsPanel.add(new JLabel("Presentation Type:")); fieldsPanel.add(typeBox);
        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.add(uploadBtn);
        buttonPanel.add(fileLabel);
        buttonPanel.add(submitBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void uploadFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            selectedFilePath = file.getAbsolutePath();
            fileLabel.setText(file.getName());
        }
    }

    private void submitForm() {
        
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty");
            return;
        }

        if (abstractArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Abstract cannot be empty");
            return;
        }

        if (supervisorField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Supervisor cannot be empty");
            return;
        }

        if (selectedFilePath == null || selectedFilePath.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please upload a file");
            return;
        }

        Submission submission = new Submission(
                titleField.getText(),
                abstractArea.getText(),
                supervisorField.getText(),
                typeBox.getSelectedItem().toString(),
                selectedFilePath
        );
        student.setSubmission(submission);

        JOptionPane.showMessageDialog(this, "Registration Successful!\nFile Path:\n" + selectedFilePath);
        dispose();
    }
}