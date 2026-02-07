import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RegistrationForm extends JFrame {
    private JTextField titleField;
    private JTextArea abstractArea;
    private JTextField supervisorField;
    private JComboBox<String> typeBox;
    private JTextField boardIdField;
    private JComboBox<Session> sessionBox;
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
        boardIdField = new JTextField(20);
        boardIdField.setEnabled(false);
        updateBoardIdState();
        sessionBox = new JComboBox<>();
        sessionBox.setPrototypeDisplayValue(new Session(new java.util.Date(), "09:00-10:00", "Auditorium A", "Proposal"));
        sessionBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Session) {
                    Session s = (Session) value;
                    label.setText(formatSessionShort(s));
                    label.setToolTipText(s.toString());
                }
                return label;
            }
        });
        loadSessions();

        JButton uploadBtn = new JButton("Upload File");
        JButton submitBtn = new JButton("Submit");
        fileLabel = new JLabel("No file selected");

        uploadBtn.addActionListener(e -> uploadFile());
        submitBtn.addActionListener(e -> submitForm());
        typeBox.addActionListener(e -> updateBoardIdState());

        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2));
        fieldsPanel.add(new JLabel("Title:")); fieldsPanel.add(titleField);
        fieldsPanel.add(new JLabel("Abstract:")); fieldsPanel.add(new JScrollPane(abstractArea));
        fieldsPanel.add(new JLabel("Supervisor:")); fieldsPanel.add(supervisorField);
        fieldsPanel.add(new JLabel("Presentation Type:")); fieldsPanel.add(typeBox);
        fieldsPanel.add(new JLabel("Poster Board ID:")); fieldsPanel.add(boardIdField);
        fieldsPanel.add(new JLabel("Select Session:")); fieldsPanel.add(sessionBox);
        add(fieldsPanel, BorderLayout.CENTER);

        JButton sessionInfoBtn = new JButton("Session Details");
        sessionInfoBtn.addActionListener(e -> showSessionDetails());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.add(uploadBtn);
        buttonPanel.add(fileLabel);
        buttonPanel.add(sessionInfoBtn);
        buttonPanel.add(submitBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateBoardIdState() {
        String type = typeBox.getSelectedItem().toString();
        boolean isPoster = "Poster".equalsIgnoreCase(type);
        boardIdField.setEnabled(isPoster);
        if (!isPoster) {
            boardIdField.setText("");
        }
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

        Session selectedSession = (Session) sessionBox.getSelectedItem();
        if (selectedSession == null) {
            JOptionPane.showMessageDialog(this, "Please select a session");
            return;
        }
        if (selectedSession.getStudent() != null && selectedSession.getStudent() != student) {
            JOptionPane.showMessageDialog(this, "Selected session already has a student assigned");
            return;
        }
        if (student.getSubmissionForSession(selectedSession) != null) {
            JOptionPane.showMessageDialog(this, "You already submitted for this session");
            return;
        }

        String type = typeBox.getSelectedItem().toString();
        String boardId = boardIdField.getText().trim();
        if ("Poster".equalsIgnoreCase(type) && boardId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Poster Board ID is required for poster presentations");
            return;
        }

        Submission submission = new Submission(
                student,
                titleField.getText(),
                abstractArea.getText(),
                supervisorField.getText(),
                type,
                selectedFilePath,
                boardId,
                selectedSession
        );
        student.addSubmission(submission);
        selectedSession.setStudent(student);

        JOptionPane.showMessageDialog(this, "Registration Successful!\nFile Path:\n" + selectedFilePath);
        dispose();
    }

    private void loadSessions() {
        sessionBox.removeAllItems();
        for (Session s : SeminarSystem.sessions) {
            if (s.getStudent() == null || s.getStudent() == student) {
                sessionBox.addItem(s);
            }
        }
        if (sessionBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No available sessions yet. Please ask the coordinator to create one.");
        }
    }

    private void showSessionDetails() {
        Session s = (Session) sessionBox.getSelectedItem();
        if (s == null) return;
        JOptionPane.showMessageDialog(this, s.toString(), "Session Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private String formatSessionShort(Session s) {
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("dd MMM yyyy");
        String dateStr = (s.getDate() != null) ? fmt.format(s.getDate()) : "No Date";
        String timeStr = (s.getTimeSlot() != null && !s.getTimeSlot().trim().isEmpty()) ? s.getTimeSlot() : "No Time";
        String venue = (s.getVenue() != null && !s.getVenue().trim().isEmpty()) ? s.getVenue() : "No Venue";
        String type = (s.getSessionType() != null && !s.getSessionType().trim().isEmpty()) ? s.getSessionType() : "No Type";
        return dateStr + " " + timeStr + " | " + type + " | " + venue;
    }
}
