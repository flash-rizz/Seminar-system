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
    private JLabel awardResultLabel = new JLabel("No nomination calculated yet.");

    private JSpinner dateSpinner;
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
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Create Session"));

        panel.add(new JLabel("Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd MMM yyyy");
        dateSpinner.setEditor(editor);
        panel.add(dateSpinner);

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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Award Nomination"));

        JButton awardBtn = new JButton("Calculate Highest Score");
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
        String venue = venueField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();

        if (venue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Venue cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session s = new Session(date, venue, type);
        SeminarSystem.sessions.add(s);
        sessionModel.addElement(s);

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
            awardResultLabel.setText("No evaluations available.");
            return;
        }

        Evaluation topEval = null;
        double topScore = -1;

        for (Evaluation eval : SeminarSystem.evaluations) {
            if (eval == null) {
                continue;
            }
            double score = eval.getTotalScore();
            if (score > topScore) {
                topScore = score;
                topEval = eval;
            }
        }

        if (topEval == null) {
            awardResultLabel.setText("Could not read evaluation scores.");
            return;
        }

        Student winner = topEval.getStudent();
        String studentName = (winner != null) ? winner.getUsername() : "Unknown Student";
        Award highestScoreAward = new Award("Best Presenter", winner, topScore);
        SeminarSystem.awards.removeIf(a -> "Best Presenter".equals(a.getAwardName()));
        SeminarSystem.awards.add(highestScoreAward);

        if (studentName == null) studentName = "Unknown Student";
        awardResultLabel.setText("Highest score: " + studentName + " (" + topScore + ")");
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
    }

    private void handleLogout() {
        this.dispose();
        new LoginScreen();
    }
}
