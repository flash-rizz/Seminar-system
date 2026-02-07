public class Evaluation {
    private Student student;
    private Evaluator evaluator;
    private Session session;
    private int problemClarity;
    private int methodology;
    private int results;
    private int presentation;
    private String comments;
    private boolean peoplesChoiceVote;

    public Evaluation(Student student, Evaluator evaluator, Session session, int problemClarity, int methodology, int results, int presentation, String comments, boolean peoplesChoiceVote) {
        this.student = student;
        this.evaluator = evaluator;
        this.session = session;
        this.problemClarity = problemClarity;
        this.methodology = methodology;
        this.results = results;
        this.presentation = presentation;
        this.comments = comments;
        this.peoplesChoiceVote = peoplesChoiceVote;
    }

    public Student getStudent() { return student; }
    public Evaluator getEvaluator() { return evaluator; }
    public Session getSession() { return session; }
    public int getProblemClarity() { return problemClarity; }
    public int getMethodology() { return methodology; }
    public int getResults() { return results; }
    public int getPresentation() { return presentation; }
    public String getComments() { return comments; }
    public boolean isPeoplesChoiceVote() { return peoplesChoiceVote; }

    public double getTotalScore() {
        return problemClarity + methodology + results + presentation;
    }

    public String toString() {
        String studentName = (student != null) ? student.getUsername() : "Unknown";
        String evaluatorName = (evaluator != null) ? evaluator.getUsername() : "Unknown";
        return studentName + " evaluated by " + evaluatorName + " (" + getTotalScore() + ")";
    }
}
