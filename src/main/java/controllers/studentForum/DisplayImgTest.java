package controllers.studentForum;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DisplayImgTest {



    private final ImageView imageView;
    private final String imageUrl;

    public DisplayImgTest(ImageView imageView, String imageUrl) {
        this.imageView = imageView;
        this.imageUrl = imageUrl;
    }

    public void loadImage() {
        Task<Image> loadImageTask = new Task<>() {
            @Override
            protected Image call() {
                return new Image(imageUrl, true); // true for background loading
            }
        };

        loadImageTask.setOnSucceeded(event -> imageView.setImage(loadImageTask.getValue()));

        Thread thread = new Thread(loadImageTask);
        thread.setDaemon(true); // Set the thread as a daemon thread
        thread.start();
    }
}
