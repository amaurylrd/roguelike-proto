import engine.application.Application;
import engine.scene.Entity;
import engine.scene.Scene;
import engine.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setUndecorated(true);
        primaryStage.addScene("test", new Scene());
        primaryStage.setScene("test");
        primaryStage.thread.start();
    }

    @Override
    public void stop() {}

    @Override
    protected void init() {}
}