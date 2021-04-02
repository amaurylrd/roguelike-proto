package com.sandbox;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL2;

import com.aspose.psd.internal.A.a;
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

        private void allocateIndexBuffer(GL2 graphics, int[] indices) {
            int[] vb = new int[1];
            graphics.glGenBuffers(1, vb, 0);
            int vboId = vb[0];
            graphics.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, vboId);
                
            IntBuffer buffer = IntBuffer.allocate(indices.length);
            buffer.put(0, indices);
            buffer.flip();

            graphics.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, indices.length, buffer, GL2.GL_DYNAMIC_DRAW);
            graphics.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
            //graphics.glDeleteBuffers(vboId, buffer);
        }
        
        private void allocateAttributeBuffer(GL2 graphics, int attribute, float[] data) {
            int[] vb = new int[1];
            graphics.glGenBuffers(1, vb, 0);
            int vboId = vb[0];
            graphics.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboId);

            FloatBuffer buffer = FloatBuffer.allocate(data.length);
            buffer.put(0, data);
            buffer.flip();

            graphics.glBufferData(GL2.GL_ARRAY_BUFFER, data.length, buffer, GL2.GL_DYNAMIC_DRAW);
            graphics.glVertexAttribPointer(0, 2, GL2.GL_FLOAT, false, 0, 0);
            graphics.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
            //graphics.glDeleteBuffers(vboId, buffer);
        }

        @Override
        protected void draw(GL2 graphics) {
            String mode = "new";
            if (mode.equals("new")) {
                float[] vertices = {
                    bounds.getX(), bounds.getY(),
                    bounds.getX(), bounds.getY() + bounds.getHeight(),
                    bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight(),
                    bounds.getX() + bounds.getWidth(), bounds.getY(),
                };

                int rgb = java.awt.Color.WHITE.getRGB();
                float color = rgb / 250f;
                float[] colors = {
                    color,
                    color,
                    color,
                    1
                };

                int[] indices = { 0, 1, 2, 2, 1, 3 };

                //creation vao
                int[] va = new int[1];
                graphics.glGenVertexArrays(1, va, 0);
                int vaoId = va[0];
                graphics.glBindVertexArray(vaoId);

                allocateIndexBuffer(graphics, indices);
                allocateAttributeBuffer(graphics, 0, vertices);
                //allocateAttributeBuffer(graphics, 1, colors);

                //unbind vao
                graphics.glBindVertexArray(0);

                //render
                graphics.glBindVertexArray(vaoId);
		        graphics.glEnableVertexAttribArray(0);
                //graphics.glEnableVertexAttribArray(1);
                    graphics.glDrawElements(GL2.GL_TRIANGLES, indices.length, GL2.GL_UNSIGNED_INT, 0);
                graphics.glDisableVertexAttribArray(0);
                //graphics.glDisableVertexAttribArray(1);
		        graphics.glBindVertexArray(0);
                graphics.glFlush();
            } else if (mode.equals("old")) {
                graphics.glColor3f(255, 0, 0);
                graphics.glBegin(GL2.GL_QUADS);
                    graphics.glVertex2f(bounds.getX(), bounds.getY());
                    graphics.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY());
                    graphics.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
                    graphics.glVertex2f(bounds.getX(), bounds.getY() + bounds.getHeight());
                graphics.glEnd();
            }
        }

        @Override
        public boolean isRemovable() {
            return false;
        }
    }

    public TestScene() {
        float constant = -100;
        for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 4; j++) {
				add(new TestComponent(i * 100, 250, 100, 10, Scene.TILES_LAYER, true)); // plafond traversable
				//add(new TestComponent(i * 100, j * 100 + 500, 100, 100, -3, false)); // background -3
				add(new TestComponent(i * 100 + 100, j * 100 + 500, 100, 100, Scene.TILES_LAYER, false)); // sol banane test
			}
		}
		add(new Player(150, 300, 100, 100, Scene.ENTITIES_LAYER));
    }
}
