import engine.application.Application;
import engine.render2d.OpenCL;
import engine.stage.Stage;
import game.level.LTest;
//import com.sun.opengl.util.*;
//import org.lwjgl.glfw.GLFWErrorCallback;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas canvas = new GLCanvas(capabilities);
        canvas.setSize(400, 200);

        final javax.swing.JFrame frame = new javax.swing.JFrame (" Basic Frame");
		
        frame.getContentPane().add(canvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        // primaryStage.getContentPane().add(glcanvas);
        // primaryStage.setSize(primaryStage.getContentPane().getPreferredSize());
        // primaryStage.setVisible(true);
        while (true) {}
        
        //primaryStage.addScene("test2", new LTest());
        //primaryStage.setScene("test2");
    }

    @Override
    public void stop() {
        // OpenCL.release();
    }

    @Override
    protected void init() { //System.setProperty("sun.java2d.opengl", "true");
        //engine.render2d.JOCLDeviceQuery.main();    
        //OpenCL.setup();
    }
}