package com.engine.graphics2d;

import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLCapabilities;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas {
    public Canvas(GLCapabilities capabilities) {
        super(capabilities);
        setAutoSwapBufferMode(true);
    }
}