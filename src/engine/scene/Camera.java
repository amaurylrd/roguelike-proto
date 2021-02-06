package engine.scene;

import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Color;

import engine.application.Properties;
import engine.geom.shape.Rectangle;
import engine.scene.entity.Component;
import engine.scene.entity.Drawable;
import engine.util.Random;

/**
 * The class {@code Camera} defines a 2D camera which is following the
 * {@code Player} movement.
 */
public class Camera extends Rectangle implements Drawable {
	/**
	 * The {@code Scene} linked to this {@code Camera}.
	 */
	private Scene scene;

	/**
	 * This {@code Rectangle} is the area on screen shown by the {@code Camera}.
	 */
	private Rectangle viewport;

	/**
	 * The {@code Camera} translates the {@code scene} upon its {@code Player}.
	 * 
	 * @param scene the scene to capture and center
	 */
	public Camera(Scene scene) {
		super(0, 0, scene.getWidth(), scene.getHeight());
		viewport = this.clone();
		this.scene = scene;
	}

	/**
	 * The {@code trauma} represents the turbulence of this {@code Camera} (between
	 * 0 and 1).
	 */
	public double trauma = 0.0;

	/**
	 * The {@code decay} value represents how quickly the shaking stops (between 0
	 * and 1).
	 */
	public double decay = 0.6;

	/**
	 * This value represents the amplitude of shakes (should be possitiv).
	 */
	public double amplitude = 0.2;

	/**
	 * Adds the specified value to the total {@code trauma}. Trauma applies
	 * turbulence on this {@code Camera} (in both axis with translations and
	 * rotations).
	 * 
	 * @param amount the specified value to add
	 */
	public void addTrauma(double amount) {
		trauma = Math.min(trauma + amount, 1.0);
	}

	/**
	 * Applies trauma if possible and updates the camera position and angle.
	 *
	 * @param dt the delta time between updates at render speed
	 */
	@Override
	public void update(double dt) {
		rotation = rotation * 0.9;
		if (scene.player != null) {
			Rectangle playerBounds = scene.player.getBounds();
			double targetX = playerBounds.getX() + playerBounds.getWidth() / 2.0 - scene.getWidth() / 2.3;
			double targetY = playerBounds.getY() + playerBounds.getHeight() / 2.0 - scene.getHeight() / 2.0;
			translate((targetX - getX()) * 0.1, (targetY - getY()) * 0.02);
		}

		if (trauma > 0) {
			if (Properties.evaluate("enable_camera_shake")) {
				double magnitude = amplitude * trauma * trauma * trauma;
				double offsetX = Random.nextDouble(-1.0, 1.0) * magnitude * 50.0;
				double offsetY = Random.nextDouble(-1.0, 1.0) * magnitude * 50.0;
				translate(offsetX, offsetY);
				rotation -= Random.nextDouble(-1.0, 1.0) * magnitude * Math.PI / 50.0;
			}
			trauma -= Math.min((trauma + 0.3) * decay * dt / 1000.0, trauma);
		}
	}

	/**
	 * Returns if the specified {@code component} should be painted.
	 * 
	 * @param component the specified component to intersect
	 * @return <i>true</i> if the {@code component} is contained in the viewport
	 */
	public boolean focuses(Component component) {
		return viewport.intersects(component.getBounds());
	}

	/**
	 * Returns a copy of the {@code viewport} of this {@code Camera}.
	 * 
	 * @return the viewport rectangle
	 */
	public Rectangle getViewport() {
		return viewport.clone();
	}

	/**
	 * The frequency at which frames appear on screen. (in seconds)
	 */
	private int framePerSecond = 0;

	/**
	 * Represents the gap between text and the top edge. (in pixels)
	 */
	private final int MARGIN_TOP = 10;

	/**
	 * Represents the gap between text and the left edge. (in pixels)
	 */
	private final int MARGIN_LEFT = 20;

	@Override
	public void render(Graphics2D graphics) {
		if (Properties.evaluate("enable_fps_counter")) {
			Color penColor = graphics.getColor();
			graphics.setColor(new Color(209, 209, 209, 180));
			FontMetrics metric = graphics.getFontMetrics(graphics.getFont());
			String text = "FPS: " + framePerSecond;
			int x = (int) (viewport.getX() + viewport.getWidth()) - MARGIN_LEFT - metric.stringWidth(text);
			int y = (int) viewport.getY() + MARGIN_TOP + metric.getHeight();
			graphics.drawString(text, x, y);
			graphics.setColor(penColor);
		}
	}

	public void updateFPS(int frameRate) {
		this.framePerSecond = frameRate;
	}
}