import java.text.SimpleDateFormat;
import java.util.Date;

public class Session {
    private Date date;
    private String startTime;
    private String endTime;
    private String venue;
    private String sessionType;
    private Student student;
    private Evaluator evaluator;

    public Session(Date date, String startTime, String endTime, String venue, String sessionType) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.sessionType = sessionType;
    }

    public Date getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getVenue() { return venue; }
    public String getSessionType() { return sessionType; }
    public Student getStudent() { return student; }
    public Evaluator getEvaluator() { return evaluator; }

    public void setStudent(Student student) { this.student = student; }
    public void setEvaluator(Evaluator evaluator) { this.evaluator = evaluator; }

    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        String dateStr = (date != null) ? fmt.format(date) : "No Date";
        String timeStr = startTime + "-" + endTime;
        String studentStr = (student != null) ? student.getUsername() : "Unassigned";
        String evaluatorStr = (evaluator != null) ? evaluator.getUsername() : "Unassigned";
        return dateStr + " | " + timeStr + " | " + sessionType + " | " + venue
                + " | Student: " + studentStr + " | Evaluator: " + evaluatorStr;
    }
}
