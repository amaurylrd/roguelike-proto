package com.sandbox;

import javax.media.opengl.GL2;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_SHADER_PATH = SHADER_FOLDER + "vertex_shader.glsl";
    private static final String FRAGMENT_SHADER_PATH = SHADER_FOLDER + "fragment_shader.glsl";

    public StaticShader(GL2 gl) {
        super(gl, VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);
    }

    @Override
    public void bindAttributes(GL2 gl) {
        bindAttribute(gl, 0, "attribute_position");
    }
    
}
