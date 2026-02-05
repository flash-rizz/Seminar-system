public class Evaluation {
    private Student student;
    private Evaluator evaluator;
    private int problemClarity;
    private int methodology;
    private int results;
    private String comments;

    public Evaluation(Student student, Evaluator evaluator, int problemClarity, int methodology, int results, String comments) {
        this.student = student;
        this.evaluator = evaluator;
        this.problemClarity = problemClarity;
        this.methodology = methodology;
        this.results = results;
        this.comments = comments;
    }

    public Student getStudent() { return student; }
    public Evaluator getEvaluator() { return evaluator; }
    public int getProblemClarity() { return problemClarity; }
    public int getMethodology() { return methodology; }
    public int getResults() { return results; }
    public String getComments() { return comments; }

    public double getTotalScore() {
        return problemClarity + methodology + results;
    }

    public String toString() {
        String studentName = (student != null) ? student.getUsername() : "Unknown";
        String evaluatorName = (evaluator != null) ? evaluator.getUsername() : "Unknown";
        return studentName + " evaluated by " + evaluatorName + " (" + getTotalScore() + ")";
    }
}