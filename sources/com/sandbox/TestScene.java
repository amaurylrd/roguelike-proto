package com.sandbox;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.engine.entity.Player;
import com.engine.physics2d.Collider;
import com.engine.scene.Scene;

public class TestScene extends Scene {
    private class TestComponent extends Collider {
        public TestComponent(float x, float y, float width, float height, int layer, boolean traversable) {
            super(x, y, width, height, layer);
            this.traversable = traversable;
        }

        @Override
        public void update(float dt) {}

        //generate VBO
        private int generate(GL2 gl) { 
            int[] id = new int[1];
            gl.glGenBuffers(1, id, 0);
            //gl.glGenBuffers();
            return id[0];
        }

        //Do not unbind the index buffer anywhere.
        private void allocateIndexBuffer(GL2 gl, int[] indices) {
            int vboId = generate(gl);
            gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, vboId);
            
            IntBuffer buffer = Buffers.newDirectIntBuffer(indices);
            gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, buffer, GL2.GL_STATIC_DRAW);
            buffer = null;
            //graphics.glDeleteBuffers(vboId, buffer); TODO: clean up when on closing
        }


        private void allocateAttributeBuffer(GL2 gl, int attribute, float[] data) {
            int vboId = generate(gl);
            gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboId); //binds the buffer

            FloatBuffer buffer = Buffers.newDirectFloatBuffer(data);
            gl.glBufferData(GL2.GL_ARRAY_BUFFER, data.length * 4, buffer, GL2.GL_DYNAMIC_DRAW); //fills the buffer
            buffer = null;
            gl.glEnableVertexAttribArray(attribute); //enables the attribute at that location
            gl.glVertexAttribPointer(attribute, 2, GL2.GL_FLOAT, false, 0, 0); //once the buffer is bound
            gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0); //unbinds the buffer
            
            //gl.glDeleteBuffers(vboId, buffer); TODO: clean up when on closing
            //gl.glDeleteVertexArrays(vboId, null); TODO: clean up vaos
            //gl.glDeleteBuffers(1, vboName, 0);
        }

        @Override
        protected void draw(GL2 graphics) {
            float[] vertices = {
                bounds.getX(), bounds.getY(),
                bounds.getX(), bounds.getY() + bounds.getHeight(),
                bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight(),
                bounds.getX() + bounds.getWidth(), bounds.getY(),
            };

            //sens anti-horaire
            int[] indices = { 0, 1, 3, 3, 1, 2 };

            //creation vao
            int[] id = new int[1];
            graphics.glGenVertexArrays(1, id, 0);
            int vaoId = id[0];

            graphics.glBindVertexArray(vaoId);
                allocateIndexBuffer(graphics, indices);
                allocateAttributeBuffer(graphics, 0, vertices);
            graphics.glBindVertexArray(0);

            //render
            graphics.glBindVertexArray(vaoId);
		        graphics.glEnableVertexAttribArray(0);
                    graphics.glDrawElements(GL2.GL_TRIANGLES, indices.length, GL2.GL_UNSIGNED_INT, 0);
                graphics.glDisableVertexAttribArray(0);
		    graphics.glBindVertexArray(0);
            graphics.glFlush();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }
    }

    public TestScene() {
        for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 4; j++) {
				add(new TestComponent(i * 100, 250, 100, 10, Scene.TILES_LAYER, true)); // plafond traversable
				//add(new TestComponent(i * 100, j * 100 + 500, 100, 100, -3, false)); // background -3
				add(new TestComponent(i * 100 + 100, j * 100 + 500, 100, 100, Scene.TILES_LAYER, false)); // sol banane test
			}
		}
		add(new Player(150, 300, 100, 100, Scene.ENTITIES_LAYER));
    }

    ShaderProgram shader = null;

    @Override
    public void render(GL2 gl) {
        if (shader == null)
			shader = new StaticShader(gl);
        //shader.start(gl);
        super.render(gl);
        //shader.stop(gl);
    }
}
