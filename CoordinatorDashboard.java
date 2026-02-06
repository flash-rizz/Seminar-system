import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.io.File;

public class CoordinatorDashboard extends JFrame {
    private DefaultListModel<Session> sessionModel = new DefaultListModel<>();
    private JList<Session> sessionList = new JList<>(sessionModel);
    private JComboBox<Student> studentCombo;
    private JComboBox<Evaluator> evaluatorCombo;
    private JLabel awardResultLabel = new JLabel("No nomination calculated yet.");

    private JSpinner dateSpinner;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField venueField;
    private JComboBox<String> typeCombo;

    public CoordinatorDashboard() {
        setTitle("Coordinator Dashboard");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildCreateSessionPanel(), BorderLayout.NORTH);
        add(buildAssignPanel(), BorderLayout.CENTER);
        add(buildAwardPanel(), BorderLayout.SOUTH);

        loadSessions();
        setVisible(true);
    }

    private JPanel buildCreateSessionPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Create Session"));

        panel.add(new JLabel("Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd MMM yyyy");
        dateSpinner.setEditor(editor);
        panel.add(dateSpinner);

        panel.add(new JLabel("Start Time (HH:mm):"));
        startTimeField = new JTextField();
        panel.add(startTimeField);

        panel.add(new JLabel("End Time (HH:mm):"));
        endTimeField = new JTextField();
        panel.add(endTimeField);

        panel.add(new JLabel("Venue:"));
        venueField = new JTextField();
        panel.add(venueField);

        panel.add(new JLabel("Session Type:"));
        typeCombo = new JComboBox<>(new String[] { "Proposal", "Progress", "Final" });
        panel.add(typeCombo);

        JButton createBtn = new JButton("Create Session");
        createBtn.addActionListener(this::handleCreateSession);
        panel.add(createBtn);

        JButton refreshBtn = new JButton("Refresh Lists");
        refreshBtn.addActionListener(e -> refreshUserLists());
        panel.add(refreshBtn);

        return panel;
    }

    private JPanel buildAssignPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Assign Student, Evaluator, Poster Board"));

        sessionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(sessionList), BorderLayout.CENTER);

        JPanel assignControls = new JPanel(new GridLayout(3, 3, 10, 5));
        assignControls.add(new JLabel("Student:"));
        studentCombo = new JComboBox<>();
        assignControls.add(studentCombo);
        JButton assignStudentBtn = new JButton("Assign Student");
        assignStudentBtn.addActionListener(e -> handleAssignStudent());
        assignControls.add(assignStudentBtn);

        assignControls.add(new JLabel("Evaluator:"));
        evaluatorCombo = new JComboBox<>();
        assignControls.add(evaluatorCombo);
        JButton assignEvaluatorBtn = new JButton("Assign Evaluator");
        assignEvaluatorBtn.addActionListener(e -> handleAssignEvaluator());
        assignControls.add(assignEvaluatorBtn);

        assignControls.add(new JLabel("Poster Board ID:"));
        JTextField boardIdField = new JTextField();
        assignControls.add(boardIdField);
        JButton assignBoardBtn = new JButton("Assign Board ID");
        assignBoardBtn.addActionListener(e -> handleAssignBoardId(boardIdField.getText().trim()));
        assignControls.add(assignBoardBtn);

        panel.add(assignControls, BorderLayout.SOUTH);

        refreshUserLists();
        return panel;
    }

    private JPanel buildAwardPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Award Nomination"));

        JButton awardBtn = new JButton("Compute Awards");
        awardBtn.addActionListener(e -> handleAwardNomination());
        panel.add(awardBtn);
        panel.add(awardResultLabel);

        JButton top3Btn = new JButton("Show Top 3");
        top3Btn.addActionListener(e -> handleShowTop3());
        panel.add(top3Btn);

        JButton reportBtn = new JButton("Generate Report");
        reportBtn.addActionListener(e -> handleGenerateReport());
        panel.add(reportBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> handleLogout());
        panel.add(logoutBtn);

        return panel;
    }

    private void loadSessions() {
        sessionModel.clear();
        for (Session s : SeminarSystem.sessions) {
            sessionModel.addElement(s);
        }
    }

    private void refreshUserLists() {
        studentCombo.removeAllItems();
        evaluatorCombo.removeAllItems();

        for (User u : SeminarSystem.users) {
            if (u instanceof Student) {
                studentCombo.addItem((Student) u);
            } else if (u instanceof Evaluator) {
                evaluatorCombo.addItem((Evaluator) u);
            }
        }
    }

    private void handleCreateSession(ActionEvent e) {
        Date date = (Date) dateSpinner.getValue();
        String startTime = startTimeField.getText().trim();
        String endTime = endTimeField.getText().trim();
        String venue = venueField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();

        if (startTime.isEmpty() || endTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Start and end time are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (venue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Venue cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session s = new Session(date, startTime, endTime, venue, type);
        SeminarSystem.sessions.add(s);
        sessionModel.addElement(s);

        startTimeField.setText("");
        endTimeField.setText("");
        venueField.setText("");
        JOptionPane.showMessageDialog(this, "Session created.");
    }

    private void handleAssignStudent() {
        Session s = sessionList.getSelectedValue();
        Student st = (Student) studentCombo.getSelectedItem();
        if (s == null || st == null) {
            JOptionPane.showMessageDialog(this, "Select a session and a student.");
            return;
        }
        s.setStudent(st);
        sessionList.repaint();
        JOptionPane.showMessageDialog(this, "Student assigned to session.");
    }

    private void handleAssignEvaluator() {
        Session s = sessionList.getSelectedValue();
        Evaluator ev = (Evaluator) evaluatorCombo.getSelectedItem();
        if (s == null || ev == null) {
            JOptionPane.showMessageDialog(this, "Select a session and an evaluator.");
            return;
        }
        s.setEvaluator(ev);
        sessionList.repaint();
        JOptionPane.showMessageDialog(this, "Evaluator assigned to session.");
    }

    private void handleAssignBoardId(String boardId) {
        Student st = (Student) studentCombo.getSelectedItem();
        if (st == null || st.getSubmission() == null) {
            JOptionPane.showMessageDialog(this, "Select a student with a submission.");
            return;
        }

        if (!st.getSubmission().isPoster()) {
            JOptionPane.showMessageDialog(this, "Board ID can only be assigned to Poster submissions.");
            return;
        }

        if (boardId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Board ID cannot be empty.");
            return;
        }

        st.getSubmission().setBoardId(boardId);
        JOptionPane.showMessageDialog(this, "Board ID assigned to " + st.getUsername() + ".");
    }

    private void handleAwardNomination() {
        if (SeminarSystem.evaluations == null || SeminarSystem.evaluations.isEmpty()) {
            awardResultLabel.setText("No evaluations available.");
            return;
        }

        Evaluation bestOral = pickTopByType("Oral");
        Evaluation bestPoster = pickTopByType("Poster");
        Evaluation peopleChoice = SeminarSystem.evaluations.stream()
                .max(Comparator.comparingDouble(Evaluation::getPresentation))
                .orElse(null);

        SeminarSystem.awards.removeIf(a ->
                "Best Oral".equals(a.getAwardName())
                        || "Best Poster".equals(a.getAwardName())
                        || "People's Choice".equals(a.getAwardName()));

        StringBuilder summary = new StringBuilder();

        if (bestOral != null) {
            SeminarSystem.awards.add(new Award("Best Oral", bestOral.getStudent(), bestOral.getTotalScore()));
            summary.append("Best Oral: ").append(bestOral.getStudent().getUsername()).append(". ");
        }

        if (bestPoster != null) {
            SeminarSystem.awards.add(new Award("Best Poster", bestPoster.getStudent(), bestPoster.getTotalScore()));
            summary.append("Best Poster: ").append(bestPoster.getStudent().getUsername()).append(". ");
        }

        if (peopleChoice != null) {
            SeminarSystem.awards.add(new Award("People's Choice", peopleChoice.getStudent(), peopleChoice.getPresentation()));
            summary.append("People's Choice: ").append(peopleChoice.getStudent().getUsername()).append(".");
        }

        if (summary.length() == 0) {
            awardResultLabel.setText("Could not calculate awards.");
            return;
        }

        awardResultLabel.setText(summary.toString());
    }

    private Evaluation pickTopByType(String type) {
        Evaluation top = null;
        for (Evaluation eval : SeminarSystem.evaluations) {
            if (eval == null || eval.getStudent() == null || eval.getStudent().getSubmission() == null) {
                continue;
            }
            String submissionType = eval.getStudent().getSubmission().getPresentationType();
            if (!type.equalsIgnoreCase(submissionType)) {
                continue;
            }
            if (top == null || eval.getTotalScore() > top.getTotalScore()) {
                top = eval;
            }
        }
        return top;
    }

    private void handleShowTop3() {
        if (SeminarSystem.evaluations == null || SeminarSystem.evaluations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No evaluations available.");
            return;
        }

        ArrayList<Evaluation> evals = new ArrayList<>(SeminarSystem.evaluations);
        evals.sort((a, b) -> Double.compare(b.getTotalScore(), a.getTotalScore()));

        StringBuilder sb = new StringBuilder("Top 3 Rankings:\n");
        int rank = 1;
        for (Evaluation eval : evals) {
            if (rank > 3) break;
            double score = eval.getTotalScore();
            if (score < 0) {
                continue;
            }
            Student student = eval.getStudent();
            String studentName = (student != null) ? student.getUsername() : null;
            if (studentName == null) studentName = "Unknown Student";
            sb.append(rank).append(". ").append(studentName).append(" (").append(score).append(")\n");
            rank++;
        }

        if (rank == 1) {
            sb.append("No valid evaluation scores found.");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void handleGenerateReport() {
        Report report = Report.generate(SeminarSystem.sessions, SeminarSystem.evaluations, SeminarSystem.awards);
        SeminarSystem.reports.add(report);

        JTextArea reportArea = new JTextArea(report.getContent());
        reportArea.setEditable(false);
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new Dimension(650, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Final Evaluation Report", JOptionPane.INFORMATION_MESSAGE);

        int choice = JOptionPane.showConfirmDialog(this,
                "Export report files (TXT and CSV)?",
                "Export",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Choose export folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File dir = chooser.getSelectedFile();
                boolean txtOk = report.exportAsText(new File(dir, "seminar_report.txt").getAbsolutePath());
                boolean csvOk = Report.exportSummaryCsv(new File(dir, "seminar_evaluation_summary.csv").getAbsolutePath(),
                        SeminarSystem.evaluations);
                if (txtOk && csvOk) {
                    JOptionPane.showMessageDialog(this, "Report exported successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Export failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void handleLogout() {
        this.dispose();
        new LoginScreen();
    }
}
