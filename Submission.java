public class Submission {
    private Student student;
    private String title;
    private String abstractText;
    private String supervisor;
    private String presentationType;
    private String filePath;
    private String boardId;
    private Session session;

    public Submission(Student student, String title, String abstractText, String supervisor, String presentationType, String filePath, String boardId, Session session) {
        this.student = student;
        this.title = title;
        this.abstractText = abstractText;
        this.supervisor = supervisor;
        this.presentationType = presentationType;
        this.filePath = filePath;
        this.boardId = boardId;
        this.session = session;
    }

    public Student getStudent() { return student; }
    public String getTitle() { return title; }
    public String getAbstractText() { return abstractText; }
    public String getSupervisor() { return supervisor; }
    public String getPresentationType() { return presentationType; }
    public String getFilePath() { return filePath; }
    public String getBoardId() { return boardId; }
    public Session getSession() { return session; }
}
