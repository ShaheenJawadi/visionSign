package entities;

public class UserCours {


    private int id ;
    private int userId ;
    private int coursId ;

    private int isCorrectQuizz ;
    private  int state  ;




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

    public int getIsCorrectQuizz() {
        return isCorrectQuizz;
    }

    public void setIsCorrectQuizz(int isCorrectQuizz) {
        this.isCorrectQuizz = isCorrectQuizz;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
