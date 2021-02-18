package engine.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public abstract class Canvas {
    private JFrame rootPane;
    private Graphics2D offscreen, onscreen;
    private BufferedImage offscreenImage;

    public void bind(JFrame contentPane) {
        rootPane = contentPane;
        int width = contentPane.getWidth(), height = contentPane.getHeight();

        BufferedImage onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreen = onscreenImage.createGraphics();
        contentPane.setContentPane(new JLabel(new ImageIcon(onscreenImage)));

        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        offscreen.setColor(Color.BLACK);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(Color.WHITE);

        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);

        offscreen.fillRect(width, height, 1, 1);
        contentPane.requestFocus();
        contentPane.requestFocusInWindow();
		contentPane.toFront();
        start();
    }

    // public void test(BufferedImage image, float x, float y) {
    //     Graphics2D g2d = offscreenImage.createGraphics();
    //     g2d.drawImage(image, (int) x, (int) y, null);
    //     g2d.dispose();
    // }

    public abstract void start();

    public void show() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        rootPane.getContentPane().repaint(0, 0, rootPane.getWidth(), rootPane.getHeight());
        rootPane.revalidate();
    }

    public void clear() {
        Color penColor = offscreen.getColor();
        offscreen.setColor(Color.BLACK);
        offscreen.fillRect(0, 0, rootPane.getWidth(), rootPane.getHeight());
        offscreen.setColor(penColor);
    }

    public Graphics2D getContext() {
        return offscreen;
    }

    public BufferedImage getBuffer() {
        return offscreenImage;
    }

    public float getWidth() {
        return rootPane == null ? 0 : (float) rootPane.getWidth();
    }

    public float getHeight() {
        return rootPane == null ? 0 : (float) rootPane.getHeight();
    }
}