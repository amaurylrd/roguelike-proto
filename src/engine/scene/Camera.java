package engine.scene;

import engine.geom.shape.Rectangle;
import engine.scene.entity.Component;
import engine.util.Random;

/**
 * The class {@code Camera} defines a 2D camera which is following the {@code Player} movement.
 */
public class Camera extends Rectangle {
	/**
	 * The {@code Scene} linked to this {@code Camera}.
	 */
	private Scene scene;

	/**
	 * This {@code rectangle} is the area on screen of this {@code Camera}.
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
	 * The {@code trauma} represents the turbulence of this {@code Camera} (between 0 and 1).
	 */
	public double trauma = 0.0;

	/**
	 * The {@code decay} value represents how quickly the shaking stops (between 0 and 1).
	 */
	public double decay = 0.6;

	/**
	 * This value represents the amplitude of shakes.
	 */
	public double amplitude = 0.2;

	/**
	 * Adds the specified value to the total trauma.
	 * Trauma applies turbulence on this {@code Camera} (in both axis with translations and rotations).
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
	public void update(double dt) {
		rotation = rotation * 0.9;
		if (scene.player != null) {
			Rectangle player = scene.player.getBounds();
			double targetX = player.getX() + player.getWidth() / 2.0 - scene.getWidth() / 2.3;
			double targetY = player.getY() + player.getHeight() / 2.0 - scene.getHeight() / 2.0;
			translate((targetX - getX()) * 0.1, (targetY - getY()) * 0.02);
		}
		
		if (trauma > 0) {
			double magnitude = amplitude * trauma * trauma * trauma;
			double offsetX = Random.nextDouble(-1.0, 1.0) * magnitude * 50.0;
			double offsetY = Random.nextDouble(-1.0, 1.0) * magnitude * 50.0;
			translate(offsetX, offsetY);
			rotation -= Random.nextDouble(-1.0, 1.0) * magnitude * Math.PI / 50.0;
			trauma -= Math.min((trauma + 0.3) * decay * dt / 1000.0, trauma);
		}
	}
	
	/**
	 * Returns if the specified {@code component} should be painted.
	 * 
	 * @param component the specified component to intersect
	 * @return <true> if the {@code component} is contained in the camera's viewport
	 */
	public boolean focuses(Component component) {
		return viewport.intersects(component.getBounds());
	}
}