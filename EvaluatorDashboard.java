import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EvaluatorDashboard extends JFrame {
    private JComboBox<Session> sessionCombo;
    private JSlider claritySlider;
    private JSlider methodologySlider;
    private JSlider resultsSlider;
    private JSlider presentationSlider;
    private JTextArea commentsArea;
    private JLabel totalLabel;
    private JCheckBox peoplesChoiceCheck;
    private JLabel assignmentStatusLabel;

    public EvaluatorDashboard(Evaluator evaluator) {
        setTitle("Evaluator Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildFormPanel(evaluator), BorderLayout.CENTER);
        add(buildBottomPanel(evaluator), BorderLayout.SOUTH);

        updateTotal();
        setVisible(true);
    }

    private JPanel buildFormPanel(Evaluator evaluator) {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Evaluation Form"));

        panel.add(new JLabel("Select Session:"));
        sessionCombo = new JComboBox<>();
        panel.add(sessionCombo);

        panel.add(new JLabel("Problem Clarity (0-10):"));
        claritySlider = createSlider();
        panel.add(claritySlider);

        panel.add(new JLabel("Methodology (0-10):"));
        methodologySlider = createSlider();
        panel.add(methodologySlider);

        panel.add(new JLabel("Results (0-10):"));
        resultsSlider = createSlider();
        panel.add(resultsSlider);

        panel.add(new JLabel("Presentation (0-10):"));
        presentationSlider = createSlider();
        panel.add(presentationSlider);

        panel.add(new JLabel("Comments:"));
        commentsArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(commentsArea));

        panel.add(new JLabel("People's Choice Vote:"));
        peoplesChoiceCheck = new JCheckBox("Cast vote for selected student");
        panel.add(peoplesChoiceCheck);

        assignmentStatusLabel = new JLabel();
        panel.add(new JLabel("Assignments:"));
        panel.add(assignmentStatusLabel);

        loadAssignedSessions(evaluator);

        return panel;
    }

    private JPanel buildBottomPanel(Evaluator evaluator) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton calcBtn = new JButton("Calculate Total");
        calcBtn.addActionListener(e -> updateTotal());
        panel.add(calcBtn);

        totalLabel = new JLabel("Total: 0");
        panel.add(totalLabel);

        JButton submitBtn = new JButton("Submit Evaluation");
        submitBtn.addActionListener(e -> handleSubmit(evaluator));
        panel.add(submitBtn);

        JButton refreshBtn = new JButton("Refresh Assignments");
        refreshBtn.addActionListener(e -> loadAssignedSessions(evaluator));
        panel.add(refreshBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> handleLogout());
        panel.add(logoutBtn);

        return panel;
    }

    private JSlider createSlider() {
        JSlider slider = new JSlider(0, 10, 5);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private void updateTotal() {
        int total = claritySlider.getValue() + methodologySlider.getValue() + resultsSlider.getValue() + presentationSlider.getValue();
        totalLabel.setText("Total: " + total);
    }

    private void handleSubmit(Evaluator evaluator) {
        Session session = (Session) sessionCombo.getSelectedItem();
        if (session == null) {
            JOptionPane.showMessageDialog(this, "Please select a session.");
            return;
        }
        Student st = session.getStudent();
        if (st == null) {
            JOptionPane.showMessageDialog(this, "Selected session has no student.");
            return;
        }

        Submission submission = st.getSubmissionForSession(session);
        if (submission == null) {
            JOptionPane.showMessageDialog(this, "Selected student has no submission for this session.");
            return;
        }

        if (hasAlreadyEvaluated(evaluator, st, session)) {
            JOptionPane.showMessageDialog(this, "You have already evaluated this student.", "Duplicate", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int clarity = claritySlider.getValue();
        int methodology = methodologySlider.getValue();
        int results = resultsSlider.getValue();
        int presentation = presentationSlider.getValue();
        String comments = commentsArea.getText().trim();

        Evaluation eval = new Evaluation(st, evaluator, session, clarity, methodology, results, presentation, comments, peoplesChoiceCheck.isSelected());
        SeminarSystem.evaluations.add(eval);

        JOptionPane.showMessageDialog(this, "Evaluation submitted. Total: " + eval.getTotalScore());

        commentsArea.setText("");
        claritySlider.setValue(5);
        methodologySlider.setValue(5);
        resultsSlider.setValue(5);
        presentationSlider.setValue(5);
        peoplesChoiceCheck.setSelected(false);
        updateTotal();
    }

    private boolean hasAlreadyEvaluated(Evaluator evaluator, Student student, Session session) {
        for (Evaluation eval : SeminarSystem.evaluations) {
            if (eval.getEvaluator() == evaluator && eval.getStudent() == student && eval.getSession() == session) {
                return true;
            }
        }
        return false;
    }

    private void loadAssignedSessions(Evaluator evaluator) {
        sessionCombo.removeAllItems();
        java.util.ArrayList<Session> assigned = new java.util.ArrayList<>();
        for (Session s : SeminarSystem.sessions) {
            if (s.getEvaluator() == evaluator && s.getStudent() != null) {
                assigned.add(s);
            }
        }

        for (Session s : assigned) {
            sessionCombo.addItem(s);
        }

        if (assigned.isEmpty()) {
            assignmentStatusLabel.setText("No assigned sessions yet.");
        } else {
            assignmentStatusLabel.setText("Assigned: " + assigned.size());
        }
    }

    private void handleLogout() {
        this.dispose();
        new LoginScreen();
    }
}
