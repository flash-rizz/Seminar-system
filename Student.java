public class Student extends User {
    private java.util.ArrayList<Submission> submissions = new java.util.ArrayList<>();

    public Student(String username, String password) {
        super(username, password, "Student");
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }

    public java.util.ArrayList<Submission> getSubmissions() {
        return submissions;
    }

    public Submission getSubmissionForSession(Session session) {
        if (session == null) return null;
        for (Submission s : submissions) {
            if (s.getSession() == session) {
                return s;
            }
        }
        return null;
    }
}
