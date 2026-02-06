public class Submission {
    private String title;
    private String abstractText;
    private String supervisor;
    private String presentationType;
    private String filePath;
    private String boardId;

    public Submission(String title, String abstractText, String supervisor, String presentationType, String filePath) {
        this.title = title;
        this.abstractText = abstractText;
        this.supervisor = supervisor;
        this.presentationType = presentationType;
        this.filePath = filePath;
        this.boardId = "";
    }

    public String getTitle() { return title; }
    public String getAbstractText() { return abstractText; }
    public String getSupervisor() { return supervisor; }
    public String getPresentationType() { return presentationType; }
    public String getFilePath() { return filePath; }
    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }
    public boolean isPoster() { return "Poster".equalsIgnoreCase(presentationType); }
}
