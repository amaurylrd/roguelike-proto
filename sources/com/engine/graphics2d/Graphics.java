package com.engine.graphics2d;

import javax.media.opengl.GL2;
import com.engine.geom.Rectangle;

//classe de tests immediate mode maggle

/*
final String GL_VENDOR = gl.glGetString(GL2.GL_VENDOR);
        final String GL_RENDERER = gl.glGetString(GL2.GL_RENDERER);
        final String GL_VERSION = gl.glGetString(GL2.GL_VERSION);
        */

public abstract class Graphics {
    private static GL2 gl; //pas fait

    public static void drawQuad(Rectangle rectangle) {
        // Draws a quad like this:
        //
        //    height
        //       |
        //       v________
        //       |        |
        //       |        |
        //       |        |
        //       |        |
        //       |________|<--width
        //     xy
    
        //glTexCoord2f function is there in case we want to put a texture on
        //glVertex2f function sets the points of the quad

        gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(rectangle.getX(), rectangle.getY());
            gl.glTexCoord2f(1, 0);
            gl.glVertex2f(rectangle.getX() + rectangle.getWidth(), rectangle.getY());
            gl.glTexCoord2f(1, 1);
            gl.glVertex2f(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
            gl.glTexCoord2f(0, 1);
            gl.glVertex2f(rectangle.getX(), rectangle.getY() + rectangle.getHeight());
        gl.glEnd();
    }

    //setColor: GL11.glColor3f(1.0f, 0.0f, 0.0f);  


    //à voir:
    
    //matrices
    //vertexbuffer
    //vertexarray
}
