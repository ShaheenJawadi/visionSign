package entities;

public class Suggestion {

    private int id;
    private String suggestion;

    private boolean status;
    private int questionId;

    public Suggestion(){};

    public Suggestion(int id, String suggestion, boolean status, int questionId) {
        this.id = id;
        this.suggestion = suggestion;
        this.status = status;
        this.questionId = questionId;
    }

    public Suggestion(String suggestion, boolean status, int questionId) {
        this.suggestion = suggestion;
        this.status = status;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "id=" + id +
                ", suggestion='" + suggestion + '\'' +
                ", status=" + status +
                ", questionId=" + questionId +
                '}';
    }
}
