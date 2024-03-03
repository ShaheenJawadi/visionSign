package entities;

public class Notes {
    private int id;
    private float note;
    private int userId;
    private int quizId;

    public Notes(){}

    public Notes(int id, float note, int userId, int quizId) {
        this.id = id;
        this.note = note;
        this.userId = userId;
        this.quizId = quizId;
    }

    public Notes(float note, int userId, int quizId) {
        this.note = note;
        this.userId = userId;
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", note=" + note +
                ", userId=" + userId +
                ", quizId=" + quizId +
                '}';
    }
}
