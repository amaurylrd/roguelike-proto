package com.engine.graphics2d;

import com.jogamp.opengl.awt.GLCanvas;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas {
    public Canvas(Capabilities capabilities) {
        super(capabilities);
        setAutoSwapBufferMode(true);
    }

    public Canvas() {
        this(Capabilities.create());
    }
}