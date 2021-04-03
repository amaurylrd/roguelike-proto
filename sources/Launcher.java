import com.engine.application.Application;

import com.engine.stage.Stage;
//import com.game.level.LTest;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.addScene("test_opengl", new com.sandbox.TestScene());
        primaryStage.setScene("test_opengl");
        new com.sandbox.ShaderProgram(null, "sources/Launcher.java", "");
        //primaryStage.setVisible(true);
        //while (true) {}
        //primaryStage.addScene("test2", new LTest());
        //primaryStage.setScene("test2");
    }

    @Override
    public void stop() {}

    @Override
    protected void init() {
        System.setProperty("sun.java2d.opengl", "true");
    }
}