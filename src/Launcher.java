import engine.application.Application;
import engine.scene.Scene;
import engine.stage.Stage;

import engine.physics2d.Vector;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.addScene("test", new Scene());
        primaryStage.setScene("test");
        primaryStage.thread.start();
    }

    @Override
    public void stop() {}

    @Override
    protected void init() {}
}