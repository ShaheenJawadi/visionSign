package controllers.Student;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NoteQuizController {

    int note;
    int size;
    @FXML
    private Text notess;

    public void setNoteQuiz(int note) {
        this.note=note;
    }

    public void setNombreQuestions(int size) {
        this.size=size;
    }
    @FXML
    void initialize() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.schedule(() -> {
            notess.setText(note + "/" + size);
            executor.shutdown();
        }, 1, TimeUnit.SECONDS);
        executor.shutdown();

    }
}
