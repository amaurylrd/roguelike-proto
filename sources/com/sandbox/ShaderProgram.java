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
                System.out.println("erreur 1");
                System.exit(1);
                //TODO: error 1
            }
            
            gl.glShaderSource(shaderId, 1, new String[] { source }, null); //? par ligne ou par mot ?
            gl.glCompileShader(shaderId);
            int[] result = new int[1];
            gl.glGetShaderiv(shaderId, GL2.GL_COMPILE_STATUS, result, 0);
            if (result[0] == GL2.GL_FALSE) {
                System.out.println("erreur 2");
                System.exit(1);

                //TODO: error 2        
            }
            scanner.close();
        } catch (IOException exception) {
            System.out.println("erreur 3" + exception);
            System.exit(1);
            //TODO: error 3
        }
        return shaderId;
    }
}