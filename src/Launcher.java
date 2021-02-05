import engine.application.Application;
import engine.stage.Stage;
import game.level.LTest;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.addScene("test2", new LTest());
        primaryStage.setScene("test2");
    }

    @Override
    public void stop() {}

    @Override
    protected void init() {}
}