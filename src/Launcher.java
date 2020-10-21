import engine.application.Application;
import engine.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
        System.out.println("message");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("salut");
        primaryStage.setSize(400, 400);
        //primaryStage.centerOnScreen();
        
        //Routine loop = new Routine(primaryStage);
        //loop.start();
    }

    @Override
    public void stop() {
        //save game Serialization.save()
    }

    @Override
    protected void init() {
    }
}