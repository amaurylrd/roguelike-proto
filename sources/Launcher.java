import com.engine.application.Application;

import com.engine.stage.Stage;
import com.game.level.LTest;

import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GL2;
import javax.media.opengl.GL;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import com.jogamp.opengl.util.texture.Texture;
import java.nio.ByteBuffer;

import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.glu.*;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    public static class Renderer implements GLEventListener {
        @Override
        public void display(GLAutoDrawable drawable) {
            final GL2 gl = drawable.getGL().getGL2();
            gl.glClearColor(1f, 1f, 1f, 1f);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

            BufferedImage image = com.engine.application.Ressources.ressource("banana");
            TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), image, false);
            Texture texture = TextureIO.newTexture(textureData);

            texture.enable(gl);
            texture.bind(gl);
            
            gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
            TextureCoords coords = texture.getImageTexCoords();
            gl.glBegin(GL2.GL_QUADS);
            
            gl.glTexCoord2f(coords.left(), coords.bottom());
            gl.glVertex2f(-1, -1);

            gl.glTexCoord2f(coords.right(), coords.bottom());
            gl.glVertex2f(1, -1);

            gl.glTexCoord2f(coords.right(), coords.top());
            gl.glVertex2f(1, 1);
            
            gl.glTexCoord2f(coords.left(), coords.top());
            gl.glVertex2f(-1, 1);
            
            gl.glEnd();
            texture.disable(gl);
            gl.glFlush();
            
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {}

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

        @Override
        public void init(GLAutoDrawable drawable) {}
    }

    @Override
    public void start(Stage primaryStage) {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
        capabilities.setBackgroundOpaque(true);

        final GLCanvas canvas = new GLCanvas(capabilities);
        
        canvas.setSize(400, 200);
        Renderer renderer = new Renderer();
        canvas.addGLEventListener(renderer);

        final javax.swing.JFrame frame = new javax.swing.JFrame(" Basic Frame");
		
        frame.getContentPane().add(canvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        
        //canvas.render();
        
        //dispose(drawable)
        //init(drawable)
        //display(drawable) -> xx
        // 
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