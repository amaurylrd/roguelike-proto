package com.engine.graphics2d;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;

public class Capabilities extends GLCapabilities {
    private Capabilities(GLProfile profile) {
        super(profile);
        setRedBits(8);
        setGreenBits(8);
        setBlueBits(8);
        setAlphaBits(8);
        setDoubleBuffered(true);
        setBackgroundOpaque(true);
        setHardwareAccelerated(true);
        setNumSamples(4);
        setBackgroundOpaque(false);
        setSampleBuffers(true);
    }

    public static Capabilities create() {
        try {
            GLProfile.initSingleton();
            GLProfile profile = GLProfile.get(GLProfile.GL2);
            return new Capabilities(profile);  
        } catch (GLException exception) {
            throw new RuntimeException("Error: OpenGL version is not supported.", exception);
        }
        
    }
}