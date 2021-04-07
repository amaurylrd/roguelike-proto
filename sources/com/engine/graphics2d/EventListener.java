package com.engine.graphics2d;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GL2;
import java.awt.Color;

// import java.awt.image.BufferedImage;
// import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
// import com.jogamp.opengl.util.texture.TextureData;
// import com.jogamp.opengl.util.texture.Texture;
// import com.jogamp.opengl.util.texture.TextureCoords;
// import com.jogamp.opengl.util.texture.TextureIO;

public interface EventListener extends GLEventListener {  
    public static final Color CLEAR_COLOR = Color.BLACK;
    
    public default void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        begin(gl);
    }

    public abstract void begin(GL2 graphics);

    public default void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glUseProgram(0);

        end();
    }

    public abstract void end();

    public default void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        
        double scale = 0.8;
        gl.glOrtho(0.0, width / scale, height / scale, 0.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public default void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(CLEAR_COLOR.getRed(), CLEAR_COLOR.getGreen(), CLEAR_COLOR.getBlue(), CLEAR_COLOR.getAlpha());
        gl.glClearDepth(1.0);
        gl.glClearStencil(0);

        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glShadeModel(GL2.GL_SMOOTH);

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL2.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL2.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL2.GL_VERSION));
    }
}
