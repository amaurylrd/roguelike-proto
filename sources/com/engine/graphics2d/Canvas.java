package com.engine.graphics2d;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLCapabilities;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas {
    public Canvas(GLCapabilities capabilities) {
        super(capabilities);
        setAutoSwapBufferMode(true);
    }
}