package engine.scene;

import engine.geom.shape.Rectangle;
import engine.scene.entity.Component;
import engine.util.Random;

public class Camera extends Rectangle {
	private Scene scene;

	public Camera(Scene scene) {
		super(0, 0, scene.getWidth(), scene.getHeight());
		this.scene = scene;
	}

	public double shakeDuration = 0.0;
	public double internalTimer = 0.0;

	public double amplitude = 20.0;
	public double frequency = 35;

	public void shake(double duration) {
		shakeDuration = duration;
	}

	protected void update(double dt) {
		if (scene.player != null) {
			Rectangle playerBox = scene.player.getBounds();
			double targetX = playerBox.getX() + playerBox.getWidth() / 2 - scene.getWidth() / 2.3;
			double targetY = playerBox.getY() + playerBox.getHeight() / 2 - scene.getHeight() / 2;
			translate((targetX - getX()) * 0.1, (targetY - getY()) * 0.02);
		}

		internalTimer += dt;
		if (shakeDuration > 0) {
			shakeDuration -= dt;
			double shakeTime = (internalTimer * frequency);
			double deltaT = shakeTime - (int) shakeTime;
			
			//dans le constructeur
			double fst = Random.nextDouble() * 2 - 1;
			double scd = Random.nextDouble() * 2 - 1;
			double lst = Random.nextDouble() * 2 - 1;
			
			double deltaX = fst * deltaT + scd * ( 1 - deltaT);
			double deltaY = scd * deltaT + lst * ( 1 - deltaT);

			double dx = deltaX, dy = deltaY;
			translate(dx * amplitude * Math.min(shakeDuration, 1), dy * amplitude * Math.min(shakeDuration, 1));
		} 
	}

	/**
	 * Gives the linear interpolation between two known points.
	 * 
	 * @param a the minimum value to evaluate
	 * @param b the maximum value to evaluate
	 * @param f the interpolation value between the two previous values
	 * @return the linear interpolation
	 */
	public static double lerp(double a, double b, double f) {
		return a + f*(b - a);
	}

	public boolean focuses(Component component) {
		Rectangle bounds = component.getBounds().clone();
		bounds.translate((1 + 0.05 * component.getLayer()) * getX(), (1 + 0.05 * component.getLayer()) * getY());
		return intersects(bounds);
	}
}