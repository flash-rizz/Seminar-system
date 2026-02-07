import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

public class CoordinatorDashboard extends JFrame {
    private DefaultListModel<Session> sessionModel = new DefaultListModel<>();
    private JList<Session> sessionList = new JList<>(sessionModel);
    private JComboBox<Student> studentCombo;
    private JComboBox<Evaluator> evaluatorCombo;
    private JTextArea awardResultArea = new JTextArea(2, 60);

    private JSpinner dateSpinner;
    private JTextField timeSlotField;
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
        JPanel panel = new JPanel(new GridLayout(2, 5, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Create Session"));

        panel.add(new JLabel("Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd MMM yyyy");
        dateSpinner.setEditor(editor);
        panel.add(dateSpinner);

        panel.add(new JLabel("Time Slot:"));
        timeSlotField = new JTextField();
        panel.add(timeSlotField);

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
        panel.setBorder(BorderFactory.createTitledBorder("Assign Student & Evaluator"));

        sessionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(sessionList), BorderLayout.CENTER);

        JPanel assignControls = new JPanel(new GridLayout(2, 3, 10, 5));
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

        panel.add(assignControls, BorderLayout.SOUTH);

        refreshUserLists();
        return panel;
    }

    private JPanel buildAwardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Award & Reports"));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton awardBtn = new JButton("Compute Awards");
        awardBtn.addActionListener(e -> handleAwardNomination());
        buttonRow.add(awardBtn);

        JButton top3Btn = new JButton("Show Top 3");
        top3Btn.addActionListener(e -> handleShowTop3());
        buttonRow.add(top3Btn);

        JButton reportBtn = new JButton("Generate Report");
        reportBtn.addActionListener(e -> handleGenerateReport());
        buttonRow.add(reportBtn);

        JButton exportBtn = new JButton("Export Report");
        exportBtn.addActionListener(e -> handleExportReport());
        buttonRow.add(exportBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> handleLogout());
        buttonRow.add(logoutBtn);

        awardResultArea.setEditable(false);
        awardResultArea.setLineWrap(true);
        awardResultArea.setWrapStyleWord(true);
        awardResultArea.setText("No nomination calculated yet.");
        JScrollPane resultScroll = new JScrollPane(awardResultArea);
        resultScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        panel.add(buttonRow, BorderLayout.NORTH);
        panel.add(resultScroll, BorderLayout.CENTER);

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
        String timeSlot = timeSlotField.getText().trim();
        String venue = venueField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();

        if (timeSlot.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Time slot cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (venue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Venue cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session s = new Session(date, timeSlot, venue, type);
        SeminarSystem.sessions.add(s);
        sessionModel.addElement(s);

        timeSlotField.setText("");
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

    private void handleAwardNomination() {
        if (SeminarSystem.evaluations == null || SeminarSystem.evaluations.isEmpty()) {
            awardResultArea.setText("No evaluations available.");
            return;
        }

        SeminarSystem.awards.removeIf(a ->
                "Best Oral".equals(a.getAwardName())
                        || "Best Poster".equals(a.getAwardName())
                        || "People's Choice".equals(a.getAwardName()));

        Award bestOral = computeBestAward("Oral", "Best Oral");
        Award bestPoster = computeBestAward("Poster", "Best Poster");
        Award peopleChoice = computePeoplesChoice();

        if (bestOral != null) SeminarSystem.awards.add(bestOral);
        if (bestPoster != null) SeminarSystem.awards.add(bestPoster);
        if (peopleChoice != null) SeminarSystem.awards.add(peopleChoice);

        StringBuilder sb = new StringBuilder("Awards computed:");
        if (bestOral != null) sb.append(" Best Oral -> ").append(bestOral.getWinner().getUsername());
        if (bestPoster != null) sb.append(" | Best Poster -> ").append(bestPoster.getWinner().getUsername());
        if (peopleChoice != null) sb.append(" | People's Choice -> ").append(peopleChoice.getWinner().getUsername());
        awardResultArea.setText(sb.toString());
    }

    private void handleShowTop3() {
        if (SeminarSystem.evaluations == null || SeminarSystem.evaluations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No evaluations available.");
            return;
        }

        ArrayList<SubmissionScore> scores = computeAverageScores();
        scores.sort((a, b) -> Double.compare(b.averageScore, a.averageScore));

        StringBuilder sb = new StringBuilder("Top 3 Rankings:\n");
        int rank = 1;
        for (SubmissionScore sc : scores) {
            if (rank > 3) break;
            double score = sc.averageScore;
            if (score < 0) {
                continue;
            }
            Student student = sc.submission.getStudent();
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
    }

    private void handleExportReport() {
        if (SeminarSystem.reports.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Generate a report before exporting.");
            return;
        }

        Report report = SeminarSystem.reports.get(SeminarSystem.reports.size() - 1);
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("seminar_report.txt"));
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try (java.io.FileWriter writer = new java.io.FileWriter(chooser.getSelectedFile())) {
            writer.write(report.getContent());
            JOptionPane.showMessageDialog(this, "Report exported successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to export report: " + ex.getMessage());
        }
    }

    private static class SubmissionScore {
        private final Submission submission;
        private final double averageScore;
        private final int totalVotes;

        private SubmissionScore(Submission submission, double averageScore, int totalVotes) {
            this.submission = submission;
            this.averageScore = averageScore;
            this.totalVotes = totalVotes;
        }
    }

    private ArrayList<SubmissionScore> computeAverageScores() {
        ArrayList<SubmissionScore> results = new ArrayList<>();
        java.util.Map<Session, java.util.ArrayList<Evaluation>> map = new java.util.HashMap<>();

        for (Evaluation eval : SeminarSystem.evaluations) {
            if (eval == null || eval.getSession() == null) continue;
            map.computeIfAbsent(eval.getSession(), k -> new java.util.ArrayList<>()).add(eval);
        }

        for (java.util.Map.Entry<Session, java.util.ArrayList<Evaluation>> entry : map.entrySet()) {
            Session session = entry.getKey();
            Student student = session.getStudent();
            if (student == null) continue;
            Submission submission = student.getSubmissionForSession(session);
            if (submission == null) continue;

            double total = 0;
            int votes = 0;
            for (Evaluation eval : entry.getValue()) {
                total += eval.getTotalScore();
                if (eval.isPeoplesChoiceVote()) {
                    votes++;
                }
            }
            double avg = entry.getValue().isEmpty() ? 0 : total / entry.getValue().size();
            results.add(new SubmissionScore(submission, avg, votes));
        }
        return results;
    }

    private Award computeBestAward(String presentationType, String awardName) {
        ArrayList<SubmissionScore> scores = computeAverageScores();
        SubmissionScore best = null;
        for (SubmissionScore sc : scores) {
            Submission sub = sc.submission;
            if (sub == null) continue;
            if (!presentationType.equalsIgnoreCase(sub.getPresentationType())) continue;
            if (best == null || sc.averageScore > best.averageScore) {
                best = sc;
            }
        }
        if (best == null) return null;
        return new Award(awardName, best.submission.getStudent(), best.averageScore);
    }

    private Award computePeoplesChoice() {
        ArrayList<SubmissionScore> scores = computeAverageScores();
        SubmissionScore best = null;
        for (SubmissionScore sc : scores) {
            if (best == null
                    || sc.totalVotes > best.totalVotes
                    || (sc.totalVotes == best.totalVotes && sc.averageScore > best.averageScore)) {
                best = sc;
            }
        }
        if (best == null) return null;
        return new Award("People's Choice", best.submission.getStudent(), best.totalVotes);
    }

    private void handleLogout() {
        this.dispose();
        new LoginScreen();
    }
}
