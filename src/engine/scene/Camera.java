package engine.scene;

import engine.geom.shape.Rectangle;
import engine.scene.entity.Component;
import engine.util.Random;

public class Camera extends Rectangle {
	private Scene scene;
	private Rectangle viewport;

	public Camera(Scene scene) {
		super(0, 0, scene.getWidth(), scene.getHeight());
		viewport = clone();
		this.scene = scene;
	}

	public double trauma = 0.0; //turbulence [0 ; 1]
	public double decay = 0.6;  ///How quickly the shaking stops [0 ; 1].

	public double amplitude = 0.2; //amplitude of shakes

	public void addTrauma(double amount) {
		trauma = Math.min(trauma + amount, 1.0);
	}

	protected void update(double dt) {
		if (scene.player != null) {
			Rectangle playerBox = scene.player.getBounds();
			double targetX = playerBox.getX() + playerBox.getWidth() / 2 - scene.getWidth() / 2.3;
			double targetY = playerBox.getY() + playerBox.getHeight() / 2 - scene.getHeight() / 2;
			translate((targetX - getX()) * 0.1, (targetY - getY()) * 0.02);
			rotation = rotation * 0.9;
		}

		if (trauma > 0) {			
			double magnitude = amplitude * trauma * trauma * trauma;
			double offsetX = Random.nextDouble(-1, 1) * magnitude * 50;
			double offsetY = Random.nextDouble(-1, 1) * magnitude * 50;
			translate(offsetX, offsetY);
			rotation -= Random.nextDouble(-1, 1) * magnitude * Math.PI/50;
			trauma -= Math.min((trauma + 0.3) * decay * dt / 1000, trauma);
		}
	}
	
	/**
	 * Returns if the specified {@ccode component} should be painted.
	 * 
	 * @param component the specified component
	 * @return <true> if the {@ccode component} is contained in the camera's viewport
	 */
	public boolean focuses(Component component) {
		return viewport.intersects(component.getBounds());
	}
}