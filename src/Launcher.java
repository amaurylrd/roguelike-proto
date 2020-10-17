import java.util.Locale;

import engine.application.Application;
import engine.stage.Stage;
import engine.stage.Stage.Style;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
        System.out.println("message");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("salut");
        primaryStage.setSize(400, 400);
        primaryStage.centerOnScreen();
        boolean isCompatible =  !System.getProperty("os.name").toLowerCase().contains("win");
        
        //Routine loop = new Routine(primaryStage);
        //loop.start();
    }

    @Override
    public void stop() {
    }

    @Override
    protected void init() {
    }
}