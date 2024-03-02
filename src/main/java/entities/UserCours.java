package entities;

public class UserCours {

//todo DATABASE
    private int id ;
    private int userId ;
    private int coursId ;

    private boolean isCorrectQuizz ;
    private  int state  ;

    public UserCours() {
    }


    public UserCours(int userId, int coursId, boolean isCorrectQuizz, int state) {
        this.userId = userId;
        this.coursId = coursId;
        this.isCorrectQuizz = isCorrectQuizz;
        this.state = state;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isCorrectQuizz() {
        return isCorrectQuizz;
    }

    public void setCorrectQuizz(boolean correctQuizz) {
        isCorrectQuizz = correctQuizz;
    }
}
