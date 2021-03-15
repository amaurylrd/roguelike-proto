package com.engine.render2d;

import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLCapabilities;

public class Canvas extends GLCanvas {
    public Canvas(GLCapabilities capabilities) {
        super(capabilities);
        setAutoSwapBufferMode(true);
    }
} 