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
	public float trauma = 0;

	/**
	 * The {@code decay} value represents how quickly the shaking stops (between 0
	 * and 1).
	 */
	public float decay = 0.6f;

	/**
	 * This value represents the amplitude of shakes (should be possitiv).
	 */
	public float amplitude = 0.2f;

	/**
	 * Adds the specified value to the total {@code trauma}. Trauma applies
	 * turbulence on this {@code Camera} (in both axis with translations and
	 * rotations).
	 * 
	 * @param amount the specified value to add
	 */
	public void addTrauma(float amount) {
		trauma = Math.min(trauma + amount, 1);
	}

	/**
	 * Applies trauma if possible and updates the camera position and angle.
	 *
	 * @param dt the delta time between updates at render speed
	 */
	@Override
	public void update(float dt) {
		rotation = rotation * 0.9f;
		if (scene.player != null) {
			Rectangle playerBounds = scene.player.getBounds();
			float targetX = playerBounds.getX() + playerBounds.getWidth() / 2 - scene.getWidth() / 2.3f;
			float targetY = playerBounds.getY() + playerBounds.getHeight() / 2 - scene.getHeight() / 2;
			translate((targetX - getX()) * 0.1f, (targetY - getY()) * 0.02f);
		}

		if (trauma > 0) {
			if (Properties.evaluate("enable_camera_shake")) {
				float magnitude = amplitude * trauma * trauma * trauma;
				float offsetX = Random.nextFloat(-1, 1) * magnitude * 50;
				float offsetY = Random.nextFloat(-1, 1) * magnitude * 50;
				translate(offsetX, offsetY);
				rotation -= Random.nextFloat(-1, 1) * magnitude * Math.PI / 50;
			}
			trauma -= Math.min((trauma + 0.3f) * decay * dt / 1000, trauma);
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
	private float[] frameRateValues = new float[100];

	/**
	 * The current index in the array {@code FPS_VALUES}.
	 */
	private int currentIndex = 0;

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
			String text = "FPS: " + (int) averageFPS();
			int x = (int) (viewport.getX() + viewport.getWidth()) - MARGIN_LEFT - metric.stringWidth(text);
			int y = (int) viewport.getY() + MARGIN_TOP + metric.getHeight();
			graphics.drawString(text, x, y);
			graphics.setColor(penColor);
		}
	}

	public void updateFPS(float frameRate) {
		frameRateValues[currentIndex] = frameRate;
		currentIndex = (currentIndex + 1) % frameRateValues.length;
	}

	private float averageFPS() {
		float average = 0;
		int i;
		for (i = 0; i < frameRateValues.length && frameRateValues[i] != 0; ++i)
			average += frameRateValues[i];
		return average / i;
	}
}