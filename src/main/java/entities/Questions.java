package entities;

import java.util.List;

public class Questions {
    private int id;
    private String question;
    private int quizId;
    private int userId;

    private List<Suggestion> suggestionsQuestion;

    public List<Suggestion> getSuggestionsQuestion() {
        return suggestionsQuestion;
    }

    public void setSuggestionsQuestion(List<Suggestion> suggestionsQuestion) {
        this.suggestionsQuestion = suggestionsQuestion;
    }

    public Questions(){

    }

    public Questions(int id, String question, int quizId, int userId) {
        this.id = id;
        this.question = question;
        this.quizId = quizId;
        this.userId = userId;
    }

    public Questions(String question, int quizId, int userId) {
        this.question = question;

        this.quizId = quizId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", quizId=" + quizId +
                ", userId=" + userId +
        getSuggestionsQuestion().toString()+
                '}'+'\n'/*+"---"+'\n'*/;
    }
}
