package com.sandbox;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.media.opengl.GL2;

public abstract class ShaderProgram {
    protected final static String SHADER_FOLDER = "ressources/shaders/";

    private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;

    public ShaderProgram(GL2 gl, String vertexShader, String fragmentShader) {
        vertexShaderId = loadShader(gl, vertexShader, GL2.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(gl, fragmentShader, GL2.GL_FRAGMENT_SHADER);
        
        programId = gl.glCreateProgram();
		gl.glAttachShader(programId, vertexShaderId);
		gl.glAttachShader(programId, fragmentShaderId);

		bindAttributes(gl);
		
        gl.glLinkProgram(programId);
		gl.glValidateProgram(programId);
    }

    public void start(GL2 gl) {
        gl.glUseProgram(programId);
    }

    public void stop(GL2 gl) {
        gl.glUseProgram(0);
    }

    /*
    clean up
    :   + stop()
        GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
    */

    protected abstract void bindAttributes(GL2 gl);
    
    protected void bindAttribute(GL2 gl, int attribute, String variable){
		gl.glBindAttribLocation(programId, attribute, variable);
	}

    private int loadShader(GL2 gl, String shader, int type) {
        int shaderId = -1;
        try {
            Scanner scanner = new Scanner(new File(shader), "UTF-8");
            String source = scanner.useDelimiter("\\A").next();
            
            if ((shaderId = gl.glCreateShader(type)) == GL2.GL_INVALID_ENUM) {
                System.out.println("error 1");
                System.exit(1); //TODO
            }
            
            //gl.glShaderSource(shaderId, 1, new String[] { source }, null); //? par ligne ou par mot ?
            gl.glShaderSource(shaderId, 1, new String[] { source }, new int[] { source.length() }, 0);
            gl.glCompileShader(shaderId);

            int[] result = new int[1];
            gl.glGetShaderiv(shaderId, GL2.GL_COMPILE_STATUS, result, 0);
            if (result[0] == GL2.GL_FALSE) {
                int[] error = new int[1];
                gl.glGetShaderiv(shaderId, GL2.GL_INFO_LOG_LENGTH, error, 0);

                byte[] log = new byte[error[0]];
                gl.glGetShaderInfoLog(shaderId, error[0], null, 0, log, 0);

                throw new RuntimeException("Error compiling the shader " + shader + ": " + new String(log));
            }
            scanner.close();
        } catch (IOException exception) {
            System.out.println("error 3" + exception);
            System.exit(1); //TODO:
        }
        return shaderId;
    }
}