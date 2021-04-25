package com.sandbox;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.nio.IntBuffer;
import com.jogamp.opengl.GL2;
import com.jogamp.common.nio.Buffers;

public abstract class ShaderProgram {
    protected final static String SHADER_FOLDER = "ressources/shaders/";

    private int programId;

    public ShaderProgram(GL2 gl, String vertexShader, String fragmentShader) {
        int vertexShaderId = loadShader(gl, vertexShader, GL2.GL_VERTEX_SHADER);
        int fragmentShaderId = loadShader(gl, fragmentShader, GL2.GL_FRAGMENT_SHADER);
        
        programId = gl.glCreateProgram();
		gl.glAttachShader(programId, vertexShaderId);
		gl.glAttachShader(programId, fragmentShaderId);

		bindAttributes(gl);
		
        gl.glLinkProgram(programId);
		gl.glValidateProgram(programId);

        int[] status = new int[1];
        gl.glGetProgramiv(programId, GL2.GL_LINK_STATUS, status, 0);
        if (status[0] == GL2.GL_FALSE) {
            int[] error = new int[1];
            gl.glGetProgramiv(programId, GL2.GL_INFO_LOG_LENGTH, error, 0);

            byte[] log = new byte[error[0]];
            gl.glGetProgramInfoLog(programId, error[0], null, 0, log, 0);
            
            throw new RuntimeException("Error linking the program: " + new String(log));
        }

        gl.glDetachShader(programId, vertexShaderId);
		gl.glDetachShader(programId, fragmentShaderId);
        gl.glDeleteShader(vertexShaderId);
        gl.glDeleteShader(fragmentShaderId);
    }

    public void start(GL2 gl) {
        gl.glUseProgram(programId);
    }

    public void stop(GL2 gl) {
        gl.glUseProgram(0);
    }

    public void free(GL2 gl) {
		gl.glDeleteProgram(programId);
    }

    protected abstract void bindAttributes(GL2 gl);
    
    protected void bindAttribute(GL2 gl, int attribute, String variable){
		gl.glBindAttribLocation(programId, attribute, variable);
	}

    private int loadShader(GL2 gl, String shader, int type) {
        int shaderId = -1;
        try {
            Scanner scanner = new Scanner(new File(shader), "UTF-8");
            String source = scanner.useDelimiter("\\A").next();
            scanner.close();
            
            if ((shaderId = gl.glCreateShader(type)) == GL2.GL_INVALID_ENUM) {
                System.out.println("error 1");
                System.exit(1); //TODO
            }
            
            IntBuffer length = Buffers.newDirectIntBuffer(new int[] { source.length() });
            gl.glShaderSource(shaderId, 1, new String[] { source }, length);
            gl.glCompileShader(shaderId);

            IntBuffer status = Buffers.newDirectIntBuffer(1);
            gl.glGetShaderiv(shaderId, GL2.GL_COMPILE_STATUS, status);
            if (status.get(0) == GL2.GL_FALSE) {
                IntBuffer error = Buffers.newDirectIntBuffer(1);
                gl.glGetShaderiv(shaderId, GL2.GL_INFO_LOG_LENGTH, error);

                byte[] log = new byte[error.get(0)];
                gl.glGetShaderInfoLog(shaderId, error.get(0), null, 0, log, 0);

                throw new RuntimeException("Error compiling the shader " + shader + ": " + new String(log));
            }    
        } catch (IOException exception) {
            System.out.println("error 3" + exception);
            System.exit(1); //TODO:
        }

        return shaderId;
    }
}